const moment = require("moment");
const Tour = require("../../models/tour.model")
const Category = require("../../models/category.model")
const City = require("../../models/city.model")

module.exports.detail = async (req, res) => {
  // Breadcrumb
  const slug = req.params.slug;

  const tourDetail = await Tour.findOne({
    slug: slug,
    deleted: false,
    status: "active"
  })

  if (tourDetail) {
    const breadcrumb = {
      image: "",
      title: "",
      list: [
        {
          link: "/",
          title: "Trang Chủ"
        }
      ]
    }

    const category = await Category.findOne({
      _id: tourDetail.category,
      deleted: false,
      status: "active"
    })

    if (category && category.parent) {
      const parentCategory = await Category.findOne({
        _id: category.parent,
        deleted: false,
        status: "active"
      })

      if (parentCategory) {
        breadcrumb.list.push({
          link: `/category/${parentCategory.slug}`,
          title: parentCategory.name,
          tourDetail: tourDetail
        })
      }
    }

    if (category) {
      breadcrumb.list.push({
        link: `/category/${category.slug}`,
        title: category.name
      })
    }

    breadcrumb.list.push({
      link: `/tour/detail/${slug}`,
      title: tourDetail.name
    })

    breadcrumb.image = tourDetail.avatar;
    breadcrumb.title = tourDetail.name;

    tourDetail.departureDateFormatted = moment(tourDetail.departureDate).format("DD/MM/YYYY");

    const cityList = await City.find({
      _id: { $in: tourDetail.locations }
    })

    res.render("client/pages/tour-detail", {
      pageTitle: "Chi tiết tour",
      tourDetail: tourDetail,
      cityList: cityList,
      breadcrumb: breadcrumb
    })
  } else {
    res.redirect("/");
  }
}