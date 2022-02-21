const express = require("express");
const apiRouter = express.Router();
const Society = require("../../../model/society");
const UserSocietyJoint = require("../../../model/society_member");

const STATUS = require("../../../utils/return_data");
const { brdNewNoticeWatcher, brdNewNoticeMember } = require("../../../socketio/socketio");
const SocietyNotice = require("../../../model/society_notice");


apiRouter.post("/list", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyNotice.findAll({ where: { societyId } }).then(data => {
        res.json(data);
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/create", (req, res, next) => {
    let postUserId = req.body.postUserId;
    let societyId = req.body.societyId;
    let title = req.body.title;
    let notice = req.body.notice;
    let permissionLevel = req.body.permissionLevel || 10;

    UserSocietyJoint.findOne({ where: { userId: postUserId } }).then(data => {
        if (!data) {
            res.status(404).json(STATUS.STATUS_404);
            return;
        }
        if (data.permissionLevel != 111) {
            res.status(403).json(STATUS.STATUS_403);
            return;
        }
        let postUsername = data.username;
        let postUserIconUrl = data.userIconUrl;

        Society.findOne({ where: { id: societyId } }).then(d => {
            if (!d) {
                res.status(404).json(STATUS.STATUS_404);
                return;
            }
            let societyName = d.name;

            SocietyNotice.create({ postUserId, postUsername, title, notice, permissionLevel }).then(d => {
                res.json(STATUS.STATUS_200(d));
                switch (permissionLevel) {
                    case 0: {
                        brdNewNoticeWatcher(societyId, societyName, postUserId, postUsername, title)
                    }
                    case 10: {
                        brdNewNoticeMember(societyId, societyName, postUserId, postUsername, title)
                    }
                }

            }).catch(e => {
                console.log(e);
                res.status(500).json(STATUS.STATUS_500);
            });

        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
    });
});

apiRouter.post("/delete", (req, res, next) => {
    let userId = req.body.userId;
    // check permission
    let noticeId = req.body.noticeId;
    SocietyNotice.destroy({ where: { id: noticeId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

module.exports = { apiRouter };
