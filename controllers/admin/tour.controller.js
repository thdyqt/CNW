const moment = require("moment");
const slugify = require('slugify');
const Tour = require("../../models/tour.model");
const Category = require("../../models/category.model");
const AccountAdmin = require("../../models/account-admin.model");
const City = require("../../models/city.model");
const categoryHelper = require("../../helpers/category.helper");

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

  const totalRecord = await Tour.countDocuments(find);
  const totalPage = Math.ceil(totalRecord / limitItems);

  const skip = (page - 1) * limitItems;

  const pagination = {
    skip: skip,
    totalRecord: totalRecord,
    totalPage: totalPage
  }

  const tourList = await Tour
    .find(find)
    .sort({
      position: "desc"
    })
    .limit(limitItems)
    .skip(skip)
  
  for (const item of tourList) {
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

  // Danh sách danh mục
  const categoryList = await Category.find({}).select("id name");
  const categoryTree = categoryHelper.buildCategoryTree(categoryList, "");

  res.render("admin/pages/tour-list", {
    pageTitle: "Quản lý tour",
    tourList: tourList,
    accountAdminList: accountAdminList,
    categoryList: categoryTree,
    pagination: pagination
  });
}

module.exports.create = async (req, res) => {
  const categoryList = await Category.find({
      deleted: false
  });
  
  const categoryTree = categoryHelper.buildCategoryTree(categoryList, "");

  const cityList = await City.find({});

  res.render("admin/pages/tour-create", {
    pageTitle: "Tạo tour",
    categoryList: categoryTree,
    cityList: cityList
  });
}

module.exports.createPost = async (req, res) => {
  if(req.role.permissions.includes("tour-create")) {
    if (req.body.position) {
      req.body.position = parseInt(req.body.position);
    } else {
      const totalRecord = await Tour.countDocuments({});
      req.body.position = totalRecord + 1;
    }

    req.body.createdBy = req.account.id;
    req.body.updatedBy = req.account.id;
    
    if (req.files && req.files.avatar && req.files.avatar.length > 0) {
      req.body.avatar = req.files.avatar[0].path; 
    } else {
      req.body.avatar = "";
    }

    if (req.files && req.files.images && req.files.images.length > 0) {
      req.body.images = req.files.images.map(file => file.path); 
    } else {
      delete req.body.images; 
    }

    req.body.priceAdult = req.body.priceAdult ? parseInt(req.body.priceAdult) : 0;
    req.body.priceChildren = req.body.priceChildren ? parseInt(req.body.priceChildren) : 0;
    req.body.priceBaby = req.body.priceBaby ? parseInt(req.body.priceBaby) : 0;
    req.body.priceNewAdult = req.body.priceNewAdult ? parseInt(req.body.priceNewAdult) : 0;
    req.body.priceNewChildren = req.body.priceNewChildren ? parseInt(req.body.priceNewChildren) : 0;
    req.body.priceNewBaby = req.body.priceNewBaby ? parseInt(req.body.priceNewBaby) : 0;
    req.body.stockAdult = req.body.stockAdult ? parseInt(req.body.stockAdult) : 0;
    req.body.stockChildren = req.body.stockChildren ? parseInt(req.body.stockChildren) : 0;
    req.body.stockBaby = req.body.stockBaby ? parseInt(req.body.stockBaby) : 0;
    req.body.locations = req.body.locations ? JSON.parse(req.body.locations) : [];
    req.body.departureDate = req.body.departureDate ? new Date(req.body.departureDate) : null;
    req.body.schedules = req.body.schedules ? JSON.parse(req.body.schedules) : [];

    const newRecord = new Tour(req.body);
    await newRecord.save();

    req.flash('success', `Tạo tour "${req.body.name}" thành công!`);

    res.json({
      code: "success"
    });
  } else {
    res.json({
      code: "error"
    })
  }
}

module.exports.edit = async (req, res) => {
  try {
    const categoryList = await Category.find({
      deleted: false
    })

    const categoryTree = categoryHelper.buildCategoryTree(categoryList, "");

    const id = req.params.id;

    const currentTour = await Tour.findOne({
      _id: id,
      deleted: false
    });

    const cityList = await City.find({});
    currentTour.departureDateFormatted = moment(currentTour.departureDate).format('YYYY-MM-DD');

    res.render("admin/pages/tour-edit", {
      pageTitle: "Chỉnh sửa tour",
      categoryList: categoryTree,
      cityList: cityList,
      currentTour: currentTour
    });
  } catch (error) {
    res.redirect(`/${pathAdmin}/tour/list`);
  }
}

