const express = require("express");
const Picture = require("../../model/picture");
const Post = require("../../model/post");
const PostReply = require("../../model/post_reply");
const apiRouter = express.Router();
const logicRouter = express.Router();
const Society = require("../../model/society");
const SocietyMember = require("../../model/society_member");
const User = require("../../model/user");

const STATUS = require("../../utils/return_data");


apiRouter.post("/user/list", (req, res, next) => {
    User.findAll().then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/user/update", (req, res, next) => {
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

apiRouter.post("/user/create", (req, res, next) => {
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

apiRouter.post("/user/byid", (req, res, next) => {
    let userId = req.body.userId;
    User.findOne({ where: { id: userId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/user/delete", (req, res, next) => {
    let userId = req.body.userId;
    User.destroy({ where: { id: userId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/list", (req, res, next) => {
    Society.findAll().then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/create", (req, res, next) => {
    let name = req.body.name;
    let describe = req.body.describe;
    let bbsName = req.body.bbsName;
    let bbsDescribe = req.body.bbsDescribe;
    Society.create({ name, describe, bbsName, bbsDescribe }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/update", (req, res, next) => {
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

apiRouter.post("/society/delete", (req, res, next) => {
    let societyId = req.body.societyId;
    Society.destroy({ where: { id: societyId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/member/create", (req, res, next) => {
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

apiRouter.post("/society/member/delete", (req, res, next) => {
    let userId = req.body.userId;
    let societyId = req.body.societyId;
    SocietyMember.destroy({ where: { userId, societyId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/society/member/update/permission", (req, res, next) => {
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

apiRouter.post("/post/delete", (req, res, next) => {
    let postId = req.body.postId;

    PostReply.destroy({ where: { postId } }).catch(e => { console.log(e) });
    Post.destroy({ where: { id: postId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/post/reply/delete", (req, res, next) => {
    let replyId = req.body.replyId;
    PostReply.destroy({ where: { id: replyId } }).then(d => {
        res.json(STATUS.STATUS_200());
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
})

apiRouter.post("/pic/list", (req, res, next) => {
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

apiRouter.post("/user/register/allow/switch", (req, res, next) => {
    userRegisterAllow = !userRegisterAllow;
    res.json(STATUS.STATUS_200({ userRegisterAllow }));
});


module.exports = { apiRouter };
