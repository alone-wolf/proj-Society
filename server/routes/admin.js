// const express = require("express");
// const apiRouter = express.Router();
// const logicRouter = express.Router();
// const Society = require("../model/society");
// const UserSocietyJoint = require("../model/user_society_joint");
// const authMiddleware = require("../middleware/auth");

// const STATUS = require("../utils/return_data");

// // get all society
// apiRouter.get("/", authMiddleware.checkAdminToken, (req, res, next) => {
//   Society.findAll()
//     .then((data) => {
//       res.json(STATUS.STATUS_200(JSON.parse(JSON.stringify(data))));
//     })
//     .catch((e) => {
//       console.log(e);
//       res.status(500).json(STATUS.STATUS_500);
//     });
// });

// // create new society
// apiRouter.post("/", (req, res, next) => {
//   let name = req.body.name;
//   Society.create({ name })
//     .then((data) => {
//       res.json(STATUS.STATUS_200());
//     })
//     .catch((e) => {
//       console.log(e);
//       res.status(500).json(STATUS.STATUS_500);
//     });
// });

// // used to check society name
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

// // used to check bbs name
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

// // delete one society only
// apiRouter.post("/delete", (req, res, next) => {
//   let societyId = req.body.societyId;
//   Society.findOne({ where: { id: societyId } })
//     .then((data) => {
//       if (data) {
//         data
//           .destroy()
//           .then(() => {
//             UserSocietyJoint.findAll({ where: { societyId } }).then((data) => {
//               if (data) {
//                 data
//                   .destroy()
//                   .then(() => {
//                     res.json(STATUS.STATUS_200());
//                   })
//                   .catch((e) => {
//                     console.log(e);
//                     res.status(500).json(STATUS.STATUS_500);
//                   });
//               } else {
//                 res.json(STATUS.STATUS_200());
//               }
//             });
//           })
//           .catch((e) => {
//             res.status(500).json(STATUS.STATUS_500);
//           });
//       } else {
//         res.json(STATUS.STATUS_200());
//       }
//     })
//     .catch((e) => {
//       console.log(e);
//       res.status(500).json(STATUS.STATUS_500);
//     });
// });

// // add user and society exit check
// apiRouter.post("/join", (req, res, next) => {
//   let societyId = req.body.societyId;
//   let userId = req.body.userId;
//   let permissionLevel = req.body.permissionLevel;
//   let m = { societyId, userId, permissionLevel };
//   UserSocietyJoint.create(m)
//     .then(() => {
//       res.json(STATUS.STATUS_200());
//     })
//     .catch((e) => {
//       console.log(e);
//       res.status(500).json(STATUS.STATUS_500);
//     });
// });

// // find join by societyId
// apiRouter.get("/join", (req, res, next) => {
//   let societyId = req.body.societyId;
//   if (!societyId) {
//     res.status(400).json(STATUS.STATUS_400);
//     return;
//   }
//   UserSocietyJoint.findAll({ where: { societyId } })
//     .then((data) => {
//       res.json(data);
//     })
//     .catch((e) => {
//       console.log(e);
//       res.status(500).json(STATUS.STATUS_500);
//     });
// });

// module.exports = { apiRouter };
