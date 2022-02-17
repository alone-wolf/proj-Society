// 用户间私密聊天
const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class BBSUserWatch extends Model { }

BBSUserWatch.init(
    {
        id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
        societyId: DataTypes.INTEGER,
        userId: DataTypes.INTEGER
    },
    {
        sequelize,
        timestamps: true,
        createdAt: "createTimestamp",
        updatedAt: "updateTimestamp",
    }
);

module.exports = BBSUserWatch;