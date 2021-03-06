const express = require("express");
const apiRouter = express.Router();

const UserSocietyJoint = require("../../../model/society_member");

const STATUS = require("../../../utils/return_data");
const User = require("../../../model/user");


// 获取发出请求用户的指定社团的成员信息
apiRouter.post("/by/society/id", (req, res, next) => {
  let societyId = req.body.societyId;
  let userId = req.body.cookieTokenUserId;
  UserSocietyJoint.findOne({ where: { societyId, userId } }).then(d => {
    if (d) {
      res.json(STATUS.STATUS_200(d));
    } else {
      res.status(404).json(STATUS.STATUS_404);
    }
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/list/by/society", (req, res, next) => {
  let societyId = req.body.societyId;
  UserSocietyJoint.findAll({ where: { societyId } })
    .then(d => {
      res.json(STATUS.STATUS_200(d));
    }).catch(e => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/update/permissionLevel", (req, res, next) => {
  let memberId = req.body.memberId;
  let permissionLevel = req.body.permissionLevel;
  UserSocietyJoint.update({ permissionLevel }, { where: { id: memberId } })
    .then(_ => {
      res.json(STATUS.STATUS_200());
    }).catch(e => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
})

apiRouter.post("/info", (req, res, next) => {
  let societyId = req.body.societyId;
  UserSocietyJoint.findAll({ where: { societyId } }).then(data => {
    console.log(data);
    let userInfoList = [];
    data.dataValues.forEach(element => {
      User.findOne({ where: { id: element.userId } }).then(data => {
        if (data) {
          userInfoList.push({
            id: data.id,
            iconUrl: data.iconUrl
          });
        }
      }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
      });
    });
    res.json(STATUS.STATUS_200(userInfoList));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

const societyMemberRequest = require("./request/router");
apiRouter.use("/request", societyMemberRequest.apiRouter);

module.exports = { apiRouter };
