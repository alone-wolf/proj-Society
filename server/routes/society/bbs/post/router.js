const express = require("express");
const apiRouter = express.Router();
const Society = require("../../../../model/society");

const STATUS = require("../../../../utils/return_data");
const Post = require("../../../../model/post");
const PostReply = require("../../../../model/post_reply");
const User = require("../../../../model/user");
const { brdNewPost } = require("../../../../socketio/socketio");

apiRouter.post("/list", (req, res, next) => {
    let societyId = req.body.id;
    let m = {};
    if (societyId) {
        m.societyId = societyId
    }
    Post.findAll({ where: m }).then((data) => {
        res.json(STATUS.STATUS_200(data));
    }).catch((e) => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500)
    });
});

apiRouter.post("/by/id", (req, res, next) => {
    let postId = req.body.postId;
    Post.findOne({ where: { id: postId } }).then(d => {
        if (d) {
            res.json(STATUS.STATUS_200(d));
        } else {
            res.status(404).json(STATUS.STATUS_404);
        }
    }).catch((e) => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500)
    });
})
apiRouter.post("/create", (req, res, next) => {
    let userId = req.body.userId;
    let societyId = req.body.societyId;
    let societyName = "";
    let bbsName = "";
    let title = req.body.title;
    let post = req.body.post;
    let level = req.body.level || 0;
    let deviceName = req.body.deviceName || "Android";

    Society.findOne({ where: { id: societyId } }).then(data => {
        if (data) {
            societyName = data.name;
            bbsName = data.bbsName;
        }
        User.findOne({ where: { id: userId } }).then(data => {
            let username = data.username;
            Post.create({
                userId, societyId, title, post, level, username, userIconUrl: data.iconUrl, deviceName, societyName
            }).then(data => {
                res.json(STATUS.STATUS_200(data));
                brdNewPost(societyId, bbsName, title, userId);
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



apiRouter.post("/delete", (req, res, next) => {
    let postId = req.body.postId;
    // let userId = req.body.userId;
    PostReply.destroy({ where: { postId } })
        .catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
    Post.destroy({ where: { id: postId } })
        .catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
    res.json(STATUS.STATUS_200());
});

const bbsPostReplyRouter = require("./reply/router");
apiRouter.use("/reply", bbsPostReplyRouter.apiRouter);

module.exports = { apiRouter };
