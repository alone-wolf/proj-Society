// 用户间私密聊天
const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class UserChatPrivate extends Model { }

UserChatPrivate.init(
    {
        id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
        // user0
        userId: DataTypes.INTEGER,
        username: DataTypes.STRING,
        userIconUrl: {
            defaultValue: "",
            type: DataTypes.STRING
        },
        // user1
        opUserId: DataTypes.INTEGER,

        message: DataTypes.STRING
    },
    {
        sequelize,
        timestamps: true,
        createdAt: "createTimeStamp",
        updatedAt: "updateTimeStamp",
    }
);

module.exports = UserChatPrivate;
