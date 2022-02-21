const express = require("express");
const apiRouter = express.Router();
const logicRouter = express.Router();
const Society = require("../../model/society");
const UserSocietyJoint = require("../../model/society_member");
const authMiddleware = require("../../middleware/auth");

const STATUS = require("../../utils/return_data");
const SocietyJoinRequest = require("../../model/society_join_request");
const Post = require("../../model/post");
const PostReply = require("../../model/post_reply");
const User = require("../../model/user");
const SocietyInnerChat = require("../../model/society_inner_chat");
const SocietyActivity = require("../../model/society_activity");
const BBSUserWatch = require("../../model/bbs_user_watch");

// get all society
apiRouter.post("/list", authMiddleware.checkAdminToken, (req, res, next) => {
  Society.findAll()
    .then((data) => {
      res.json(STATUS.STATUS_200(JSON.parse(JSON.stringify(data))));
    })
    .catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
});

// create new society
apiRouter.post("/create", (req, res, next) => {
  let name = req.body.name;
  Society.create({ name, describe: name, bbsName: name, bbsDescribe: name, openTimestamp: Date.now() })
    .then((data) => {
      res.json(STATUS.STATUS_200(JSON.parse(JSON.stringify(data))));
    })
    .catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
});

// used to check society name
// apiRouter.post("/check/name", (req, res, next) => {
//   let name = req.body.name;
//   Society.findOne({ where: { name } })
//     .then((data) => {
//       if (data) {
//         res.json(STATUS.STATUS_200({ exit: true }));
//       } else {
//         res.json(STATUS.STATUS_200({ exit: false }));
//       }
//     })
//     .catch((e) => {
//       console.log(e);
//       res.status(500).json(STATUS.STATUS_500);
//     });
// });

// used to check bbs name
// apiRouter.post("/check/bbsname", (req, res, next) => {
//   let bbsName = req.body.bbsName;
//   Society.findOne({ where: { bbsName } })
//     .then((data) => {
//       if (data) {
//         res.json(STATUS.STATUS_200({ exit: true }));
//       } else {
//         res.json(STATUS.STATUS_200({ exit: false }));
//       }
//     })
//     .catch((e) => {
//       console.log(e);
//       res.status(500).json(STATUS.STATUS_500);
//     });
// });

// apiRouter.post("/update", (req, res, next) => {
//   let societyId = req.body.societyId;
//   if (!societyId) {
//     res.status(400).json(STATUS.STATUS_400);
//     return;
//   }
//   let name = req.body.name;
//   let openTimeStamp = req.body.openTimeStamp;
//   let photos = req.body.photos;
//   let describe = req.body.describe;
//   let bbsName = req.body.bbsName;
//   let bbsDescribe = req.body.bbsDescribe;
//   let m = { name, openTimeStamp, photos, describe, bbsName, bbsDescribe };
//   Society.findOne({ where: { id: societyId } })
//     .then((data) => {
//       if (data) {
//         data
//           .update(m)
//           .then((data) => {
//             res.json(STATUS.STATUS_200());
//           })
//           .catch((e) => {
//             console.log(e);
//             res.status(500).json(STATUS.STATUS_500);
//           });
//       } else {
//         res.status(404).json(STATUS.STATUS_404);
//       }
//     })
//     .catch((e) => {
//       console.log(e);
//       res.status(500).json(STATUS.STATUS_500);
//     });
// });

// delete one society only
// post with admin token
// admin function, must check permission
apiRouter.post("/delete", (req, res, next) => {
  let id = req.body.societyId;
  Society.destroy({ where: { id } });
  UserSocietyJoint.destroy({ where: { societyId: id } });
  SocietyJoinRequest.destroy({ where: { societyId: id } });
  Post.destroy({ where: { societyId: id } });
  PostReply.destroy({ where: { societyId: id } });
  SocietyActivity.destroy({ where: { societyId: id } });
  SocietyInnerChat.destroy({ where: { societyId: id } });
  res.json(STATUS.STATUS_200());
});

// add user and society exit check
// permission level >= 100 required
apiRouter.post("/join", (req, res, next) => {
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
apiRouter.post("/leave", (req, res, next) => {
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
apiRouter.post("/joint", (req, res, next) => {
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
apiRouter.use("/bbs", societyBBSRouter.apiRouter);
const societyChatRouter = require("./chat/router");
apiRouter.use("/chat", societyChatRouter.apiRouter);
const societyMemberRouter = require("./member/router");
apiRouter.use("/member", societyMemberRouter.apiRouter);
const societyNoticeRouter = require("./notice/router");
apiRouter.use("/notice", societyNoticeRouter.apiRouter);

module.exports = { apiRouter };
