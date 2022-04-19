var express = require("express");
var path = require("path");
var cookieParser = require("cookie-parser");
var logger = require("morgan");

const sequelize = require("./sequelize/sequelize");
sequelize.sync({ force: false });

var app = express();
const { STATUS_403 } = require("./utils/return_data");

var { socketio } = require('./socketio/socketio')

app.use(logger("dev"));
app.use(express.json({ limit: "10mb" }));
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

const { checkAdminToken } = require("./middleware/auth");


const societyRouter = require("./routes/society/router");
app.use("/society", societyRouter.apiRouter);
const userRouter = require("./routes/user/router");
app.use("/user", userRouter.apiRouter);
const pictureRouter = require("./routes/picture/router");
app.use("/pic", pictureRouter.apiRouter);
const collegeRouter = require("./routes/college/router");
app.use("/college", collegeRouter.apiRouter);
const adminRouter = require("./routes/admin/router");
app.use("/admin", checkAdminToken, adminRouter.apiRouter);
const allRouter = require("./routes/all/router");
app.use('/all', checkAdminToken, allRouter.apiRouter);

app.use("/", express.static(path.join(__dirname, "public")));

module.exports = app;
