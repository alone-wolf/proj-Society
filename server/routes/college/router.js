const express = require("express");
const College = require("../../model/college");
const apiRouter = express.Router();

const STATUS = require("../../utils/return_data");

apiRouter.post("/list", (req, res, next) => {
    College.findAll().then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/create", (req, res, next) => {
    let collegeName = req.body.collegeName;
    let des = req.body.des;
    College.create({ name: collegeName, des })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});

apiRouter.post("/delete", (req, res, next) => {
    let collegeId = req.body.collegeId;
    College.destroy({ where: { id: collegeId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});

apiRouter.post("/update", (req, res, next) => {
    let collegeName = req.body.collegeName;
    let collegeId = req.body.collegeId;
    let des = req.body.des;
    College.update({ name: collegeName, des }, { where: { id: collegeId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
})


module.exports = { apiRouter };
