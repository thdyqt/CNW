const moment = require("moment");
const Tour = require("../../models/tour.model");
const Order = require("../../models/order.model");

module.exports.list = async (req, res) => {
  const find = {
    deleted: false
  };

  const orderList = await Order
    .find(find)
    .sort({
      createdAt: "desc"
    })
  
  for (const orderDetail of orderList) {
    for (const item of orderDetail.items) {
      const tourInfo = await Tour.findOne({
        _id: item.tourId
      });

      if (tourInfo) {
        item.avatar = tourInfo.avatar;
        item.name = tourInfo.name;
      }
    }

    switch (orderDetail.paymentMethod) {
            case "money":
                orderDetail.paymentMethodName = "Thanh toán tiền mặt";
                break;
            case "momo":
                orderDetail.paymentMethodName = "Ví MoMo";
                break;
            case "bank":
                orderDetail.paymentMethodName = "Chuyển khoản ngân hàng";
                break;
        }

    switch (orderDetail.paymentStatus) {
        case "unpaid":
            orderDetail.paymentStatusName = "Chưa thanh toán";
            break;
        case "paid":
            orderDetail.paymentStatusName = "Đã thanh toán";
            break;
    }

    switch (orderDetail.status) {
        case "initial":
            orderDetail.statusName = "Khởi tạo";
            break;
        case "done":
            orderDetail.statusName = "Hoàn thành";
            break;
        case "cancel":
            orderDetail.statusName = "Hủy";
            break;
    }

    orderDetail.createdAtFormattedTime = moment(orderDetail.createdAt).format("HH:mm");
    orderDetail.createdAtFormattedDate = moment(orderDetail.createdAt).format("DD/MM/YYYY");
  }

  res.render("admin/pages/order-list", {
    pageTitle: "Quản lý đơn hàng",
    orderList: orderList
  });
}

module.exports.edit = async (req, res) => {
  try {
    const id = req.params.id;

    const orderDetail = await Order.findOne({
      _id: id
    })
    res.render("admin/pages/order-edit", {
      pageTitle: "Đơn hàng: OD000001"
    });
  } catch (error) {
    res.redirect(`${pathAdmin}/order/list`)
  }
}