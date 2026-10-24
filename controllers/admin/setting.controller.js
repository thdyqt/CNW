const moment = require("moment");
const slugify = require('slugify');
const bcrypt = require("bcryptjs");
const SettingWebsiteInfo = require("../../models/setting-website-info.model");
const Role = require("../../models/role.model");
const AccountAdmin = require("../../models/account-admin.model");
const permissionConfig = require("../../config/permission")

module.exports.list = async (req, res) => {
  res.render("admin/pages/setting-list", {
    pageTitle: "Cài đặt chung",
  });
}

// Website Info
module.exports.websiteInfo = async (req, res) => {
  let settingWebsiteInfo = await SettingWebsiteInfo.findOne({});

  if (!settingWebsiteInfo) {
    settingWebsiteInfo = {};
  }

  res.render("admin/pages/website-info", {
    pageTitle: "Thông tin website",
    settingWebsiteInfo: settingWebsiteInfo
  });
}

module.exports.websiteInfoPatch = async (req, res) => {
  try {
    if (req.files && req.files.logo) {
      req.body.logo = req.files.logo[0].path; 
    } else {
      if (req.body.logo === "deleted") {
        req.body.logo = ""; // 
      } else {
        delete req.body.logo; 
      }
    }

    if (req.files && req.files.favicon) {
      req.body.favicon = req.files.favicon[0].path;
    } else {
      if (req.body.favicon === "deleted") {
        req.body.favicon = ""; 
      } else {
        delete req.body.favicon; 
      }
    }

    await SettingWebsiteInfo.findOneAndUpdate({}, req.body, { upsert: true });

    req.flash('success', `Cập nhật thông tin website thành công!`);

    res.json({
      code: "success",
    });
  } catch (error) {
      res.json({
        code: "error",
        message: error
      })
  }
}

// Account Admin
module.exports.accountAdminList = async (req, res) => {
  const find = { 
    deleted: false 
  };

  // Lọc theo trạng thái
  if (req.query.status) {
    find.status = req.query.status;
  }

  // Lọc theo nhóm quyền
  if (req.query.role) {
    find.role = req.query.role;
  }

  // Lọc theo ngày tạo
  const dateFilter = {};
  if (req.query.startDate) {
    const startDate = moment(req.query.startDate).startOf("day").toDate();
    dateFilter.$gte = startDate;
  }

  if (req.query.endDate) {
    const endDate = moment(req.query.endDate).startOf("day").toDate();
    dateFilter.$lte = endDate;
  }
  
  if (Object.keys(dateFilter).length > 0) {
    find.createdAt = dateFilter;
  }
  
  // Tìm kiếm
  if (req.query.keyword) {
    const keyword = slugify(req.query.keyword, {
      lower: true
    });

    const keywordRegex = new RegExp(keyword);
    find.slug = keywordRegex;
  }

  // Phân trang
  const limitItems = 10;
  let page = 1;
  if (req.query.page) page = parseInt(req.query.page);
  
  const totalRecord = await AccountAdmin.countDocuments(find);
  const totalPage = Math.ceil(totalRecord / limitItems);
  const skip = (page - 1) * limitItems;
  const pagination = { skip, totalRecord, totalPage };

  const accountAdminList = await AccountAdmin
    .find(find)
    .sort({
      position: "desc"
    })
    .limit(limitItems)
    .skip(skip)

  // Lấy tên nhóm quyền
  for (const item of accountAdminList) {
    if (item.role) {
      const roleInfo = await Role.findOne({
        _id: item.role
      });

      if (roleInfo) {
        item.roleName = roleInfo.name;
      }
    }
  }

  const roleList = await Role.find({ 
    deleted: false 
  });

  res.render("admin/pages/admin-list", {
    pageTitle: "Tài khoản quản trị",
    accountAdminList: accountAdminList,
    roleList: roleList,
    pagination: pagination
  });
}

module.exports.accountAdminCreate = async (req, res) => {
  const roleList = await Role.find({
    deleted: false
  });

  res.render("admin/pages/admin-create", {
    pageTitle: "Tạo tài khoản quản trị",
    roleList: roleList
  });
}

module.exports.accountAdminCreatePost = async (req, res) => {
  req.body.createdBy = req.account.id;
  req.body.updatedBy = req.account.id;
  
  req.body.avatar = req.file ? req.file.path : "";

  // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
  const existAccount = await AccountAdmin.findOne({ 
    email: req.body.email 
  });

  if (existAccount) {
    return res.json({
      code: "error",
      message: "Email đã tồn tại trong hệ thống!"
    });
  }

  // Mã hóa mật khẩu bằng thư viện bcrypt 
  const salt = await bcrypt.genSalt(10);
  req.body.password = await bcrypt.hash(req.body.password, salt);

  const newAccount = new AccountAdmin(req.body);
  await newAccount.save();

  req.flash('success', 'Tạo tài khoản quản trị thành công!');

  res.json({
    code: "success"
  })
}

module.exports.accountAdminEdit = async (req, res) => {
  try {
    const roleList = await Role.find({
      deleted: false
    });

    const id = req.params.id;
    const currentAccount = await AccountAdmin.findOne({
      _id: id,
      deleted: false
    });

    res.render("admin/pages/admin-edit", {
      pageTitle: "Chỉnh sửa tài khoản quản trị",
      roleList: roleList,
      currentAccount: currentAccount
    });
  } catch (error) {
    res.redirect(`/${pathAdmin}/setting/account-admin/list`);
  }
}

