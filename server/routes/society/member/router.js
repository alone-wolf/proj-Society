const express = require("express");
const apiRouter = express.Router();

const UserSocietyJoint = require("../../../model/society_member");

const STATUS = require("../../../utils/return_data");
const User = require("../../../model/user");

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
