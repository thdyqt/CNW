const Joi = require('joi');

module.exports.registerPost = (req, res, next) => {
    const schema = Joi.object({
        fullName: Joi.string().required().min(5).max(50)
            .messages({
                "string.empty": "Vui lòng nhập họ tên đầy đủ!",
                "string.min": "Họ tên phải có ít nhất 5 kí tự!",
                "string.max": "Họ tên không được vượt quá 50 kí tự!"
            }),
        
        email: Joi.string().required().email()
            .messages({
                "string.empty": "Vui lòng nhập Email của bạn!",
                "string.email": "Email không đúng định dạng!"
            }),
        
        password: Joi.string().required().pattern(/^(?=.*[A-Za-z])(?=.*\d).{8,}$/)
            .messages({
                "string.empty": "Vui lòng nhập mật khẩu của bạn!",
                "string.pattern.base": "Mật khẩu tối thiểu 8 kí tự, ít nhất một chữ cái và một chữ số."
            })
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

module.exports.loginPost = (req, res, next) => {
    const schema = Joi.object({
        email: Joi.string().required().email()
            .messages({
                "string.empty": "Vui lòng nhập Email của bạn!",
                "string.email": "Email không đúng định dạng!"
            }),
        
        password: Joi.string().required()
            .messages({
                "string.empty": "Vui lòng nhập mật khẩu của bạn!",
            }),
        rememberPassword: Joi.boolean()
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