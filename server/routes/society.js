const express = require("express");
const apiRouter = express.Router();
const logicRouter = express.Router();
const Society = require("../model/society");
const UserSocietyJoint = require("../model/user_society_joint");
const authMiddleware = require("../middleware/auth");

const STATUS = require("../utils/return_data");
const SocietyJoinRequest = require("../model/society_join_request");
const Post = require("../model/post");
const PostReply = require("../model/post_reply");
const User = require("../model/user");
const SocietyInnerChat = require("../model/society_inner_chat");
const SocietyActivity = require("../model/society_activity");
const BBSUserWatch = require("../model/bbs_user_watch");
const { brdChatInner, brdNewPost, srdNewPostReply, brdNewMemberRequest, srdMemberRequestReply, brdMemberChanged } = require("../socketio/socketio");

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

apiRouter.post("/member/info", (req, res, next) => {
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

// reply post-count reply-count
apiRouter.post("/bbs/info", (req, res, next) => {
  let societyId = req.body.id;
  Post.findAndCountAll({ where: { societyId } }).then((data) => {
    let postCount = data.count;
    let posts = data.rows;
    PostReply.findAndCountAll({ where: { societyId } }).then((data) => {
      let replyCount = data.count;
      Society.findOne({ where: { id: societyId } }).then((data) => {
        let bbsAllowGuestPost = data.bbsAllowGuestPost;
        res.json(STATUS.STATUS_200({
          "postCount": postCount,
          "posts": posts,
          "postReplyCount": replyCount,
          bbsAllowGuestPost
        }));
      }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500)
      });
    }).catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500)
    });
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  });
});

