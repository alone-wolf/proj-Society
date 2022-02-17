var express = require("express");
var path = require("path");
var cookieParser = require("cookie-parser");
var logger = require("morgan");
const { routers } = require("./config.json");
const authMiddleware = require("./middleware/auth");

const sequelize = require("./sequelize/sequelize");
sequelize.sync({ force: false });

var app = express();
var { socketio } = require('./socketio/socketio')

app.use(logger("dev"));
app.use(express.json({ limit: "10mb" }));
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

const societyRouter = require("./routes/society");
app.use("/society", societyRouter.apiRouter);
const userRouter = require("./routes/user");
app.use("/user", userRouter.apiRouter);
const pictureRouter = require("./routes/picture");
app.use("/pic", pictureRouter.apiRouter);

app.use("/", express.static(path.join(__dirname, "public")));

module.exports = app;
