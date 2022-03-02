const express = require("express");
const apiRouter = express.Router();

const STATUS = require("../../../utils/return_data");
const SocietyActivity = require("../../../model/society_activity");
const Society = require("../../../model/society");

apiRouter.post("/list", (req, res, next) => {
    let societyId = req.body.societyId;
    SocietyActivity.findAll({ where: { societyId } })
        .then(d => {
            res.json(STATUS.STATUS_200(d));
        }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
        });
});

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

apiRouter.post("/delete", (req, res, next) => {
    let activityId = req.body.activityId;
    Society.destroy({ where: { id: activityId } }).then(d => {
        res.json(STATUS.STATUS_200(d));
    }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
    });
});


// apiRouter.post("/info", (req, res, next) => {
//     let societyId = req.body.societyId;
//     UserSocietyJoint.findAll({ where: { societyId } }).then(data => {
//         console.log(data);
//         let userInfoList = [];
//         data.dataValues.forEach(element => {
//             User.findOne({ where: { id: element.userId } }).then(data => {
//                 if (data) {
//                     userInfoList.push({
//                         id: data.id,
//                         iconUrl: data.iconUrl
//                     });
//                 }
//             }).catch(e => {
//                 console.log(e);
//                 res.status(500).json(STATUS.STATUS_500);
//             });
//         });
//         res.json(STATUS.STATUS_200(userInfoList));
//     }).catch(e => {
//         console.log(e);
//         res.status(500).json(STATUS.STATUS_500);
//     });
// });

const societyActivityRequest = require("./request/router");
apiRouter.use("/request", societyActivityRequest.apiRouter);
const societyActivityMember = require("./member/router");
apiRouter.use("/member", societyActivityMember.apiRouter);


module.exports = { apiRouter };
