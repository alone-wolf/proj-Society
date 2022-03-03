const express = require("express");
const apiRouter = express.Router();
const Society = require("../../../../model/society");

const STATUS = require("../../../../utils/return_data");
const User = require("../../../../model/user");
const { brdNewActivityRequest } = require("../../../../socketio/socketio");
const SocietyActivityRequest = require("../../../../model/society_activity_request");
const SocietyActivity = require("../../../../model/society_activity");
const SocietyMember = require("../../../../model/society_member");
const SocietyActivityMember = require("../../../../model/society_activity_member");

apiRouter.post("/create", (req, res, next) => {

    let activityId = req.body.activityId;
    let societyId = req.body.societyId;
    let userId = req.body.userId;

    SocietyActivityMember.findOne({ where: { activityId, societyId, userId } }).then(d => {
        if (d) {
            res.json(STATUS.STATUS_200())
        } else {
            User.findOne({ where: { id: userId } }).then(userData => {
                let username = userData.username;
                let userIconUrl = userData.iconUrl;

                Society.findOne({ where: { id: societyId } }).then(d => {
                    if (d) {
                        SocietyMember.create({
                            activityId, societyId, userId, username, userIconUrl
                        }).then(d => {
                            res.json(STATUS.STATUS_200(d));
                        }).catch(e => {
                            console.log(e);
                            res.status(500).json(STATUS.STATUS_500);
                        });
                    }
                }).catch(e => {
                    console.log(e);
                    res.status(500).json(STATUS.STATUS_500);
                });

            }).catch(e => {
                console.log(e);
                res.status(500).json(STATUS.STATUS_500);
            });
        }
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/list", (req, res, next) => {
    let activityId = req.body.activityId;
    SocietyActivityMember.findAll({ where: { activityId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500)
        });
});

module.exports = { apiRouter };
