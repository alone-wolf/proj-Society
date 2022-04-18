const express = require("express");
const apiRouter = express.Router();
const STATUS = require("../../../../utils/return_data");
const Society = require("../../../../model/society");
const SocietyMember = require("../../../../model/society_member");
const SocietyJoinRequest = require("../../../../model/society_member_request");
const User = require("../../../../model/user");
const { brdNewMemberRequest, srdMemberRequestReply, brdMemberChanged } = require("../../../../socketio/socketio");

apiRouter.post("/create", (req, res, next) => {
  let societyId = req.body.societyId;
  let userId = req.body.userId;
  let request = req.body.request;
  let isJoin = req.body.isJoin || true;

  SocietyJoinRequest.destroy({ where: { societyId, userId } }).then(d => {
    User.findOne({ where: { id: userId } }).then(data => {
      if (data) {
        let username = data.username;
        Society.findOne({ where: { id: societyId } }).then(data => {
          if (data) {
            let societyName = data.name;
            SocietyJoinRequest.create(
              { societyId, userId, request, username, societyName, isJoin }
            ).then((data) => {
              res.json(STATUS.STATUS_200(data));
              brdNewMemberRequest(societyId, societyName, userId, username)
            }).catch((e) => {
              console.log(e);
              res.status(500).json(STATUS.STATUS_500)
            });
          } else {
            res.status(404).json(STATUS.STATUS_404);
          }
        });
      } else {
        res.status(404).json(STATUS.STATUS_404);
      }
    }).catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500)
    });
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  });
});

apiRouter.post("/list", (req, res, next) => {
  let societyId = req.body.societyId;
  SocietyJoinRequest.findAll({ where: { societyId } }).then(d => {
    res.json(STATUS.STATUS_200(d));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  });
});

apiRouter.post("/deal", (req, res, next) => {
  let requestId = req.body.requestId;
  let isAgreed = req.body.isAgreed || true;
  SocietyJoinRequest.findOne({ where: { id: requestId } }).then(sjr => {
    if (!sjr) {
      res.status(404).json(STATUS.STATUS_404);
      return;
    }
    sjr.update({ isAgreed, isDealDone: true });

    let userId = sjr.userId;
    let societyId = sjr.societyId;

    if (sjr.isJoin && sjr.isAgreed) {
      let username = sjr.username;
      let societyName = sjr.societyName;
      // let userIconUrl = sjr.userIconUrl;

      User.findOne({ where: { id: userId } }).then(d => {
        let userIconUrl = d.iconUrl;

        SocietyMember.create(
          { userId, username, societyId, societyName, userIconUrl, permissionLevel: 11 }
        ).then(d => {
          res.json(STATUS.STATUS_200(d));
          srdMemberRequestReply(userId, societyId, societyName);
          brdMemberChanged(societyId, societyName);
        }).catch(e => {
          console.log(e);
          res.status(500).json(STATUS.STATUS_500)
        });
      });

    } else if (!sjr.isJoin && sjr.isAgreed) {
      SocietyMember.destroy({ where: { userId, societyId } })
        .then(d => {
          res.json(STATUS.STATUS_200(d));
        }).catch(e => {
          console.log(e);
          res.status(500).json(STATUS.STATUS_500)
        });
    } else {
      res.json(STATUS.STATUS_200());
    }
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  });
});

module.exports = { apiRouter };
