const UserLogin = require("../model/user_login");
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
const adminToken = "asdfghjkjhgfdchvbjkuyfvghjb";

const checkAdminToken = (req, res, next) => {
  if (req.headers["admin-token"] == adminToken) {
    next()
  } else {
    res.status(403).json(STATUS_403);
  }
}

module.exports = {
  checkCookieToken,
  checkAdminToken,
};