module.exports.editPatch = async (req, res) => {
  try {
    if(req.role.permissions.includes("tour-edit")) {
      const id = req.params.id;

      if (req.body.position) {
        req.body.position = parseInt(req.body.position);
      } else {
        const totalRecord = await Tour.countDocuments({});
        req.body.position = totalRecord + 1;
      }

      req.body.createdBy = req.account.id;
      req.body.updatedBy = req.account.id;

      if (req.files && req.files.avatar && req.files.avatar.length > 0) {
        req.body.avatar = req.files.avatar[0].path; 
      } else {
        if (req.body.avatar === "deleted") {
          req.body.avatar = "";
        } else {
          delete req.body.avatar; 
        }
      }

      const newImages = (req.files && req.files.images) ? req.files.images.map(file => file.path) : [];

      let oldImages = [];
      if (req.body.images) {
          oldImages = Array.isArray(req.body.images) ? req.body.images : [req.body.images];
      }

      req.body.images = [...oldImages, ...newImages];

      req.body.priceAdult = req.body.priceAdult ? parseInt(req.body.priceAdult) : 0;
      req.body.priceChildren = req.body.priceChildren ? parseInt(req.body.priceChildren) : 0;
      req.body.priceBaby = req.body.priceBaby ? parseInt(req.body.priceBaby) : 0;
      req.body.priceNewAdult = req.body.priceNewAdult ? parseInt(req.body.priceNewAdult) : 0;
      req.body.priceNewChildren = req.body.priceNewChildren ? parseInt(req.body.priceNewChildren) : 0;
      req.body.priceNewBaby = req.body.priceNewBaby ? parseInt(req.body.priceNewBaby) : 0;
      req.body.stockAdult = req.body.stockAdult ? parseInt(req.body.stockAdult) : 0;
      req.body.stockChildren = req.body.stockChildren ? parseInt(req.body.stockChildren) : 0;
      req.body.stockBaby = req.body.stockBaby ? parseInt(req.body.stockBaby) : 0;
      req.body.locations = req.body.locations ? JSON.parse(req.body.locations) : [];
      req.body.departureDate = req.body.departureDate ? new Date(req.body.departureDate) : null;
      req.body.schedules = req.body.schedules ? JSON.parse(req.body.schedules) : [];
      
      await Tour.updateOne({
        _id: id,
        deleted: false
      }, req.body)

      req.flash('success', `Cập nhật tour "${req.body.name}" thành công!`);

      res.json({
        code: "success",
      });
    } else {
      res.json({
        code: "error"
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
    if(req.role.permissions.includes("tour-delete")) {
      const id = req.params.id;

      const deletedTour = await Tour.findOne({
        _id: id
      })

      await Tour.updateOne({
        _id: id,
      }, {
        deleted: true,
        deletedAt: Date.now(),
        deletedBy: req.account.id,
        updatedBy: req.account.id
      })

      req.flash('success', `Xóa danh mục "${deletedTour.name}" thành công!`);

      res.json({
        code: "success",
      });
    } else {
      res.json({
        code: "error"
      })
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
        await Tour.updateMany({
          _id: { $in: ids }
        }, {
          status: option,
          updatedBy: req.account.id
        });
        req.flash('success', `Đổi trạng thái thành công!`);
        break;
      case "delete":
        await Tour.updateMany({
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

module.exports.trash = async (req, res) => {
  const find = {
    deleted: true
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

  const totalRecord = await Tour.countDocuments(find);
  const totalPage = Math.ceil(totalRecord / limitItems);

  const skip = (page - 1) * limitItems;

  const pagination = {
    skip: skip,
    totalRecord: totalRecord,
    totalPage: totalPage
  }

  const tourList = await Tour
    .find(find)
    .sort({
      position: "desc"
    })
    .limit(limitItems)
    .skip(skip)
  
  for (const item of tourList) {
    if (item.createdBy) {
      const infoAccountCreated = await AccountAdmin.findOne({
        _id: item.createdBy
      })

      item.createdByFullName = infoAccountCreated.fullName;
    }

    if (item.deletedBy) {
      const infoAccountCreated = await AccountAdmin.findOne({
        _id: item.deletedBy
      })

      item.deletedByFullName = infoAccountCreated.fullName;
    }

    item.createdAtFormatted = moment(item.createdAt).format("HH:mm - DD/MM/YYYY");
    item.deletedAtFormatted = moment(item.updatedAt).format("HH:mm - DD/MM/YYYY");
  }

  res.render("admin/pages/tour-trash", {
    pageTitle: "Thùng rác tour",
    tourList: tourList,
    pagination: pagination
  });
}

module.exports.undoPatch = async (req, res) => {
  try {
    if(req.role.permissions.includes("tour-trash")) {
      const id = req.params.id;

      const recoveredTour = await Tour.findOne({
        _id: id
      })

      await Tour.updateOne({
        _id: id,
      }, {
        deleted: false,
        updatedAt: Date.now(),
        updatedBy: req.account.id
      })

      req.flash('success', `Khôi phục tour "${recoveredTour.name}" thành công!`);

      res.json({
        code: "success",
      });
    } else {
      res.json({
        code: "error"
      })
    }
  } catch (error) {
      res.json({
        code: "error",
        message: error
      })
  }
}

module.exports.deleteDestroyPatch = async (req, res) => {
  try {
    if(req.role.permissions.includes("tour-trash")) {
      const id = req.params.id;

      const destroyedTour = await Tour.findOne({
        _id: id
      })

      await Tour.deleteOne({
        _id: id,
      })

      req.flash('success', `Xóa vĩnh viễn danh mục "${destroyedTour.name}" thành công!`);

      res.json({
        code: "success",
      });
    } else {
      res.json({
        code: "error"
      })
    }
  } catch (error) {
      res.json({
        code: "error",
        message: error
      })
  }
}

module.exports.trashChangeMultiPatch = async (req, res) => {
  try {
    const { option, ids } = req.body;
    switch (option) {
      case "undo":
        await Tour.updateMany({
          _id: { $in: ids }
        }, {
          deleted: false,
          updatedAt: Date.now(),
          updatedBy: req.account.id
        });
        req.flash('success', `Khôi phục tour thành công!`);
        break;
      case "delete-destroy":
        await Tour.deleteMany({
          _id: { $in: ids }
        });
        req.flash('success', `Xóa vĩnh viễn tour thành công!`);
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