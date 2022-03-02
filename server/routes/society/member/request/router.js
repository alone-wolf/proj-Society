const express = require("express");
const apiRouter = express.Router();
const Society = require("../../../../model/society");
const UserSocietyJoint = require("../../../../model/society_member");

const STATUS = require("../../../../utils/return_data");
const SocietyJoinRequest = require("../../../../model/society_member_request");
const User = require("../../../../model/user");
const { brdNewMemberRequest, srdMemberRequestReply, brdMemberChanged } = require("../../../../socketio/socketio");

apiRouter.post("/create", (req, res, next) => {
    console.log(req.body);
    let societyId = req.body.societyId;
    let userId = req.body.userId;
    let request = req.body.request;
    let isJoin = req.body.isJoin || true;
    SocietyJoinRequest.findOne({ where: { societyId, userId } }).then((data) => {
        if (data) {
            data.destroy();
        }
        User.findOne({ where: { id: userId } }).then(data => {
            if (!data) {
                res.status(404).json(STATUS.STATUS_404);
                return;
            }
            let username = data.username;
            Society.findOne({ where: { id: societyId } }).then(data => {
                if (!data) {
                    res.status(404).json(STATUS.STATUS_404);
                    return;
                }
                let societyName = data.name;
                SocietyJoinRequest.create({ societyId, userId, request, username, societyName, isJoin }).then((data) => {
                    res.json(STATUS.STATUS_200(data));

                    brdNewMemberRequest(societyId, societyName, userId, username)
                }).catch((e) => {
                    console.log(e);
                    res.status(500).json(STATUS.STATUS_500)
                });
            });
        });
    });
});

apiRouter.post("/list", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyJoinRequest.findAll({ where: { societyId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500)
    });
});

apiRouter.post("/deal", (req, res, next) => {
    let requestId = req.body.requestId;
    let isAgreed = req.body.isAgreed || true;
    let permissionLevel = req.body.permissionLevel || 11;
    SocietyJoinRequest.findOne({ where: { id: requestId } }).then(sjr => {
        if (!sjr) {
            res.status(404).json(STATUS.STATUS_404);
            return;
        }
        let userId = sjr.userId;
        let username = sjr.username;
        let societyId = sjr.societyId;
        let societyName = sjr.societyName;
        let userIconUrl = sjr.userIconUrl;

        UserSocietyJoint.create(
            { userId, username, societyId, societyName, userIconUrl, permissionLevel }
        ).then(d => {
            sjr.update({ isAgreed, isDealDone: true });
            res.json(STATUS.STATUS_200(d));
            srdMemberRequestReply(userId, societyId, societyName);
            brdMemberChanged(societyId, societyName);
        });

    });
});

module.exports = { apiRouter };