// certain bbs watch list
apiRouter.post("/bbs/watch/list", (req, res, next) => {
  let societyId = req.body.societyId;

  BBSUserWatch.findAll({ where: { societyId } }).then(d => {
    res.json(STATUS.STATUS_200(d));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

// watch certain bbs, with userId
apiRouter.post("/bbs/watch/create", (req, res, next) => {
  let userId = req.body.userId;
  let societyId = req.body.societyId;

  BBSUserWatch.findOrCreate({ where: { userId, societyId } }).then(d => {
    res.json(STATUS.STATUS_200(d));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/bbs/watch/delete", (req, res, next) => {
  let userId = req.body.userId;
  let societyId = req.body.societyId;

  BBSUserWatch.destroy({ where: { userId, societyId } }).then(d => {
    res.json(STATUS.STATUS_200(d));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
})

apiRouter.post("/bbs/post/list", (req, res, next) => {
  let societyId = req.body.id;
  let m = {};
  if (societyId) {
    m.societyId = societyId
  }
  Post.findAll({ where: m }).then((data) => {
    res.json(STATUS.STATUS_200(data));
  }).catch((e) => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  });
});
apiRouter.post("/bbs/post/create", (req, res, next) => {
  let userId = req.body.userId;
  let societyId = req.body.societyId;
  let societyName = "";
  let bbsName = "";
  let title = req.body.title;
  let post = req.body.post;
  let level = req.body.level || 0;
  let deviceName = req.body.deviceName || "Android";

  Society.findOne({ where: { id: societyId } }).then(data => {
    if (data) {
      societyName = data.name;
      bbsName = data.bbsName;
    }
    User.findOne({ where: { id: userId } }).then(data => {
      let username = data.username;
      Post.create({
        userId, societyId, title, post, level, username, userIconUrl: data.iconUrl, deviceName, societyName
      }).then(data => {
        res.json(STATUS.STATUS_200(data));
        brdNewPost(societyId, bbsName, title, userId);
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



apiRouter.post("/bbs/post/delete", (req, res, next) => {
  let postId = req.body.postId;
  // let userId = req.body.userId;
  PostReply.destroy({ where: { postId } })
    .catch(e => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
  Post.destroy({ where: { id: postId } })
    .catch(e => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
  res.json(STATUS.STATUS_200());
});

apiRouter.post("/bbs/post/reply/list", (req, res, next) => {
  let postId = req.body.postId;
  PostReply.findAll({ where: { postId } }).then(data => {
    res.json(STATUS.STATUS_200(data));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/bbs/post/reply/create", (req, res, next) => {
  let societyId = req.body.societyId;
  let postId = req.body.postId;
  let userId = req.body.userId; // 指发送者的userId
  let reply = req.body.reply;
  let deviceName = req.body.deviceName;
  User.findOne({ where: { id: userId } }).then(data => {
    if (data) {
      let username = data.username;
      let userIconUrl = data.iconUrl;
      Society.findOne({ where: { id: societyId } }).then(data => {
        if (data) {
          let societyName = data.name;
          let bbsName = data.bbsName;
          Post.findOne({ where: { id: postId } }).then(data => {
            if (data) {
              let postTitle = data.title;
              let postUserId = data.userId;
              PostReply.create({
                societyId, societyName, postId, postTitle, userId, username, reply, deviceName, userIconUrl, username
              }).then(data => {
                res.json(STATUS.STATUS_200(data));

                srdNewPostReply(postUserId, userId, username, societyId, bbsName, postTitle, reply)

              }).catch(e => {
                console.log(e);
                res.status(500).json(STATUS.STATUS_500);
              });
            } else {
              res.status(404).json(STATUS.STATUS_404())
            }
          }).catch(e => {
            console.log(e);
            res.status(500).json(STATUS.STATUS_500);
          });
        } else {
          res.status(404).json(STATUS.STATUS_404())
        }
      }).catch(e => {
        console.log(e);
        res.status(500).json(STATUS.STATUS_500);
      });
    } else {
      res.status(404).json(STATUS.STATUS_404())
    }
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/bbs/post/reply/delete", (req, res, next) => {
  let postReplyId = req.body.postReplyId;
  PostReply.destroy({ where: { id: postReplyId } }).then(d => {
    res.json(STATUS.STATUS_200(d));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/member/request/create", (req, res, next) => {
  let societyId = req.body.societyId;
  let userId = req.body.userId;
  let request = req.body.request;
  let isJoin = req.body.isJoin || true;
  SocietyJoinRequest.findOne({ where: { societyId, userId } }).then((data) => {
    if (data) {
      data.destroy();
    }
    User.findOne({ where: { id: userId } }).then(data => {
      if (!data) {
        res.status(404).json(STATUS.STATUS_404);
        return;
      }
      let username = data.username;
      Society.findOne({ where: { id: societyId } }).then(data => {
        if (!data) {
          res.status(404).json(STATUS.STATUS_404);
          return;
        }
        let societyName = data.name;
        SocietyJoinRequest.create({ societyId, userId, request, username, societyName, isJoin }).then((data) => {
          res.json(STATUS.STATUS_200(data));

          brdNewMemberRequest(societyId, societyName, userId, username)
        }).catch((e) => {
          console.log(e);
          res.status(500).json(STATUS.STATUS_500)
        });
      });
    });
  });
});

apiRouter.post("/member/request/list", (req, res, next) => {
  let societyId = req.body.societyId;
  SocietyJoinRequest.findAll({ where: { societyId } }).then(d => {
    res.json(STATUS.STATUS_200(d));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  });
});

apiRouter.post("/member/request/deal", (req, res, next) => {
  let requestId = req.body.requestId;
  let isAgreed = req.body.isAgreed || true;
  let permissionLevel = req.body.permissionLevel || 11;
  SocietyJoinRequest.findOne({ where: { id: requestId } }).then(sjr => {
    if (!sjr) {
      res.status(404).json(STATUS.STATUS_404);
      return;
    }
    let userId = sjr.userId;
    let username = sjr.username;
    let societyId = sjr.societyId;
    let societyName = sjr.societyName;
    let userIconUrl = sjr.userIconUrl;

    UserSocietyJoint.create(
      { userId, username, societyId, societyName, userIconUrl, permissionLevel }
    ).then(d => {
      sjr.update({ isAgreed, isDealDone: true });
      res.json(STATUS.STATUS_200(d));
      srdMemberRequestReply(userId, societyId, societyName);
      brdMemberChanged(societyId, societyName);
    });

  });
});

// apiRouter.post("/join/request/deal",(req,res,next)=>{
//   let societyId = req.body.societyId;
//   let userId = req.body.userId;
//   let requestId = req.body.requestId;
//   let allow = req.body.allow;
//   SocietyJoinRequest.findOne({where:{id:requestId,userId,societyId}}).then(data=>{
//     if(data){
//       data.update
//     }else{
//       res.status(404).json(STATUS.STATUS_404)
//     }
//   })
// });

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

// /society/chat/inner
apiRouter.post("/chat/inner/create", (req, res, next) => {
  let userId = req.body.userId;
  let societyId = req.body.societyId;
  let message = req.body.message;

  User.findOne({ where: { id: userId } }).then(data => {
    if (!data) {
      res.status(404).json(STATUS.STATUS_404);
      return;
    }
    let username = data.username;
    let userIconUrl = data.iconUrl;
    SocietyInnerChat.create({
      userId, societyId, message,
      username, userIconUrl
    }).then(data => {
      res.json(STATUS.STATUS_200(data));

      Society.findOne({ where: { id: societyId } }).then(d => {
        if (!d) {
          return;
        }
        brdChatInner(societyId, d.name, message, userId, username);
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

apiRouter.post("/chat/inner/list", (req, res, next) => {
  let societyId = req.body.societyId;
  SocietyInnerChat.findAll({ where: { societyId } }).then(data => {
    res.json(STATUS.STATUS_200(data));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500);
  });
});

module.exports = { apiRouter };
