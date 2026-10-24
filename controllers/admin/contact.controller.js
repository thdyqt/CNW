const moment = require("moment");
const Contact = require("../../models/contact.model");

module.exports.list = async (req, res) => {
  const find = {
    deleted: false
  };

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
    const keywordRegex = new RegExp(req.query.keyword, "i"); 
    find.email = keywordRegex; 
  }

  // Phân trang
  const limitItems = 10;
  let page = 1;
  if (req.query.page) {
    const currentPage = parseInt(req.query.page);
    if (currentPage > 0) page = parseInt(req.query.page);
  }

  const totalRecord = await Contact.countDocuments(find);
  const totalPage = Math.ceil(totalRecord / limitItems);

  const skip = (page - 1) * limitItems;

  const pagination = {
    skip: skip,
    totalRecord: totalRecord,
    totalPage: totalPage
  }

  const contactList = await Contact
    .find(find)
    .sort({
      position: "desc"
    })
    .limit(limitItems)
    .skip(skip)
  
  for (const item of contactList) {
    item.createdAtFormatted = moment(item.createdAt).format("HH:mm - DD/MM/YYYY");
  }

  res.render("admin/pages/contact-list", {
    pageTitle: "Thông tin liên hệ",
    contactList: contactList,
    pagination: pagination
  });
}

module.exports.deletePatch = async (req, res) => {
  try {
    const id = req.params.id;

    const deletedContact = await Contact.findOne({
      _id: id
    })

    await Contact.updateOne({
      _id: id,
    }, {
      deleted: true,
      deletedAt: Date.now(),
      deletedBy: req.account.id,
    })

    req.flash('success', `Xóa thông tin liên hệ "${deletedContact.email}" thành công!`);

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