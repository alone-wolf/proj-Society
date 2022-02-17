// 用户间私密聊天
const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class SocietyInnerChat extends Model { }

SocietyInnerChat.init(
    {
        id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
        societyId: DataTypes.INTEGER,
        userId: DataTypes.INTEGER,
        userIconUrl: {
            defaultValue: "",
            type: DataTypes.STRING
        },
        username: DataTypes.STRING,
        message: DataTypes.STRING,
        messageType: {
            defaultValue: 0, // 0 text, 1 imageUrl
            type: DataTypes.INTEGER
        }
    },
    {
        sequelize,
        timestamps: true,
        createdAt: "createTimestamp",
        updatedAt: "updateTimestamp",
    }
);

module.exports = SocietyInnerChat;