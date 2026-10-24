const router = require("express").Router();
const orderController = require("../../controllers/client/order.controller")

router.post('/detail', orderController.createPost);

module.exports = router;