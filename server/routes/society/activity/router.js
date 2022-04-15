const express = require("express");
const apiRouter = express.Router();

const STATUS = require("../../../utils/return_data");
const SocietyActivity = require("../../../model/society_activity");
const Society = require("../../../model/society");
const SocietyActivityMember = require("../../../model/society_activity_member");

// 列出某社团下的全部活动
apiRouter.post("/list", (req, res, next) => {
    let societyId = req.body.societyId;
    let thisUserId = req.body.cookieTokenUserId;

    let ll = [];

    SocietyActivity.findAll({ where: { societyId } })
        .then(sal => {

            let sall = sal.map((v, i, a) => {
                return v.get({ plain: true });
            });

            SocietyActivityMember.findAll(
                { where: { societyId, userId: thisUserId } }
            ).then(saml => {

                let samll = saml.map((v, i, a) => {
                    return v.get({ plain: true });
                });

                sall.forEach(e => {
                    if (samll.find((item) => {
                        return ee.userId == item.userId;
                    })) {
                        e.thisUserJoin = true
                    } else {
                        e.thisUserJoin = false
                    }
                    ll.push(e);
                });

                res.json(STATUS.STATUS_200(ll));
            }).catch(e => {
                console.log(e);
                res.status(500).json(STATUS.STATUS_500);
            });

        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});

// 在某社团下创建活动
apiRouter.post("/create", (req, res, next) => {
    let societyId = req.body.societyId;
    let deviceName = req.body.deviceName;
    let title = req.body.title;
    let level = req.body.level || 0;
    let activity = req.body.activity;
    Society.findOne({ where: { id: societyId } }).then(d => {
        let societyName = d.name;
        SocietyActivity.create({
            societyId, societyName,
            deviceName, level,
            title, activity
        }).then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
    });
});
// 删除指定活动
apiRouter.post("/delete", (req, res, next) => {
    let activityId = req.body.activityId;
    Society.destroy({ where: { id: activityId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});
// 加入指定活动
apiRouter.post("/join", (req, res, next) => {
    let userId = req.body.userId;
    let activityId = req.body.activityId;

    SocietyActivity.findOne({ id: activityId }).then(d => {
        if (d) {
            SocietyActivityMember.findOrCreate({
                where: {
                    userId, activityId
                }
            }).then(d => {
                res.json(STATUS.STATUS_200(d))
            }).catch(e => {
                console.log(e);
                res.status(500).json(STATUS.STATUS_500);
            });
        } else {
            res.status(404).json(STATUS.STATUS_404);
        }
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});
// 离开指定活动
apiRouter.post("/leave", (req, res, next) => {
    let activityMemberId = req.body.activityMemberId;
    SocietyActivityMember.destroy({ where: { id: activityMemberId } }).then(d => {
        res.json(STATUS.STATUS_200(d))
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
})
// 列出某活动的全部成员
apiRouter.post("/member", (req, res, next) => {
    let activityId = req.body.activityId;

    SocietyActivityMember.findAll({ where: { activityId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});

module.exports = { apiRouter };
