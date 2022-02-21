const express = require("express");
const apiRouter = express.Router();

const STATUS = require("../../../../utils/return_data");
const BBSUserWatch = require("../../../../model/bbs_user_watch");

// certain bbs watch list
apiRouter.post("/list", (req, res, next) => {
    let societyId = req.body.societyId;

    BBSUserWatch.findAll({ where: { societyId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

// watch certain bbs, with userId
apiRouter.post("/create", (req, res, next) => {
    let userId = req.body.userId;
    let societyId = req.body.societyId;

    BBSUserWatch.findOrCreate({ where: { userId, societyId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/delete", (req, res, next) => {
    let userId = req.body.userId;
    let societyId = req.body.societyId;

    BBSUserWatch.destroy({ where: { userId, societyId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

module.exports = { apiRouter };
