const express = require("express");
const Picture = require("../../model/picture");
const apiRouter = express.Router();
const logicRouter = express.Router();
const Society = require("../../model/society");
const User = require("../../model/user");

const STATUS = require("../../utils/return_data");

apiRouter.post("/user/list", (req, res, next) => {
    User.findAll().then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/list", (req, res, next) => {
    Society.findAll().then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/pic/list", (req, res, next) => {
    Picture.findAll().then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

let userRegisterAllow = require("../../config.json").userRegisterAllow
apiRouter.post("/user/register/allow", (req, res, next) => {
    res.json(STATUS.STATUS_200({ userRegisterAllow }));
});


module.exports = { apiRouter };
