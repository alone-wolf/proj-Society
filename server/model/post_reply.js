const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class PostReply extends Model { }

PostReply.init(
  {
    id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
    societyId: DataTypes.INTEGER,
    societyName: DataTypes.STRING,
    postId: DataTypes.INTEGER,
    postTitle: DataTypes.STRING,
    userId: DataTypes.INTEGER,
    username: DataTypes.STRING,
    userIconUrl: DataTypes.STRING,
    reply: DataTypes.STRING,
    deviceName: DataTypes.STRING
  },
  {
    sequelize,
    timestamps: true,
    createdAt: "createTimestamp",
    updatedAt: "updateTimestamp",
  }
);

module.exports = PostReply;
