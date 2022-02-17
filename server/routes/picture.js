const express = require("express");
const apiRouter = express.Router();
const logicRouter = express.Router();
const Picture = require("../model/picture");
const authMiddleware = require("../middleware/auth");

const STATUS = require("../utils/return_data");
const fs = require("fs");
const path = require("path");
// /api/picture

const formidable = require("formidable");

// upload web ui for test
apiRouter.get("/ui", (req, res) => {
  res.send(`
    <h2>With <code>"express"</code> npm package</h2>
    <form action="/pic/create" enctype="multipart/form-data" method="post">
      <div>Text field title: <input type="text" name="userId" value="1" /></div>
      <div>File: <input type="file" name="file" multiple="off" /></div>
      <input type="submit" value="Upload" />
    </form>
  `);
});

// get all pictures, not usable for oridnary user
apiRouter.get("/list", (req, res, next) => {
  Picture.findAll()
    .then((data) => {
      res.json(STATUS.STATUS_200(data));
    })
    .catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.get("/:userId", (req, res, next) => {
  let userId = req.params.userId;
  if (!userId) {
    res.status(400).json(STATUS.STATUS_400);
    return;
  }
  Picture.findAll({ where: { userId } })
    .then((data) => {
      res.json(STATUS.STATUS_200(JSON.parse(JSON.stringify(data))));
    })
    .catch((e) => {
      res.status(500).json(STATUS.STATUS_500);
      console.log(e);
    }); 0
});

// for universal usage
apiRouter.get("/:userId/:token", (req, res, next) => {
  let userId = req.params.userId;
  let newFilename = req.params.token;
  if (!(userId && newFilename)) {
    res.status(404).json(STATUS.STATUS_404);
    return;
  }
  Picture.findOne({ where: { userId, newFilename } })
    .then((data) => {
      if (data) {
        res.download(
          path.join(__dirname, "../uploads", data.dataValues.newFilename),
          data.dataValues.originalFilename,
          (err) => {
            if (err) {
              console.log(err);
            }
          }
        );
      } else {
        res.status(404).json(STATUS.STATUS_404);
      }
    })
    .catch((e) => {
      console.log(e);
      res.status(500).json(STATUS.STATUS_500);
    });
});

apiRouter.post("/create", (req, res, next) => {
  // console.log(req);
  const form = formidable({
    uploadDir: path.join("__dirname", "/../uploads"),
    filter: function ({ name, originalFilename, mimetype }) {
      return mimetype && mimetype.includes("image");
    },
  });

  form.parse(req, (err, fields, files) => {
    let userId = fields.userId;
    if (err) {
      console.log(err);
      res.status(500).json(STATUS.STATUS_500);
      // next(err);
      return;
    } else {
      Picture.create({
        userId: userId,
        originalFilename: files.file.originalFilename,
        newFilename: files.file.newFilename,
      })
        .then((data) => {
          console.log(data.dataValues);
          res.json(STATUS.STATUS_200(data.dataValues));
        })
        .catch((e) => {
          res.status(500).json(STATUS.STATUS_500);
        });
    }
  });
});

apiRouter.post("/list", (req, res, next) => {
  let userId = req.body.userId;

  Picture.findAll({ where: { userId } }).then(data => {
    res.json(STATUS.STATUS_200(data));
  }).catch(e => {
    res.status(500).json(STATUS.STATUS_500);
  });
});

apiRouter.post("/delete", (req, res, next) => {
  let newFilename = req.body.picToken;
  let userId = req.body.userId;
  Picture.destroy({ where: { newFilename, userId } }).then(data => {
    res.json(STATUS.STATUS_200(data));
  }).catch(e => {
    console.log(e);
    res.status(500).json(STATUS.STATUS_500)
  });
})


// method deleted
// apiRouter.delete("/:token", (req, res, next) => {
//   let newFilename = req.params.token;
//   Picture.findOne({ where: { newFilename } })
//     .then((data) => {
//       if (data) {
//         data
//           .destroy()
//           .then(() => {
//             res.json(STATUS.STATUS_200());
//           })
//           .catch((e) => {
//             res.status(500).json(STATUS.STATUS_500);
//           });
//       } else {
//         res.json(STATUS.STATUS_200());
//       }
//     })
//     .catch((e) => {
//       res.status(500).json(STATUS.STATUS_500);
//     });
// });

module.exports = { apiRouter };
