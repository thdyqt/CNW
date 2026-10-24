const moment = require("moment");
const bcrypt = require("bcryptjs");
const AccountAdmin = require("../../models/account-admin.model");
const Role = require("../../models/role.model");

module.exports.edit = async (req, res) => {
  const currentAccountAdmin = await AccountAdmin.findOne({
    _id: req.account.id
  })

  const roleName = await Role.findOne({
    _id: currentAccountAdmin.role
  })

  currentAccountAdmin.roleName = roleName.name;

  res.render("admin/pages/profile-edit", {
    pageTitle: "Thông tin cá nhân",
    currentAccountAdmin: currentAccountAdmin
  });
}

module.exports.editPatch = async (req, res) => {
  try {
    const id = req.account.id;

    req.body.updatedBy = id;

    if (req.body.password) {
      const salt = await bcrypt.genSaltSync(10); 
      req.body.password = await bcrypt.hash(req.body.password, salt);
    } else {
      delete req.body.password; 
    }

    if (req.file) {
      req.body.avatar = req.file.path;
    } else if (req.body.avatar === "deleted") {
      req.body.avatar = ""; 
    } else {
      delete req.body.avatar;
    }

    await AccountAdmin.updateOne({ 
        _id: id,
        deleted: false 
      }, req.body
    );
    
    req.flash('success', `Cập nhật tài khoản cá nhân thành công!`);

    res.json(
      { 
        code: "success" 
      });
  } catch (error) {
    res.json({ 
      code: "error", 
      message: error 
    });
  }
}

module.exports.changePassword = async (req, res) => {
  res.render("admin/pages/profile-change-password", {
    pageTitle: "Đổi mật khẩu"
  });
}

module.exports.changePasswordPatch = async (req, res) => {
  try {
    const id = req.account.id;

    req.body.updatedBy = id;

    const salt = await bcrypt.genSaltSync(10); 
    req.body.password = await bcrypt.hash(req.body.password, salt);

    await AccountAdmin.updateOne({ 
        _id: id,
        deleted: false 
      }, req.body
    );
    
    req.flash('success', `Đổi mật khẩu thành công!`);

    res.json({ 
      code: "success" 
    });
  } catch (error) {
    res.json({ 
      code: "error", 
      message: error 
    });
  }
}