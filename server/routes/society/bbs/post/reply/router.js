const express = require("express");
const apiRouter = express.Router();
const Society = require("../../../../../model/society");


const STATUS = require("../../../../../utils/return_data");
const Post = require("../../../../../model/post");
const PostReply = require("../../../../../model/post_reply");
const User = require("../../../../../model/user");

const { srdNewPostReply } = require("../../../../../socketio/socketio");


apiRouter.post("/list", (req, res, next) => {
    let postId = req.body.postId;
    PostReply.findAll({ where: { postId } }).then(data => {
        res.json(STATUS.STATUS_200(data));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/create", (req, res, next) => {
    let societyId = req.body.societyId;
    let postId = req.body.postId;
    let userId = req.body.userId; // 指发送者的userId
    let reply = req.body.reply;
    let deviceName = req.body.deviceName;
    User.findOne({ where: { id: userId } }).then(data => {
        if (data) {
            let username = data.username;
            let userIconUrl = data.iconUrl;
            Society.findOne({ where: { id: societyId } }).then(data => {
                if (data) {
                    let societyName = data.name;
                    let bbsName = data.bbsName;
                    Post.findOne({ where: { id: postId } }).then(data => {
                        if (data) {
                            let postTitle = data.title;
                            let postUserId = data.userId;
                            PostReply.create({
                                societyId, societyName, postId, postTitle, userId, username, reply, deviceName, userIconUrl, username
                            }).then(data => {
                                res.json(STATUS.STATUS_200(data));

                                srdNewPostReply(postUserId, userId, username, societyId, bbsName, postTitle, reply)

                            }).catch(e => {
                                console.log(e);
                                res.status(500).json(STATUS.STATUS_500);
                            });
                        } else {
                            res.status(404).json(STATUS.STATUS_404())
                        }
                    }).catch(e => {
                        console.log(e);
                        res.status(500).json(STATUS.STATUS_500);
                    });
                } else {
                    res.status(404).json(STATUS.STATUS_404())
                }
            }).catch(e => {
                console.log(e);
                res.status(500).json(STATUS.STATUS_500);
            });
        } else {
            res.status(404).json(STATUS.STATUS_404())
        }
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/delete", (req, res, next) => {
    let postReplyId = req.body.postReplyId;
    PostReply.destroy({ where: { id: postReplyId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

module.exports = { apiRouter };
