const express = require('express');
const path = require('path');
require('dotenv').config();
const database = require('./config/database')
const variableConfig = require('./config/variable')
const adminRoutes = require('./routes/admin/index.route')
const clientRoutes = require('./routes/client/index.route')

const app = express();
const port = 3000;

// Kết nối database
database.connect();

// Thiết lập thư mục views và view engine pug
app.set('views', path.join(__dirname, 'views')); // Thư mục chứa file Pug
app.set('view engine', 'pug'); // Thiết lập Pug làm view engine

// Thiết lập thư mục chứa file tĩnh
app.use(express.static(path.join(__dirname, 'public')));

// Tạo biến toàn cục trong file Pug
app.locals.pathAdmin = variableConfig.pathAdmin;

// Thiết lập đường dẫn
app.use(`/${variableConfig.pathAdmin}`, adminRoutes)
app.use("/", clientRoutes)

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`);
});

//ptdntn2907_db_user
//dNEvaui6SSKrvWQz