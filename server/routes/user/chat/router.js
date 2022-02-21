const express = require("express");
const apiRouter = express.Router();

const userChatPrivateRouter = require("./private/router");
apiRouter.use("/private", userChatPrivateRouter.apiRouter)

module.exports = { apiRouter };
