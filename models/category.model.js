const mongoose = require('mongoose');
const slug = require('mongoose-slug-updater');
mongoose.plugin(slug);

const schema = new mongoose.Schema({
  name: String,
  parent: String,
  position: Number,
  avatar: String,
  description: String,
  createdBy: String,
  updatedBy: String,
  status: String,
  slug: { 
    type: String, 
    slug: "name",
    unique: true 
  }, // Đường dẫn link web
  deleted: {
    type: Boolean,
    default: false
  },
  deletedBy: String,
  deletedAt: String
}, {
  timestamps: true // Tự động sinh ra trường CreatedAt và UpdatedAt
});

const Category = mongoose.model('Category', schema, "categories");

module.exports = Category;