const moment = require("moment");
const slugify = require('slugify');
const Category = require("../../models/category.model");
const AccountAdmin = require("../../models/account-admin.model");
const categoryHelper = require("../../helpers/category.helper")

module.exports.list = async (req, res) => {
  const find = {
    deleted: false
  };

  // Lọc theo trạng thái
  if (req.query.status) {
    find.status = req.query.status;
  }

  // Lọc theo người tạo
  if (req.query.createdBy) {
    find.createdBy = req.query.createdBy;
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
  if (req.query.page) {
    const currentPage = parseInt(req.query.page);
    if (currentPage > 0) page = parseInt(req.query.page);
  }

  const totalRecord = await Category.countDocuments(find);
  const totalPage = Math.ceil(totalRecord / limitItems);

  const skip = (page - 1) * limitItems;

  const pagination = {
    skip: skip,
    totalRecord: totalRecord,
    totalPage: totalPage
  }

  const categoryList = await Category
    .find(find)
    .sort({
      position: "desc"
    })
    .limit(limitItems)
    .skip(skip)
  
  for (const item of categoryList) {
    if (item.createdBy) {
      const infoAccountCreated = await AccountAdmin.findOne({
        _id: item.createdBy
      })

      item.createdByFullName = infoAccountCreated ? infoAccountCreated.fullName : "Tài khoản đã xóa";
    }

    if (item.updatedBy) {
      const infoAccountUpdated = await AccountAdmin.findOne({
        _id: item.updatedBy
      })

      item.updatedByFullName = infoAccountUpdated ? infoAccountUpdated.fullName : "Tài khoản đã xóa";
    }

    item.createdAtFormatted = moment(item.createdAt).format("HH:mm - DD/MM/YYYY");
    item.updatedAtFormatted = moment(item.updatedAt).format("HH:mm - DD/MM/YYYY");
  }

  // Danh sách tài khoản quản trị
  const accountAdminList = await AccountAdmin.find({}).select("id fullName");

  res.render("admin/pages/category-list", {
    pageTitle: "Quản lý danh mục",
    categoryList: categoryList,
    accountAdminList: accountAdminList,
    pagination: pagination
  });
}

module.exports.create = async (req, res) => {
  const categoryList = await Category.find({
    deleted: false
  })

  const categoryTree = categoryHelper.buildCategoryTree(categoryList, "");

  res.render("admin/pages/category-create", {
    pageTitle: "Tạo danh mục",
    categoryList: categoryTree
  });
}

module.exports.createPost = async (req, res) => {
  if(req.role.permissions.includes("category-create")) {
    if (req.body.position) {
      req.body.position = parseInt(req.body.position);
    } else {
      const totalRecord = await Category.countDocuments({});
      req.body.position = totalRecord + 1;
    }

    req.body.createdBy = req.account.id;
    req.body.updatedBy = req.account.id;
    req.body.avatar = req.file ? req.file.path : "";

    const newRecord = new Category(req.body);
    await newRecord.save();

    req.flash('success', `Tạo danh mục "${req.body.name}" thành công!`);

    res.json({
      code: "success"
    });
  } else {
    res.json({
      code: "error"
    });
  }
}

module.exports.edit = async (req, res) => {
  try {
    const categoryList = await Category.find({
      deleted: false
    })

    const categoryTree = categoryHelper.buildCategoryTree(categoryList, "");

    const id = req.params.id;

    const currentCategory = await Category.findOne({
      _id: id,
      deleted: false
    })

    res.render("admin/pages/category-edit", {
      pageTitle: "Chỉnh sửa danh mục",
      categoryList: categoryTree,
      currentCategory: currentCategory
    });
  } catch (error) {
    res.redirect(`/${pathAdmin}/category/list`);
  }
}

module.exports.editPatch = async (req, res) => {
  try {
    if(req.role.permissions.includes("category-edit")) {
      const id = req.params.id;

      if (req.body.position) {
        req.body.position = parseInt(req.body.position);
      } else {
        const totalRecord = await Category.countDocuments({});
        req.body.position = totalRecord + 1;
      }

      req.body.updatedBy = req.account.id;

      if (req.file) {
        req.body.avatar = req.file.path;
      } else {
        if (req.body.avatar === "deleted") {
          req.body.avatar = ""; 
        } else {
          delete req.body.avatar;
        }
      }

      await Category.updateOne({
        _id: id,
        deleted: false
      }, req.body)

      req.flash('success', `Cập nhật danh mục "${req.body.name}" thành công!`);

      res.json({
        code: "success",
      });
    } else {
      res.json({
        code: "error",
      })
    }
  } catch (error) {
      res.json({
        code: "error",
        message: error
      })
  }
}

module.exports.deletePatch = async (req, res) => {
  try {
    if(req.role.permissions.includes("category-delete")) {
      const id = req.params.id;

      const deletedCategory = await Category.findOne({
        _id: id
      })

      await Category.updateOne({
        _id: id,
      }, {
        deleted: true,
        deletedAt: Date.now(),
        deletedBy: req.account.id,
        updatedBy: req.account.id
      })

      req.flash('success', `Xóa danh mục "${deletedCategory.name}" thành công!`);

      res.json({
        code: "success",
      });
    } else {
      res.json({
        code: "error"
      });
    }
  } catch (error) {
      res.json({
        code: "error",
        message: error
      })
  }
}

module.exports.changeMultiPatch = async (req, res) => {
  try {
    const { option, ids } = req.body;
    switch (option) {
      case "active":
      case "inactive":
        await Category.updateMany({
          _id: { $in: ids }
        }, {
          status: option,
          updatedBy: req.account.id
        });
        req.flash('success', `Đổi trạng thái thành công!`);
        break;
      case "delete":
        await Category.updateMany({
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