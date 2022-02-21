const express = require("express");
const apiRouter = express.Router();
const Society = require("../../../model/society");

const STATUS = require("../../../utils/return_data");
const Post = require("../../../model/post");
const PostReply = require("../../../model/post_reply");


// reply post-count reply-count
apiRouter.post("/info", (req, res, next) => {
    let societyId = req.body.id;
    Post.findAndCountAll({ where: { societyId } }).then((data) => {
        let postCount = data.count;
        let posts = data.rows;
        PostReply.findAndCountAll({ where: { societyId } }).then((data) => {
            let replyCount = data.count;
            Society.findOne({ where: { id: societyId } }).then((data) => {
                let bbsAllowGuestPost = data.bbsAllowGuestPost;
                res.json(STATUS.STATUS_200({
                    "postCount": postCount,
                    "posts": posts,
                    "postReplyCount": replyCount,
                    bbsAllowGuestPost
                }));
            }).catch(e => {
                console.log(e);
                res.status(500).json(STATUS.STATUS_500)
            });
        }).catch((e) => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500)
        });
    }).catch((e) => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500)
    });
});

const postRouter = require("./post/router");
apiRouter.use("/post", postRouter.apiRouter);
const bbsWatchRouter = require("./watch/route");
apiRouter.use("/watch", bbsWatchRouter.apiRouter);


module.exports = { apiRouter };
