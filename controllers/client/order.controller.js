const moment = require("moment");
const generateHelper = require("../../helpers/generate.helper");
const Tour = require("../../models/tour.model");
const Order = require("../../models/order.model");
const City = require("../../models/city.model");
const sortHelper = require("../../helpers/sort.helper")

module.exports.createPost = async (req, res) => {
    // Tạo mã đơn hàng
    req.body.code = "OD" + generateHelper.generateRandomNumber(10);

    // Danh sách tour
    for (const item of req.body.items) {
        const infoTour = await Tour.findOne({
            _id: item.tourId
        })

        item.priceNewAdult = infoTour.priceNewAdult;
        item.priceNewChildren = infoTour.priceNewChildren;
        item.priceNewBaby = infoTour.priceNewBaby;

        item.departureDate = infoTour.departureDate;

        // Cập nhật lại số lượng còn lại của Tour
        await Tour.updateOne({
            _id: item.tourId
        }, {
            stockAdult: infoTour.stockAdult - item.quantityAdult,
            stockChildren: infoTour.stockChildren - item.quantityChildren,
            stockBaby: infoTour.stockBaby - item.quantityBaby
        })
    }

    // Thanh toán
    // Tạm tính
    req.body.subTotal = req.body.items.reduce((sum, item) => {
        return sum + item.priceNewAdult * item.quantityAdult + item.priceNewChildren * item.quantityChildren + item.priceNewBaby * item.quantityBaby
    }, 0);

    // Tổng tiền
    req.body.total = req.body.subTotal;

    // Trạng thái thanh toán
    req.body.paymentStatus = "unpaid";

    // Trạng thái đơn hàng
    req.body.status = "initial";

    const newRecord = new Order(req.body);
    await newRecord.save();

    res.json({
        code: "success",
        message: "Đặt hàng thành công!",
        orderCode: req.body.code
    })
}

module.exports.success = async (req, res) => {
    const { orderCode, phone } = req.query;

    const orderDetail = await Order.findOne({
        code: orderCode,
        phone: phone
    });

    if (orderDetail) {
        switch (orderDetail.paymentMethod) {
            case "money":
                orderDetail.paymentMethodName = "Thanh toán tiền mặt";
                break;
            case "vnpay":
                orderDetail.paymentMethodName = "VNPay";
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

        orderDetail.createdAtFormatted = moment(orderDetail.createdAt).format("HH:mm - DD/MM/YYYY");

        for (const item of orderDetail.items) {
            const tourInfo = await Tour.findOne({
                _id: item.tourId
            });

            if (tourInfo) {
                item.avatar = tourInfo.avatar;
                item.name = tourInfo.name;
                item.slug = tourInfo.slug;
                item.departureDateFormatted = moment(orderDetail.departureDate).format("HH:mm - DD/MM/YYYY");
                const city = await City.findOne({
                    _id: item.locationFrom
                });
                item.locationFromName = city.name;
            }
        }

        res.render("client/pages/order-success", {
            pageTitle: "Đặt hàng thành công",
            orderDetail: orderDetail
        });
    } else {
        res.redirect("/");
    }
}

module.exports.paymentVNPay = async(req, res) => {
  const orderCode = req.query.orderCode;

  const orderDetail = await Order.findOne({
    code: orderCode,
    deleted: false,
    paymentStatus: "unpaid"
  });

  if (orderDetail) {
    var ipAddr = req.headers['x-forwarded-for'] ||
        req.connection.remoteAddress ||
        req.socket.remoteAddress ||
        req.connection.socket.remoteAddress;

    var tmnCode = process.env.VNPAY_TMN_CODE;
    var secretKey = process.env.VNPAY_SECRET;
    var vnpUrl = process.env.VNPAY_URL;
    var returnUrl = `${process.env.DOMAIN_WEBSITE}/order/payment-vnpay-result`;

    var createDate = moment().format('YYYYMMDDHHmmss');
    var orderId = `${orderDetail.code}-${Date.now()}`;
    var amount = orderDetail.total;
    var bankCode = "";
    
    var orderInfo = `Thanh toan don hang ${orderDetail.code}`;
    var orderType = "other";
    var locale = "vi"
    var currCode = 'VND';
    var vnp_Params = {};
    vnp_Params['vnp_Version'] = '2.1.0';
    vnp_Params['vnp_Command'] = 'pay';
    vnp_Params['vnp_TmnCode'] = tmnCode;
    // vnp_Params['vnp_Merchant'] = ''
    vnp_Params['vnp_Locale'] = locale;
    vnp_Params['vnp_CurrCode'] = currCode;
    vnp_Params['vnp_TxnRef'] = orderId;
    vnp_Params['vnp_OrderInfo'] = orderInfo;
    vnp_Params['vnp_OrderType'] = orderType;
    vnp_Params['vnp_Amount'] = amount * 100;
    vnp_Params['vnp_ReturnUrl'] = returnUrl;
    vnp_Params['vnp_IpAddr'] = ipAddr;
    vnp_Params['vnp_CreateDate'] = createDate;
    if(bankCode !== null && bankCode !== ''){
        vnp_Params['vnp_BankCode'] = bankCode;
    }

    vnp_Params = sortHelper.sortObject(vnp_Params);

    var querystring = require('qs');
    var signData = querystring.stringify(vnp_Params, { encode: false });
    var crypto = require("crypto");     
    var hmac = crypto.createHmac("sha512", secretKey);
    var signed = hmac.update(Buffer.from(signData, 'utf-8')).digest("hex");
    vnp_Params['vnp_SecureHash'] = signed;
    vnpUrl += '?' + querystring.stringify(vnp_Params, { encode: false });

    res.redirect(vnpUrl)
  }
}

module.exports.paymentVNPayResult = async(req, res) => {
    let vnp_Params = req.query;
    var secureHash = vnp_Params['vnp_SecureHash'];
    
    delete vnp_Params['vnp_SecureHash'];
    delete vnp_Params['vnp_SecureHashType'];

    vnp_Params = sortHelper.sortObject(vnp_Params);
    var secretKey = process.env.VNPAY_SECRET;
    var querystring = require('qs');
    var signData = querystring.stringify(vnp_Params, { encode: false });
    var crypto = require("crypto");     
    var hmac = crypto.createHmac("sha512", secretKey);
    var signed = hmac.update(Buffer.from(signData, 'utf-8')).digest("hex");
        
    if(secureHash === signed){
        if(vnp_Params.vnp_ResponseCode == "00" && vnp_Params.vnp_TransactionStatus == "00") {
            const [ orderCode, date ] = vnp_Params.vnp_TxnRef.split("-");

            const order = await Order.findOneAndUpdate({
                code: orderCode,
                deleted: false
            }, {
                paymentStatus: "paid"
            });

            res.redirect(`${process.env.DOMAIN_WEBSITE}/order/success?orderCode=${orderCode}&phone=${order.phone}`);
        }
    }
    else {
        res.redirect(`/cart`);
    }
}