const express = require("express");
const apiRouter = express.Router();
const STATUS = require("../../utils/return_data");
const Society = require("../../model/society");
const UserSocietyJoint = require("../../model/society_member");
const User = require("../../model/user");
const BBSUserWatch = require("../../model/bbs_user_watch");

const { checkCookieToken } = require("../../middleware/auth");


// get all society
apiRouter.post("/list", checkCookieToken, (req, res, next) => {

  let thisUserId = req.body.cookieTokenUserId;

  let ll = [];

  Society.findAll()
    .then((sl) => {
      let sll = sl.map((item) => { return item.get({ plain: true }) });

      SocietyMember.findAll({ where: { userId: thisUserId } })
        .then(sml => {
          let smll = sml.map((item) => { return item.get({ plain: true }) });

          sll.forEach(e => {
            if (
              smll.find((it) => { return it.societyId == e.id })
            ) {
              e.thisUserJoin = true;
            } else {
              e.thisUserJoin = false;
            }

            ll.push(e);
          });

          res.json(STATUS.STATUS_200(sll));

        }).catch((e) => {
          console.log(e);
          res.status(500).json(STATUS.STATUS_500);
        });

    }).catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/update", checkCookieToken, (req, res, next) => {
  let societyId = req.body.id;

  let name = req.body.name;
  let openTimestamp = req.body.openTimestamp;
  let describe = req.body.describe;
  let college = req.body.college;
  let bbsName = req.body.bbsName;
  let bbsDescribe = req.body.bbsDescribe;
  let iconUrl = req.body.iconUrl;
  let m = { name, openTimestamp, describe, college, bbsName, bbsDescribe, iconUrl };

  Society.update(m, { where: { id: societyId } }).then(d => {
    res.json(STATUS.STATUS_200());

    PostReply.update({ societyName: name }, { where: { societyId } }).catch(e => { console.log(e) });
    Post.update({ societyName: name }, { where: { societyId } }).catch(e => { console.log(e) });
    SocietyActivity.update({ societyName: name }, { where: { societyId } }).catch(e => { console.log(e) });
    SocietyMemberRequest.update({ societyName: name }, { where: { societyId } }).catch(e => { console.log(e) });
    SocietyNotice.update({ societyName: name }, { where: { societyId } }).catch(e => { console.log(e) });

  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });

});

apiRouter.post("/info", checkCookieToken, (req, res, next) => {
  let societyId = req.body.societyId;

  Society.findOne({ where: { id: societyId } }).then(d => {
    if (d) {
      res.json(STATUS.STATUS_200(d));
    } else {
      res.status(404).json(STATUS.STATUS_404);
    }
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/join", checkCookieToken, (req, res, next) => {
  let societyId = req.body.societyId;
  let userId = req.body.userId;
  let permissionLevel = req.body.permissionLevel || 11;

  BBSUserWatch.destroy({ where: { userId, societyId } });

  User.findOne({ where: { id: userId } }).then(userData => {
    if (userData) {
      let username = userData.username;
      let userIconUrl = userData.iconUrl;
      let m = { societyId, userId, permissionLevel, username, userIconUrl };

      UserSocietyJoint.findOne({ where: { societyId, userId } }).then((data) => {
        if (data) {
          res.json(STATUS.STATUS_200)
        } else {
          UserSocietyJoint.create(m)
            .then((data) => {
              res.json(STATUS.STATUS_200(data));
            })
            .catch((e) => {
              console.log(e);
              res.status(500).json(STATUS.STATUS_500);
            });
        }
      }).catch((e) => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
      });
    } else {
      res.status(404).json(STATUS.STATUS_404({}))
    }
  })

});

// permission level >= 100 required
apiRouter.post("/leave", checkCookieToken, (req, res, next) => {
  let userId = req.body.userId;
  let societyId = req.body.societyId;
  UserSocietyJoint.findOne({ where: { userId, societyId } }).then((data) => {
    if (data) {
      data.destroy().then((data) => {
        res.json(STATUS.STATUS_200(data));
      }).catch((e) => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
      });
    } else {
      res.json(STATUS.STATUS_200());
    }
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

// find join by societyId
apiRouter.post("/joint", checkCookieToken, (req, res, next) => {
  let societyId = req.body.id;
  if (!societyId) {
    res.status(400).json(STATUS.STATUS_400);
    return;
  }
  UserSocietyJoint.findAll({ where: { societyId } })
    .then((data) => {
      res.json(STATUS.STATUS_200(JSON.parse(JSON.stringify(data))));
    })
    .catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
});



const societyBBSRouter = require("./bbs/router");
apiRouter.use("/bbs", checkCookieToken, societyBBSRouter.apiRouter);
const societyChatRouter = require("./chat/router");
apiRouter.use("/chat", checkCookieToken, societyChatRouter.apiRouter);
const societyMemberRouter = require("./member/router");
apiRouter.use("/member", checkCookieToken, societyMemberRouter.apiRouter);
const societyNoticeRouter = require("./notice/router");
apiRouter.use("/notice", checkCookieToken, societyNoticeRouter.apiRouter);
const societyActivity = require("./activity/router");
apiRouter.use("/activity", checkCookieToken, societyActivity.apiRouter);
const societyPicture = require("./picture/router");
const SocietyMember = require("../../model/society_member");
const Post = require("../../model/post");
const PostReply = require("../../model/post_reply");
const SocietyActivity = require("../../model/society_activity");
const SocietyInnerChat = require("../../model/society_inner_chat");
const SocietyMemberRequest = require("../../model/society_member_request");
const SocietyNotice = require("../../model/society_notice");
apiRouter.use("/picture", societyPicture.apiRouter);

module.exports = { apiRouter };
