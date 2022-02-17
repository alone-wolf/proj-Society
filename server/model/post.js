const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class Post extends Model { }

Post.init(
  {
    id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
    societyId: DataTypes.INTEGER,
    societyName:DataTypes.STRING,
    userId: DataTypes.INTEGER,
    username: DataTypes.STRING,
    userIconUrl: DataTypes.STRING,
    deviceName: DataTypes.STRING,
    level: {
      type: DataTypes.INTEGER,
      defaultValue: 0,
    },
    title: DataTypes.STRING,
    post: DataTypes.STRING,
  },
  {
    sequelize,
    timestamps: true,
    createdAt: "createTimestamp",
    updatedAt: "updateTimestamp",
  }
);

module.exports = Post;
