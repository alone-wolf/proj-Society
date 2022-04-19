const express = require("express");
const { checkAdminToken } = require("../../middleware/auth");
const Picture = require("../../model/picture");
const Post = require("../../model/post");
const PostReply = require("../../model/post_reply");
const apiRouter = express.Router();
const logicRouter = express.Router();
const Society = require("../../model/society");
const SocietyActivity = require("../../model/society_activity");
const SocietyActivityMember = require("../../model/society_activity_member");
const SocietyMember = require("../../model/society_member");
const User = require("../../model/user");

const STATUS = require("../../utils/return_data");


apiRouter.post("/user/list", checkAdminToken, (req, res, next) => {
    User.findAll().then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/user/update", checkAdminToken, (req, res, next) => {
    let userId = req.body.id;
    User.update({
        name: req.body.name,
        username: req.body.username,
        college: req.body.college,
        phone: req.body.phone,
        email: req.body.email,
        password: req.body.password,
        describe: req.body.describe,
        iconUrl: req.body.iconUrl,
    }, { where: { id: userId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
})

apiRouter.post("/user/create", checkAdminToken, (req, res, next) => {
    let name = req.body.name;
    let username = req.body.username;
    let college = req.body.college;
    let phone = req.body.phone;
    let email = req.body.studentNumber;
    let password = req.body.password;
    let describe = req.body.describe;
    let iconUrl = req.body.iconUrl;
    User.create({ name, username, college, phone, email, password, describe, iconUrl }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/user/byid", checkAdminToken, (req, res, next) => {
    let userId = req.body.userId;
    User.findOne({ where: { id: userId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/user/delete", checkAdminToken, (req, res, next) => {
    let userId = req.body.userId;
    User.destroy({ where: { id: userId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/list", checkAdminToken, (req, res, next) => {
    Society.findAll().then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/create", checkAdminToken, (req, res, next) => {
    let name = req.body.name;
    let describe = req.body.describe;
    let bbsName = req.body.bbsName;
    let bbsDescribe = req.body.bbsDescribe;
    let openTimestamp = req.body.openTimestamp;
    Society.create({ name, describe, bbsName, bbsDescribe, openTimestamp }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/update", checkAdminToken, (req, res, next) => {
    let societyId = req.body.id;
    let name = req.body.name;
    let describe = req.body.describe;
    let bbsName = req.body.bbsName;
    let bbsDescribe = req.body.bbsDescribe;
    let iconUrl = req.body.iconUrl;
    Society.update({ name, describe, bbsName, bbsDescribe, iconUrl }, { where: { id: societyId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/delete", checkAdminToken, (req, res, next) => {
    let societyId = req.body.societyId;
    Society.destroy({ where: { id: societyId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/activity/update/level", checkAdminToken, (req, res, next) => {
    let activityId = req.body.activityId;
    let level = req.body.level;

    SocietyActivity.update({ level }, { where: { id: activityId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
})

apiRouter.post("/society/activity/delete", checkAdminToken, (req, res, next) => {
    let activityId = req.body.activityId;

    res.json(STATUS.STATUS_200());

    SocietyActivity.destroy({ where: { id: activityId } }).catch(e => { console.log(e) });
    SocietyActivityMember.destroy({ where: { activityId: activityId } }).catch(e => { console.log(e) });
});

apiRouter.post("/society/activity/member/delete", checkAdminToken, (req, res, next) => {
    let memberId = req.body.memberId;

    SocietyActivityMember.destroy(
        { where: { id: memberId } }
    ).then(_ => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/member/create", checkAdminToken, (req, res, next) => {
    let userId = req.body.userId;
    let societyId = req.body.societyId;
    let permissionLevel = req.body.permissionLevel || 11;
    Society.findOne({ where: { id: societyId } }).then(sd => {
        if (sd) {
            User.findOne({ where: { id: userId } }).then(ud => {
                let d = { userId, societyId, permissionLevel, username: ud.username, userIconUrl: ud.iconUrl }
                SocietyMember.create(d).then(dd => {
                    res.json(STATUS.STATUS_200());
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
})

apiRouter.post("/society/member/delete", checkAdminToken, (req, res, next) => {
    let userId = req.body.userId;
    let societyId = req.body.societyId;
    SocietyMember.destroy({ where: { userId, societyId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/member/update/permission", checkAdminToken, (req, res, next) => {
    let userId = req.body.userId;
    let societyId = req.body.societyId;
    let permissionLevel = req.body.permissionLevel;
    SocietyMember.update(
        { permissionLevel },
        { where: { userId, societyId } }
    ).then(d => {
        res.json(STATUS.STATUS_200())
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/post/delete", checkAdminToken, (req, res, next) => {
    let postId = req.body.postId;

    PostReply.destroy({ where: { postId } }).catch(e => { console.log(e) });
    Post.destroy({ where: { id: postId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/post/reply/delete", checkAdminToken, (req, res, next) => {
    let replyId = req.body.replyId;
    PostReply.destroy({ where: { id: replyId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
})

apiRouter.post("/pic/list", checkAdminToken, (req, res, next) => {
    Picture.findAll().then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

let userRegisterAllow = require("../../config.json").userRegisterAllow
apiRouter.post("/user/register/allow", (req, res, next) => {
    res.json(STATUS.STATUS_200({ userRegisterAllow }));
});

apiRouter.post("/user/register/allow/switch", checkAdminToken, (req, res, next) => {
    userRegisterAllow = !userRegisterAllow;
    res.json(STATUS.STATUS_200({ userRegisterAllow }));
});


module.exports = { apiRouter };
