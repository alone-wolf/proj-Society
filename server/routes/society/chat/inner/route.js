const express = require("express");
const apiRouter = express.Router();
const Society = require("../../../../model/society");

const STATUS = require("../../../../utils/return_data");
const User = require("../../../../model/user");
const SocietyInnerChat = require("../../../../model/society_inner_chat");
const { brdChatInner } = require("../../../../socketio/socketio");

// /society/chat/inner
apiRouter.post("/create", (req, res, next) => {
    let userId = req.body.userId;
    let societyId = req.body.societyId;
    let message = req.body.message;

    User.findOne({ where: { id: userId } }).then(data => {
        if (!data) {
            res.status(404).json(STATUS.STATUS_404);
            return;
        }
        let username = data.username;
        let userIconUrl = data.iconUrl;
        SocietyInnerChat.create({
            userId, societyId, message,
            username, userIconUrl
        }).then(data => {
            res.json(STATUS.STATUS_200(data));

            Society.findOne({ where: { id: societyId } }).then(d => {
                if (!d) {
                    return;
                }
                brdChatInner(societyId, d.name, message, userId, username);
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
    let societyId = req.body.societyId;
    SocietyInnerChat.findAll({ where: { societyId } }).then(data => {
        res.json(STATUS.STATUS_200(data));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/delete", (req, res, next) => {
    let chatId = req.body.chatId;

    SocietyInnerChat.destroy({ where: { id: chatId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/clear", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyInnerChat.destroy({ where: { societyId } }).then(d => {
        res.json(STATUS.STATUS_200(d))
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
})

module.exports = { apiRouter };
