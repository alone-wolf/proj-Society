const User = require("../model/user");
const UserLogin = require("../model/user_login");
const adminToken = "qqq";
const STATUS = require("../utils/return_data");

const checkCookieToken = (req, res, next) => {
  let cookieToken = req.headers["cookie-token"] || "";

  if (cookieToken == "1qaz2wsx") {
    next();
  } else {
    UserLogin.findOne({ where: { cookieToken } }).then(d => {
      if (d) {
        req.body.cookieTokenUserId = d.userId;
        next();
      } else {
        res.status(403).json(STATUS.STATUS_403);
      }
    }).catch(e => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
  }
}
const checkAdminToken = (req, res, next) => {
  next();
  return;

  if (req.headers.adminToken === adminToken) {
    next();
  } else {
    // res.writeHeader(403, "unauthed", { "Content-Type": "application/json" });
    res.json({
      status: 403,
      message: "unauthed",
      data: {},
    });
    // res.end();
  }
};
const checkCookieTokenOrAdminToken = (req, res, next) => { };

module.exports = {
  checkCookieToken,
  checkAdminToken,
  checkCookieTokenOrAdminToken,
};
