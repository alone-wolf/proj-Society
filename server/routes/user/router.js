const express = require("express");
const apiRouter = express.Router();
const STATUS = require("../../utils/return_data");
const User = require("../../model/user");
const UserLogin = require("../../model/user_login");
const SocietyMember = require("../../model/society_member");
const Post = require("../../model/post");
const PostReply = require("../../model/post_reply");
const SocietyActivityMember = require("../../model/society_activity_member");
const SocietyMemberRequest = require("../../model/society_member_request");
const BBSUserWatch = require("../../model/bbs_user_watch");
const UserChatPrivate = require("../../model/user_chat_private");
const SocietyInnerChat = require("../../model/society_inner_chat");
const SocietyNotice = require("../../model/society_notice");

const Op = require("sequelize").Op;

const { checkCookieToken } = require("../../middleware/auth");

// apiRouter.post("/list", checkCookieToken, (req, res, next) => {
//   User.findAll().then(d => {
//     res.json(STATUS.STATUS_200(d));
//   }).catch((e) => {
//     console.log(e);
//     res.status(500).json(STATUS.STATUS_500);
//   });
// });

// the feature of login is done
// user's basic info
apiRouter.post("/info", checkCookieToken, (req, res, next) => {
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

apiRouter.post("/info/simple", checkCookieToken, (req, res, next) => {
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

apiRouter.post("/info/update", checkCookieToken, (req, res, next) => {
  let userId = req.body.id;
  let username = req.body.username;
  let email = req.body.email;
  let studentNumber = req.body.studentNumber;
  let iconUrl = req.body.iconUrl;
  let phone = req.body.phone;
  let name = req.body.name;
  let college = req.body.college;
  let password = req.body.password;

  User.findOne({ where: { id: userId } }).then(userData => {
    if (!userData) {
      res.status(404).json(STATUS.STATUS_404);
      return;
    }

    userData.update(
      { username, email, studentNumber, iconUrl, phone, name, college, password }
    ).then(newUserData => {

      res.json(STATUS.STATUS_200(newUserData));

      Post.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      PostReply.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      SocietyInnerChat.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      UserChatPrivate.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      SocietyMemberRequest.update({ username }, { where: { userId } }).catch(e => { console.log(e); })
      SocietyMember.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })
      SocietyNotice.update({ postUsername: username }, { where: { postUserId: userId } }).catch(e => { console.log(e); })
      SocietyActivityMember.update({ username, userIconUrl: iconUrl }, { where: { userId } }).catch(e => { console.log(e); })

    }).catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });

  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/info/update/password", checkCookieToken, (req, res, next) => {
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

  User.findOne({
    where: {
      password: password,
      [Op.or]: [
        { phone: phoneStudentIdEmail },
        { studentNumber: phoneStudentIdEmail },
        { email: phoneStudentIdEmail }
      ]
    }
  }).then(data => {
    if (!data) {
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

apiRouter.post("/logout", checkCookieToken, (req, res, next) => {
  let userId = req.body.userId;
  console.log(userId);
  UserLogin.destroy({ where: { userId } }).then(data => {
    res.json(STATUS.STATUS_200(data))
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  });
});

apiRouter.post("/join/request/list", checkCookieToken, (req, res, next) => {
  let userId = req.body.userId;
  SocietyMemberRequest.findAll({ where: { userId } }).then(data => {
    res.json(STATUS.STATUS_200(data))
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500(e))
  })
})

apiRouter.post("/joint", checkCookieToken, (req, res, next) => {
  var userId = req.body.userId;
  SocietyMember.findAll({ where: { userId } }).then((data) => {
    res.json(STATUS.STATUS_200(data));
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  })
})

apiRouter.post("/post/list", checkCookieToken, (req, res, next) => {
  let userId = req.body.userId;
  Post.findAll({ where: { userId } }).then(data => {
    res.json(STATUS.STATUS_200(data));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/post/reply/list", checkCookieToken, (req, res, next) => {
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


apiRouter.use("/chat", checkCookieToken, userChatRouter.apiRouter);

module.exports = { apiRouter };
