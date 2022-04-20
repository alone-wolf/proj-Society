const express = require("express");
const apiRouter = express.Router();

const STATUS = require("../../../utils/return_data");
const SocietyActivity = require("../../../model/society_activity");
const Society = require("../../../model/society");
const SocietyActivityMember = require("../../../model/society_activity_member");
const User = require("../../../model/user");

// 列出某社团下的全部活动
apiRouter.post("/list", (req, res, next) => {
  let societyId = req.body.societyId;
  let thisUserId = req.body.userId || req.body.cookieTokenUserId;

  let ll = [];

  SocietyActivity.findAll({ where: { societyId } })
    .then(sal => {

      SocietyActivityMember.findAll(
        { where: { societyId, userId: thisUserId } }
      ).then(saml => {
        let sall = sal.map((v) => { return v.get({ plain: true }) });
        let samll = saml.map((v) => { return v.get({ plain: true }) });

        sall.forEach(e => {
          if (
            samll.find((item) => { return e.id == item.activityId })
          ) {
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
  Society.findOne({ where: { id: societyId } })
    .then(d => {
      if (d) {
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
      } else {
        res.status(404).json(STATUS.STATUS_404);
      }

    }).catch(e => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
});
// 删除指定活动
apiRouter.post("/delete", (req, res, next) => {
  let activityId = req.body.activityId;
  SocietyActivity.destroy({ where: { id: activityId } }).then(d => {
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

  SocietyActivity.findOne({ where: { id: activityId } }).then(d => {
    if (d) {
      let societyId = d.societyId;
      let societyName = d.societyName;
      let activityTitle = d.title;
      User.findOne({ where: { id: userId } }).then(d => {
        let username = d.username;
        let userIconUrl = d.iconUrl;

        SocietyActivityMember.create({
          userId, username, userIconUrl, societyId, societyName, activityId, activityTitle
        }).then(d => {
          res.json(STATUS.STATUS_200(d))
        }).catch(e => {
          console.log(e);
          res.status(500).json(STATUS.STATUS_500);
        });
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
  let activityId = req.body.activityId;
  let userId = req.body.userId;
  SocietyActivityMember.destroy({ where: { activityId, userId } }).then(d => {
    res.json(STATUS.STATUS_200(d));
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
