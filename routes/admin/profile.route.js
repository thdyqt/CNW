const router = require("express").Router();
const profileController  = require("../../controllers/admin/profile.controller")
const profileValidate  = require("../../validates/admin/profile.validate")
const cloudinaryHelper = require("../../helpers/cloudinary.helper")
const multer  = require('multer')
const upload = multer({ storage: cloudinaryHelper.storage })

router.get('/edit', profileController.edit);
router.patch('/edit/:id', upload.single('avatar'), profileValidate.editPatch, profileController.editPatch);

router.get('/change-password', profileController.changePassword);
router.patch('/change-password', profileValidate.changePasswordPatch, profileController.changePasswordPatch);

module.exports = router;