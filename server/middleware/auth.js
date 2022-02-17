const User = require("../model/user");
const UserLogin = require("../model/user_login");
const adminToken = "qqq";
const STATUS = require("../utils/return_data");

const checkCookieToken = (req, res, next) => {
  let userId = req.headers.authuserid;
  let cookieToken = req.headers.cookietoken;
  if (!userId || !cookieToken) {
    res.status(400).json(STATUS.STATUS_400)
    return;
  }
  UserLogin.findOne({ where: { userId, cookieToken } })
    .then((data) => {
      if (data) {
        next();
      } else {
        res.status(403).json(STATUS.STATUS_403);
      }
    })
    .catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
};
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
