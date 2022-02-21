const express = require("express");
const apiRouter = express.Router();
const User = require("../../../../model/user");

const STATUS = require("../../../../utils/return_data");
const { srdChatPrivate } = require("../../../../socketio/socketio");
const ChatPrivate = require("../../../../model/user_chat_private");

// /user/chat/private/create
apiRouter.post("/create", (req, res, next) => {
    let userId = req.body.userId;
    let opUserId = req.body.opUserId;
    let message = req.body.message;

    User.findOne({ where: { id: userId } }).then(d => {
        if (!d) {
            res.status(404).json(STATUS.STATUS_404);
            return;
        }
        let username = d.username;
        let userIconUrl = d.iconUrl;

        User.findOne({ where: { id: opUserId } }).then(d => {
            if (!d) {
                res.status(404).json(STATUS.STATUS_404);
                return;
            }

            ChatPrivate.create({
                userId, username, userIconUrl,
                opUserId, message
            }).then(d => {
                res.json(STATUS.STATUS_200(d));
                srdChatPrivate(opUserId, username, userId, message);
            }).catch(e => {
                console.log(e);
                res.status(500).json(STATUS.STATUS_500);
            });

        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });

    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/list", (req, res, next) => {
    let userId = req.body.userId;
    let opUserId = req.body.opUserId;
    ChatPrivate.findAll(
        {
            $or: [
                { userId, opUserId },
                { userId: opUserId, opUserId: userId }
            ]
        }
    ).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

module.exports = { apiRouter };
