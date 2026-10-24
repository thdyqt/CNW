const mongoose = require('mongoose');
const slug = require('mongoose-slug-updater');
mongoose.plugin(slug);

const schema = new mongoose.Schema(
  {
    fullName: String,
    email: String,
    phone: String,
    role: String,
    position: String,
    status: String,
    password: String,
    avatar: String,
    createdBy: String,
    updatedBy: String,
    slug: { 
      type: String, 
      slug: "fullName",
      unique: true 
    }, // Đường dẫn link web
    deleted: {
      type: Boolean,
      default: false
    },
    deletedBy: String,
    deletedAt: Date
  },
  {
    timestamps: true, // Tự động sinh ra trường createdAt và updatedAt
  }
);

const AccountAdmin = mongoose.model('AccountAdmin', schema, "accounts-admin");

module.exports = AccountAdmin;