const AccountAdmin = require("../../models/account-admin.model");
const ForgotPassword = require("../../models/forgot-password.model");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

const generateHelper = require("../../helpers/generate.helper");
const mailHelper = require("../../helpers/mail.helper")

module.exports.login = async (req, res) => {
  res.render("admin/pages/login", {
    pageTitle: "Đăng nhập"
  });
}

module.exports.loginPost = async (req, res) => {
  const { email, password, rememberPassword } = req.body;
  const existAccount = await AccountAdmin.findOne({
    email: email
  })

  if (!existAccount) {
    return res.json({
      code: "error",
      message: "Email chưa tồn tại trong hệ thống!"
    });
  }

  // Kiểm tra mật khẩu
  const isPasswordValid = await bcrypt.compare(password, existAccount.password);
  if (!isPasswordValid) {
    return res.json({
      code: "error",
      message: "Mật khẩu không đúng!"
    });
  }

  // Kiểm tra tài khoản đã được kích hoạt chưa.
  if (existAccount.status != "active") {
    return res.json({
      code: "error",
      message: "Tài khoản chưa được kích hoạt!"
    });
  }

  // Tạo JWT
  const token = jwt.sign(
    {
      id: existAccount.id,
      email: existAccount.email
    },
    process.env.JWT_SECRET,
    {
      expiresIn: rememberPassword ? "30d" : "1d" 
    }
  );

  res.cookie("token", token, {
    maxAge: rememberPassword ? (30 * 24 * 60 * 60 * 1000) : (24 * 60 * 60 * 1000), // Token lưu trong cookie 1 ngày (ms)
    httpOnly: true, // Chỉ cho phép server được truy cấp cookie
    sameSite: "strict" // Không gửi được yêu cầu từ website khác
  });

  res.json({
    code: "success",
    message: "Đăng nhập tài khoản thành công"
  });
}

module.exports.register = async (req, res) => {
  res.render("admin/pages/register", {
    pageTitle: "Đăng ký"
  });
}

module.exports.registerPost = async (req, res) => {
  const { fullName, email, password } = req.body;
  const existAccount = await AccountAdmin.findOne({
    email: email
  });

  if (existAccount) {
    return res.json({
      code: "error",
      message: "Email đã tồn tại trong hệ thống!"
    });
  }

  // Mã khóa mật khẩu với BCryptJS
  const salt = await bcrypt.genSaltSync(10); // Tạo chuỗi ngẫu nhiên có 10 kí tự
  const hashedPassword = await bcrypt.hash(password, salt); 

  const newAccount = new AccountAdmin({
    fullName: fullName,
    email: email,
    password: hashedPassword,
    status: "initial"
  });
  await newAccount.save();

  res.json({
    code: "success",
    message: "Đăng ký tài khoản thành công!"
  })
}

module.exports.registerInitial = async (req, res) => {
  res.render("admin/pages/register-initial", {
    pageTitle: "Tài khoản đã được khởi tạo!"
  });
}

module.exports.forgotPassword = async (req, res) => {
  res.render("admin/pages/forgot-password", {
    pageTitle: "Quên mật khẩu"
  });
}

module.exports.forgotPasswordPost = async (req, res) => {
  const { email } = req.body;

  const existAccount = await AccountAdmin.findOne({
    email: email
  })

  if (!existAccount) {
    return res.json({
      code: "error",
      message: "Email không tồn tại trong hệ thống!"
    })
  }

  const existEmailInForgotPassword = await ForgotPassword.findOne({
    email: email
  })

  if (existEmailInForgotPassword) {
    return res.json({
      code: "error",
      message: "Vui lòng gửi lại yêu cầu sau 5 phút!"
    })
  }

  const otp = generateHelper.generateRandomNumber(6);

  // Lưu vào database email và mã OTP, sau 5 phút tự xóa bản ghi
  const record = new ForgotPassword({
    email: email,
    otp: otp,
    expireAt: Date.now() + 5 * 60 * 1000 // ms
  })

  await record.save();

  // Gửi email tự động cho người dùng
  const subject = "Mã OTP để lấy lại mật khẩu";
  const content = `Mã OTP của bạn là <b>${otp}</b>. Mã OTP có hiệu lực trong 5 phút, vui lòng không cung cấp cho bất kì ai.`
  mailHelper.sendMail(email, subject, content)

  res.json({
    code: "success",
    message: "Gửi mã OTP thành công!"
  })
}

module.exports.otpPassword = async (req, res) => {
  res.render("admin/pages/otp-password", {
    pageTitle: "Nhập mã OTP"
  });
}

module.exports.otpPasswordPost = async (req, res) => {
  const { email, otp } = req.body;

  const existRecord = await ForgotPassword.findOne({
    email: email,
    otp: otp
  });
  
  if (!existRecord) {
    return res.json({
      code: "error",
      message: "Mã OTP không chính xác!"
    })
  }

  const account = await AccountAdmin.findOne({
    email: email
  });

  const token = jwt.sign(
    {
      id: account.id,
      email: account.email
    },
    process.env.JWT_SECRET,
    {
      expiresIn: "1d" 
    }
  );

  res.cookie("token", token, {
    maxAge: 24 * 60 * 60 * 1000, // Token lưu trong cookie 1 ngày (ms)
    httpOnly: true, // Chỉ cho phép server được truy cấp cookie
    sameSite: "strict" // Không gửi được yêu cầu từ website khác
  });

  res.json({
    code: "success",
    message: "Xác thực OTP thành công!"
  })
}

module.exports.resetPassword = async (req, res) => {
  res.render("admin/pages/reset-password", {
    pageTitle: "Đổi mật khẩu"
  });
}

module.exports.resetPasswordPost = async (req, res) => {
  const { password } = req.body;

  // Mã khóa mật khẩu với BCryptJS
  const salt = await bcrypt.genSaltSync(10); // Tạo chuỗi ngẫu nhiên có 10 kí tự
  const hashedPassword = await bcrypt.hash(password, salt); 

  await AccountAdmin.updateOne({
    _id: req.account.id
  }, {
    password: hashedPassword
  });
  
  res.json({
    code: "success",
    message: "Thay đổi mật khẩu thành công!"
  })
}

module.exports.logoutPost = async (req, res) => {
  res.clearCookie("token");
  res.json({
    code: "success",
    message: "Đăng xuất thành công!"
  })
}