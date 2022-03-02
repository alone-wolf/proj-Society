const express = require("express");
const apiRouter = express.Router();
const User = require("../../model/user");
const UserLogin = require("../../model/user_login");
const SocietyMember = require("../../model/society_member");
const authMiddleware = require("../../middleware/auth");

const STATUS = require("../../utils/return_data");
const Post = require("../../model/post");
const PostReply = require("../../model/post_reply");
const SocietyJoinRequest = require("../../model/society_member_request");
const BBSUserWatch = require("../../model/bbs_user_watch");

// the feature of login is done
// user's basic info
apiRouter.post("/info", authMiddleware.checkCookieToken, (req, res, next) => {
  let id = req.body.userId;
  User.findOne({ where: { id } })
    .then(data => {
      if (data) {
        res.json(STATUS.STATUS_200(data));
      } else {
        res.status(404).json(STATUS.STATUS_404)
      }
    }).catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/info/simple", (req, res, next) => {
  let userId = req.body.userId;
  User.findOne({ where: { id: userId } })
    .then(data => {
      if (data) {
        res.json(STATUS.STATUS_200(data));
      } else {
        res.status(404).json(STATUS.STATUS_404)
      }
    }).catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/info/update", (req, res, next) => {
  let userId = req.body.userId;
  let username = req.body.username;
  let email = req.body.email;
  let studentNumber = req.body.studentNumber;
  let iconUrl = req.body.iconUrl;
  let phone = req.body.phone;
  let name = req.body.name;
  let college = req.body.college;
  let password = req.body.password;

  let m = { username, email, studentNumber, iconUrl, phone, name, college, password };

  User.findOne({ where: { id: userId } }).then(userData => {
    if (!userData) {
      res.status(404).json(STATUS.STATUS_404);
      return;
    }

    userData.update(m).then(newUserData => {

      Post.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      PostReply.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      SocietyActivityRequest.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      SocietyInnerChat.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      UserChatPrivate.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      SocietyJoinRequest.update({ username }, { where: { userId } }).catch(e => { console.log(e); })
      SocietyMember.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      SocietyNotice.update({ postUsername: username }, { where: { postUserId: userId } }).catch(e => { console.log(e); })

      res.json(STATUS.STATUS_200(newUserData));

    }).catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });

  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/info/update/password", (req, res, next) => {
  let name = req.body.name;
  let username = req.body.username;
  let studentNumber = req.body.studentNumber;
  let phone = req.body.phone;
  let email = req.body.email;

  let password = req.body.password;

  User.update(
    { password },
    { where: { name, username, studentNumber, phone, email } }
  ).then(d => {
    res.json(STATUS.STATUS_200(d));
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/register", (req, res, next) => {
  let phone = req.body.phone;
  let username = req.body.username;
  let email = req.body.email;
  let name = req.body.name;
  let password = req.body.password;
  User.create({ phone, username, email, name, password }).then(d => {
    res.json(STATUS.STATUS_200(d));
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/login", (req, res, next) => {
  let phoneStudentIdEmail = req.body.phoneStudentIdEmail;
  let password = req.body.password;

  User.findOne({ where: { password } }).then(data => {
    if (!data) {
      res.status(404).json(STATUS.STATUS_404);
      return;
    }
    if (!(data.phone == phoneStudentIdEmail || data.email == phoneStudentIdEmail)) {
      res.status(404).json(STATUS.STATUS_404);
      return;
    }
    let userId = data.id;
    UserLogin.destroy({ where: { userId } }).then(d => {
      UserLogin.create({ userId }).then(data => {
        res.json(STATUS.STATUS_200(data));
      }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
      });
    }).catch(e => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/logout", authMiddleware.checkCookieToken, (req, res, next) => {
  let userId = req.body.userId;
  console.log(userId);
  UserLogin.destroy({ where: { userId } }).then(data => {
    res.json(STATUS.STATUS_200(data))
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  });
});

// create user
// check adminToken
apiRouter.post("/create", authMiddleware.checkAdminToken, (req, res, next) => {
  let name = req.body.name;
  let username = req.body.username || name;
  let email = req.body.email || "";
  let phone = req.body.phone || "";
  let studentNumber = req.body.studentNumber;
  let password = req.body.password;
  User.create({ name, username, email, studentNumber, password, phone })
    .then((data) => {
      res.json(STATUS.STATUS_200(data));
    })
    .catch((e) => {
      res.status(500).json(STATUS.STATUS_500);
      console.log(e);
    });
});

// update userInfo
// and update some other model
// apiRouter.post("/update", (req, res, next) => {
//   let userId = req.body.userId;
//   let name = req.body.name;
//   let username = req.body.username;
//   let phone = req.body.phone;
//   let email = req.body.email;
//   let studentNumber = req.body.studentNumber;
//   let password = req.body.password;
//   let describe = req.body.describe;
//   let iconUrl = req.body.iconUrl;
//   console.log(req.body);
//   let m = {
//     name, username, phone, email,
//     studentNumber, password, describe, iconUrl
//   };


//   User.findOne({ where: { id: userId } }).then((data) => {
//     if (!data) {
//       res.status(404).json(STATUS.STATUS_404);
//       return;
//     }

//     if (username) {
//       SocietyJoinRequest.update(
//         { username },
//         { where: { userId } }
//       );

//       SocietyJoinRequest.update(
//         { username },
//         { where: { userId } }
//       );
//       if (iconUrl) {
//         SocietyMember.update(
//           { username, userIconUrl: iconUrl },
//           { where: { userId } }
//         );
//         PostReply.update(
//           { username, userIconUrl: iconUrl },
//           { where: { userId } }
//         );
//         Post.update(
//           { username, userIconUrl: iconUrl },
//           { where: { userId } }
//         );
//       }
//     }

//     data
//       .update(m)
//       .then((data) => {
//         res.json(STATUS.STATUS_200(data));
//       })
//       .catch((e) => {
//         console.log(e);
//         res.status(500).json(STATUS.STATUS_500);
//       });
//   });
// });

apiRouter.post("/delete", authMiddleware.checkAdminToken, (req, res, next) => {
  let userId = req.body.userId;
  User.destroy({ where: { id: userId } }).then(d => {
    res.json(STATUS.STATUS_200(d));
    UserLogin.destroy({ where: { userId } });
    SocietyMember.destroy({ where: { userId } });
    UserChatPrivate.destroy({ where: { $or: [{ userId }, { opUserId: userId }] } });
    PostReply.destroy({ where: { userId } });
    Post.destroy({ where: { userId } });
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

// // find join by userId
// apiRouter.get("/join", (req, res, next) => {
//   let userId = req.body.userId;
//   if (!userId) {
//     res.status(400).json(STATUS.STATUS_400);
//     return;
//   }
//   UserSocietyJoint.findAll({ where: { userId } })
//     .then((data) => {
//       res.json(data);
//     })
//     .catch((e) => {
//       console.log(e);
//       res.status(500).json(STATUS.STATUS_500);
//     });
// });

apiRouter.post("/list", (req, res, next) => {
  User.findAll().then((data) => {
    res.json(STATUS.STATUS_200(data))
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/join/request/list", (req, res, next) => {
  let userId = req.body.userId;
  SocietyJoinRequest.findAll({ where: { userId } }).then(data => {
    res.json(STATUS.STATUS_200(data))
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500(e))
  })
})

apiRouter.post("/joint", (req, res, next) => {
  var userId = req.body.userId;
  SocietyMember.findAll({ where: { userId } }).then((data) => {
    res.json(STATUS.STATUS_200(data));
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  })
})

apiRouter.post("/post/list", (req, res, next) => {
  let userId = req.body.userId;
  Post.findAll({ where: { userId } }).then(data => {
    res.json(STATUS.STATUS_200(data));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/post/reply/list", (req, res, next) => {
  let userId = req.body.userId;
  let postId = req.body.postId;
  let where = { userId };
  if (postId) {
    where.postId = postId;
  }
  PostReply.findAll({ where: where }).then(data => {
    res.json(STATUS.STATUS_200(data));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});


// remember to check server-token
apiRouter.post("/room/info", (req, res, next) => {
  let userId = req.body.userId;

  BBSUserWatch.findAll({ where: { userId } }).then(bbsUserWatch => {
    SocietyMember.findAll({ where: { userId } }).then(societyJoint => {
      res.json(STATUS.STATUS_200({
        bbsUserWatch, societyJoint
      }))
    }).catch(e => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

const userChatRouter = require("./chat/router");
const UserChatPrivate = require("../../model/user_chat_private");
const SocietyActivityRequest = require("../../model/society_activity_request");
const SocietyInnerChat = require("../../model/society_inner_chat");
const SocietyNotice = require("../../model/society_notice");
apiRouter.use("/chat", userChatRouter.apiRouter);

module.exports = { apiRouter };
