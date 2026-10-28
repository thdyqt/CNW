const Order = require("../../models/order.model");
const AccountAdmin = require("../../models/account-admin.model")

module.exports.dashboard = async (req, res) => {
  // Thông số tổng quan
  const overview = {
    totalUser: 0,
    totalAdmin: 0,
    totalOrder: 0,
    totalRevenue: 0
  }

  overview.totalAdmin = await AccountAdmin.countDocuments({
    deleted: false
  });

  const orderList = await Order.find({
    deleted: false
  });

  overview.totalOrder = orderList.length;
  overview.totalRevenue = orderList.reduce((sum, item) => sum + item.total, 0);

  res.render("admin/pages/dashboard", {
    pageTitle: "Tổng quan",
    overview: overview
  });
}

module.exports.revenueChartPost = async (req, res) => {
  const {currentMonth, currentYear, previousMonth, previousYear, arrayDay} = req.body;

  const orderCurrentMonth = await Order.find({
    deleted: false,
    paymentStatus: "paid",
    createdAt: {
      $gte: new Date(currentYear, currentMonth - 1, 1),
      $lt: new Date(currentYear, currentMonth, 1) 
    }
  })

  const orderPreviousMonth = await Order.find({
    deleted: false,
    paymentStatus: "paid",
    createdAt: {
      $gte: new Date(previousYear, previousMonth - 1, 1),
      $lt: new Date(previousYear, previousMonth, 1) 
    }
  })

  // Tạo mảng doanh thu theo từng ngày
  const dataCurrentMonth = [];
  const dataPreviousMonth = [];

  for (const day of arrayDay) {
    // Tỉm doanh thu của ngày day trong tháng hiện tại
    let revenueCurrent = 0;
    for (const order of orderCurrentMonth) {
      const orderDate = new Date(order.createdAt).getDate();
      if (orderDate == day) {
        revenueCurrent += order.total;
      }
    }
    dataCurrentMonth.push(revenueCurrent);

    // Tỉm doanh thu của ngày day trong tháng trước
    let revenuePrevious = 0;
    for (const order of orderPreviousMonth) {
      const orderDate = new Date(order.createdAt).getDate();
      if (orderDate == day) {
        revenuePrevious += order.total;
      }
    }
    dataPreviousMonth.push(revenuePrevious);
  }
  
  res.json({
    code: "success",
    dataCurrentMonth: dataCurrentMonth,
    dataPreviousMonth: dataPreviousMonth
  })
}