module.exports.accountAdminEditPatch = async (req, res) => {
  try {
    const id = req.params.id; 

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
    
    req.flash('success', `Cập nhật tài khoản quản trị thành công!`);

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

module.exports.accountAdminDeletePatch = async (req, res) => {
  try {
    const id = req.params.id;

    const deletedAccountAdmin = await AccountAdmin.findOne({
      _id: id
    })

    await AccountAdmin.updateOne({
      _id: id,
    }, {
      deleted: true,
      deletedAt: Date.now(),
      deletedBy: req.account.id,
      updatedBy: req.account.id
    })

    req.flash('success', `Xóa tài khoản quản trị "${deletedAccountAdmin.fullName}" thành công!`);

    res.json({
      code: "success",
    });
  } catch (error) {
      res.json({
        code: "error",
        message: error
      })
  }
}

module.exports.accountAdminChangeMultiPatch = async (req, res) => {
  try {
    const { option, ids } = req.body;
    switch (option) {
      case "active":
      case "inactive":
        await AccountAdmin.updateMany({ 
          _id: { $in: ids } 
        }, { 
          status: option,
          updatedBy: req.account.id 
        });
        req.flash('success', `Đổi trạng thái thành công!`);
        break;
      case "delete":
        await AccountAdmin.updateMany({
          _id: { $in: ids }
        }, {
          deleted: true,
          deletedAt: Date.now(),
          deletedBy: req.account.id,
          updatedBy: req.account.id
        });
        req.flash('success', `Xóa thành công!`);
        break;
    }
    res.json({
      code: "success"
    });
  } catch (error) {
    res.json({
      code: "error",
      message: "Cập nhật không thành công!"
    })
  }
}

// Role
module.exports.roleList = async (req, res) => {
  const find = {
    deleted: false
  };

  // Tìm kiếm
  if (req.query.keyword) {
    const keyword = slugify(req.query.keyword, {
      lower: true
    });

    const keywordRegex = new RegExp(keyword);
    find.slug = keywordRegex;
  }

  // Phân trang
  const limitItems = 10;
  let page = 1;
  if (req.query.page) {
    const currentPage = parseInt(req.query.page);
    if (currentPage > 0) page = parseInt(req.query.page);
  }

  const totalRecord = await Role.countDocuments(find);
  const totalPage = Math.ceil(totalRecord / limitItems);

  const skip = (page - 1) * limitItems;

  const pagination = {
    skip: skip,
    totalRecord: totalRecord,
    totalPage: totalPage
  }

  const roleList = await Role
    .find(find)
    .sort({
      position: "desc"
    })
    .limit(limitItems)
    .skip(skip)

  res.render("admin/pages/role-list", {
    pageTitle: "Nhóm quyền",
    roleList: roleList,
    pagination: pagination
  });
}

module.exports.roleCreate = async (req, res) => {
  res.render("admin/pages/role-create", {
    pageTitle: "Tạo nhóm quyền",
    permissionList: permissionConfig.permissionList
  });
}

module.exports.roleCreatePost = async (req, res) => {  
  req.body.createdBy = req.account.id;
  req.body.updatedBy = req.account.id;

  const newRecord = new Role(req.body);
  await newRecord.save();

  req.flash("success", "Tạo nhóm quyền thành công!");

  res.json({
    code: "success"
  })
}

module.exports.roleEdit = async (req, res) => {
  try {
    const id = req.params.id;

    const currentRole = await Role.findOne({
      _id: id,
      deleted: false
    });

    res.render("admin/pages/role-edit", {
      pageTitle: "Chỉnh sửa nhóm quyền",
      permissionList: permissionConfig.permissionList,
      currentRole: currentRole
    });
  } catch (error) {
    res.redirect(`/${pathAdmin}/setting/role/list`);
  }
}

module.exports.roleEditPatch = async (req, res) => {
  try {
    const id = req.params.id;

    req.body.updatedBy = req.account.id;

    await Role.updateOne({
      _id: id,
      deleted: false
    }, req.body)

    req.flash('success', `Cập nhật nhóm quyền "${req.body.name}" thành công!`);

    res.json({
      code: "success",
    });
  } catch (error) {
      res.json({
        code: "error",
        message: error
      })
  }
}

module.exports.roleDeletePatch = async (req, res) => {
  try {
    const id = req.params.id;

    const deletedRole = await Role.findOne({
      _id: id
    })

    await Role.updateOne({
      _id: id,
    }, {
      deleted: true,
      deletedAt: Date.now(),
      deletedBy: req.account.id,
      updatedBy: req.account.id
    })

    req.flash('success', `Xóa nhóm quyền "${deletedRole.name}" thành công!`);

    res.json({
      code: "success",
    });
  } catch (error) {
      res.json({
        code: "error",
        message: error
      })
  }
}

module.exports.roleChangeMultiPatch = async (req, res) => {
  try {
    const { option, ids } = req.body;
    switch (option) {
      case "delete":
        await Role.updateMany({
          _id: { $in: ids }
        }, {
          deleted: true,
          deletedAt: Date.now(),
          deletedBy: req.account.id,
          updatedBy: req.account.id
        });
        req.flash('success', `Xóa thành công!`);
        break;
      
    }

    res.json({
      code: "success"
    });
  } catch (error) {
      res.json({
        code: "error",
        message: "Cập nhật không thành công!"
      })
  }
}