const moment = require("moment");
const Category = require("../../models/category.model");
const Tour = require("../../models/tour.model")
const City = require("../../models/city.model")

module.exports.list = async (req, res) => {
    // Breadcrumb
    const slug = req.params.slug;

    const category = await Category.findOne({
        slug: slug,
        deleted: false,
        status: "active"
    })

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

    if (category && category.parent) {
        const parentCategory = await Category.findOne({
            _id: category.parent,
            deleted: false,
            status: "active"
        })

        if (parentCategory) {
            breadcrumb.list.push({
                link: `/category/${parentCategory.slug}`,
                title: parentCategory.name
            })
        }
    }

    if (category) {
        breadcrumb.list.push({
            link: `/category/${category.slug}`,
            title: category.name
        })

        breadcrumb.image = category.avatar;
        breadcrumb.title = category.name;
    }

    // Danh sách tour
    const allCategoryChildren = [];

    const getCategoryChildren = async (parentId) => {
    const childs = await Category.find({
        parent: parentId,
        status: "active",
        deleted: false
    })

    for (const child of childs) {
        allCategoryChildren.push(child.id);

        await getCategoryChildren(child.id);
    }
    }

    await getCategoryChildren(category.id);

    const tourListSection9 = await Tour.find({
        category: { $in: [category.id, ...allCategoryChildren] },
        deleted: false,
        status: "active"
     }).sort({
        position: "desc"
    })

    for(const item of tourListSection9) {
        item.discount = parseInt((item.priceAdult - item.priceNewAdult) / item.priceAdult * 100);
        item.departureDateFormatted = moment(item.departureDate).format("DD/MM/YYYY ")
    }

    // Danh sách thành phố
    const cityList = await City.find({});

    res.render("client/pages/tour-list", {
        pageTitle: "Danh sách tour",
        breadcrumb: breadcrumb,
        category: category,
        tourListSection9: tourListSection9,
        cityList: cityList
    });
}