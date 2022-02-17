const Sequelize = require("sequelize");
// const { logger } = require("../socketio/socketio");
// const SOCKETIO = require("../socketio/socketio");

// const socketIOLogger = function (message) {
//   SOCKETIO.logger("server-main-db", message);
// };

const sequelize = new Sequelize({
  host: "localhost",
  dialect: "sqlite",
  logging: false,
  // logging: (msg) => socketIOLogger(msg),
  pool: {
    max: 5,
    min: 0,
    acquire: 30000,
    idle: 10000,
  },
  storage: "./database.sqlite",
  //   operatorsAliases: false,
});

module.exports = sequelize;
