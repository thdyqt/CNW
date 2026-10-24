const moment = require("moment");
const Tour = require("../../models/tour.model")
const Category = require("../../models/category.model")

module.exports.home = async (req, res) => {
  // Tour List Section 2
  const tourListSection2 = await Tour.find({
    priceNewAdult: { $gt: 0 },
    deleted: false,
    status: "active"
  }).sort({
    position: "desc"
  }).limit(6);

  for(const item of tourListSection2) {
    item.discount = parseInt((item.priceAdult - item.priceNewAdult) / item.priceAdult * 100);
    item.departureDateFormatted = moment(item.departureDate).format("DD/MM/YYYY ")
  }

  // Tour List Section 4
  const categoryIdSection4 = "6abb67b8f2151ca229110659";
  const allCategoryChildrenSection4 = [];

  const getCategoryChildrenSection4 = async (parentId) => {
    const childs = await Category.find({
      parent: parentId,
      status: "active",
      deleted: false
    })

    for (const child of childs) {
      allCategoryChildrenSection4.push(child.id);

      await getCategoryChildrenSection4(child.id);
    }
  }

  await getCategoryChildrenSection4(categoryIdSection4);

  const tourListSection4 = await Tour.find({
    category: { $in: [categoryIdSection4, ...allCategoryChildrenSection4] },
    deleted: false,
    status: "active"
  }).sort({
    position: "desc"
  }).limit(8);

  for(const item of tourListSection4) {
    item.discount = parseInt((item.priceAdult - item.priceNewAdult) / item.priceAdult * 100);
    item.departureDateFormatted = moment(item.departureDate).format("DD/MM/YYYY ")
  }

  // Tour List Section 6
  const categoryIdSection6 = "6abb6ae8c4f085001f225709";
  const allCategoryChildrenSection6 = [];

  const getCategoryChildrenSection6 = async (parentId) => {
    const childs = await Category.find({
      parent: parentId,
      status: "active",
      deleted: false
    })

    for (const child of childs) {
      allCategoryChildrenSection4.push(child.id);

      await getCategoryChildrenSection6(child.id);
    }
  }

  await getCategoryChildrenSection6(categoryIdSection4);

  const tourListSection6 = await Tour.find({
    category: { $in: [categoryIdSection6, ...allCategoryChildrenSection6] },
    deleted: false,
    status: "active"
  }).sort({
    position: "desc"
  }).limit(8);

  for(const item of tourListSection6) {
    item.discount = parseInt((item.priceAdult - item.priceNewAdult) / item.priceAdult * 100);
    item.departureDateFormatted = moment(item.departureDate).format("DD/MM/YYYY ")
  }

  res.render("client/pages/home", {
    pageTitle: "Trang chủ",
    tourListSection2: tourListSection2,
    tourListSection4: tourListSection4,
    tourListSection6: tourListSection6
  });
}