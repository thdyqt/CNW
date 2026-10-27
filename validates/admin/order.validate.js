const Joi = require('joi');

module.exports.editPatch = (req, res, next) => {
    const schema = Joi.object({
        fullName: Joi.string().required().min(5).max(50)
            .messages({
                "string.empty": "Vui lòng nhập họ tên đầy đủ!",
                "string.min": "Họ tên phải có ít nhất 5 kí tự!",
                "string.max": "Họ tên không được vượt quá 50 kí tự!"
            }),
        
        phone: Joi.string().required().pattern(/(84|0[3|5|7|8|9])+([0-9]{8})\b/)
            .messages({
                "string.empty": "Vui lòng nhập số điện thoại!",
                "string.pattern.base": "Số điện thoại không đúng định dạng!",
            }),
        note: Joi.string().allow(""),
        paymentMethod: Joi.string().allow(""),
        paymentStatus: Joi.string().allow(""),
        status: Joi.string().allow("")
    });

    const { error } = schema.validate(req.body);

    if (error) {
        const errorMessage = error.details[0].message;
        return res.json({
            code: "error",
            message: errorMessage
        });
    }

    next();
}
