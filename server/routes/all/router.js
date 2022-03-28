const express = require("express");
const Picture = require("../../model/picture");
const Post = require("../../model/post");
const PostReply = require("../../model/post_reply");
const apiRouter = express.Router();
const Society = require("../../model/society");
const SocietyActivity = require("../../model/society_activity");
const SocietyActivityMember = require("../../model/society_activity_member");
const SocietyActivityRequest = require("../../model/society_activity_request");
const SocietyInnerChat = require("../../model/society_inner_chat");
const SocietyMember = require("../../model/society_member");
const SocietyMemberRequest = require("../../model/society_member_request");
const SocietyNotice = require("../../model/society_notice");
const SocietyPicture = require("../../model/society_picture");
const User = require("../../model/user");
const UserChatPrivate = require("../../model/user_chat_private");

const STATUS = require("../../utils/return_data");

// 列出用户
apiRouter.post("/user", (req, res, next) => {
    User.findAll()
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出用户图片
apiRouter.post("/user/pic", (req, res, next) => {
    let userId = req.body.userId;
    Picture.findAll({ where: { userId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出用户发布的帖子
apiRouter.post("/user/post", (req, res, next) => {
    let userId = req.body.userId;
    Post.findAll({ where: { userId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出用户的回复
apiRouter.post("/user/reply", (req, res, next) => {
    let userId = req.body.userId;
    PostReply.findAll({ where: { userId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出用户所属的社团--成员  这里以后需要改成返回社团本体
apiRouter.post("/user/society/member", (req, res, next) => {
    let userId = req.body.userId;
    SocietyMember.findAll({ where: { userId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出用户的社团成员申请
apiRouter.post("/user/society/member/request", (req, res, next) => {
    let userId = req.body.userId;
    SocietyMemberRequest.findAll({ where: { userId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出用户所属的社团活动--成员
apiRouter.post("/user/society/activity/member", (req, res, next) => {
    let userId = req.body.userId;
    SocietyActivityMember.findAll({ where: { userId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出用户所属的社团活动--成员申请
apiRouter.post("/user/society/activity/member/request", (req, res, next) => {
    let userId = req.body.userId;
    SocietyActivityRequest.findAll({ where: { userId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});

// 列出社团
apiRouter.post("/society", (req, res, next) => {
    Society.findAll()
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出社团的帖子
apiRouter.post("/society/post", (req, res, next) => {
    let societyId = req.body.societyId;
    Post.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出社团的回复
apiRouter.post("/society/reply", (req, res, next) => {
    let societyId = req.body.societyId;
    PostReply.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出社团图片
apiRouter.post("/society/pic", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyPicture.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出社团成员
apiRouter.post("/society/member", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyMember.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出社团成员申请
apiRouter.post("/society/member/request", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyMemberRequest.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出社团活动
apiRouter.post("/society/activity", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyActivity.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出社团活动成员
apiRouter.post("/society/activity/member", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyActivityMember.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出社团活动成员申请
apiRouter.post("/society/activity/member/request", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyActivityRequest.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});
// 列出社团通知
apiRouter.post("/society/notice", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyNotice.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});

// 列出Post的回复
apiRouter.post("/post/reply", (req, res, next) => {
    let postId = req.body.postId;
    PostReply.findAll({ where: { postId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});


module.exports = { apiRouter };