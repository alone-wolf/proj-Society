const express = require("express");
const apiRouter = express.Router();


const societyInnerChat = require("./inner/route");
apiRouter.use("/inner", societyInnerChat.apiRouter);

module.exports = { apiRouter };
