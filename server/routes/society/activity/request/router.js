const express = require("express");
const apiRouter = express.Router();
const Society = require("../../../../model/society");

const STATUS = require("../../../../utils/return_data");
const User = require("../../../../model/user");
const { brdNewActivityRequest } = require("../../../../socketio/socketio");
const SocietyActivityRequest = require("../../../../model/society_activity_request");
const SocietyActivity = require("../../../../model/society_activity");

apiRouter.post("/create", (req, res, next) => {

    let societyId = req.body.societyId;
    let userId = req.body.userId;
    let request = req.body.request;
    let isJoin = req.body.isJoin || true;

    SocietyActivityRequest.findOne({ where: { societyId, userId } })
        .then((data) => {
            if (data) {
                data.destroy();
            }
            User.findOne({ where: { id: userId } }).then(data => {
                if (!data) {
                    res.status(404).json(STATUS.STATUS_404);
                    return;
                }
                let username = data.username;
                let userIconUrl = data.iconUrl;
                Society.findOne({ where: { id: societyId } }).then(data => {
                    if (!data) {
                        res.status(404).json(STATUS.STATUS_404);
                        return;
                    }
                    let societyName = data.name;
                    SocietyActivityRequest.create({
                        societyId, societyName, userId,
                        username, userIconUrl, isJoin, request
                    }).then((data) => {
                        res.json(STATUS.STATUS_200(data));
                        brdNewActivityRequest(societyId, societyName, userId, username)
                    }).catch((e) => {
                        console.log(e);
                        res.status(500).json(STATUS.STATUS_500)
                    });
                });
            });
        }).catch((e) => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500)
        });
});

apiRouter.post("/list", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyActivityRequest.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500)
        });
});

// apiRouter.post("/deal", (req, res, next) => {
//     let requestId = req.body.requestId;
//     let isAgreed = req.body.isAgreed || true;
//     let permissionLevel = req.body.permissionLevel || 11;
//     SocietyActivityRequest.findOne({ where: { id: requestId } }).then(sjr => {
//         if (!sjr) {
//             res.status(404).json(STATUS.STATUS_404);
//             return;
//         }
//         let userId = sjr.userId;
//         let username = sjr.username;
//         let societyId = sjr.societyId;
//         let societyName = sjr.societyName;
//         let userIconUrl = sjr.userIconUrl;

//         UserSocietyJoint.create(
//             { userId, username, societyId, societyName, userIconUrl, permissionLevel }
//         ).then(d => {
//             sjr.update({ isAgreed, isDealDone: true });
//             res.json(STATUS.STATUS_200(d));
//             srdMemberRequestReply(userId, societyId, societyName);
//             brdMemberChanged(societyId, societyName);
//         });

//     });
// });

module.exports = { apiRouter };
