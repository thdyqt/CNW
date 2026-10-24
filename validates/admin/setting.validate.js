const Joi = require('joi');

module.exports.websiteInfoPatch = (req, res, next) => {
    const schema = Joi.object({
        websiteName: Joi.string().required()
            .messages({
                "string.empty": "Vui lòng nhập tên Website!",
            }),
        
        email: Joi.string().email()
            .messages({
                "string.email": "Email không đúng định dạng!"
            }),
        phone: Joi.string().allow(''),
        address: Joi.string().allow(''),
        logo: Joi.string().allow(''),
        favicon: Joi.string().allow(''),
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

module.exports.accountAdminCreatePost = (req, res, next) => {
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
        phone: Joi.string().required().pattern(/(84|0[3|5|7|8|9])+([0-9]{8})\b/)
            .messages({
                "string.empty": "Vui lòng nhập số điện thoại!",
                "string.pattern.base": "Số điện thoại không đúng định dạng!",
            }),
        position: Joi.string().required()
            .messages({
                "string.empty": "Vui lòng nhập chức vụ!",
            }),
        password: Joi.string().required().pattern(/^(?=.*[A-Za-z])(?=.*\d).{8,}$/)
            .messages({
                "string.empty": "Vui lòng nhập mật khẩu của bạn!",
                "string.pattern.base": "Mật khẩu tối thiểu 8 kí tự, ít nhất một chữ cái và một chữ số."
            }),
        
        role: Joi.string().allow(""),
        status: Joi.string().allow(""),
        avatar: Joi.string().allow("")
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

module.exports.accountAdminEditPatch = (req, res, next) => {
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
        phone: Joi.string().required().pattern(/(84|0[3|5|7|8|9])+([0-9]{8})\b/)
            .messages({
                "string.empty": "Vui lòng nhập số điện thoại!",
                "string.pattern.base": "Số điện thoại không đúng định dạng!",
            }),
        position: Joi.string().required()
            .messages({
                "string.empty": "Vui lòng nhập chức vụ!",
            }),
        
        password: Joi.string().allow(""),
        role: Joi.string().allow(""),
        status: Joi.string().allow(""),
        avatar: Joi.string().allow("")
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

module.exports.roleCreatePost = (req, res, next) => {
    const schema = Joi.object({
        name: Joi.string().required()
            .messages({
                "string.empty": "Vui lòng nhập tên nhóm quyền!",
            }),
        description: Joi.string().allow(''),
        permissions: Joi.array().allow('')
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
