const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class SocietyActivityMember extends Model { }

SocietyActivityMember.init(
    {
        id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },

        activityId: DataTypes.INTEGER,

        societyId: DataTypes.INTEGER,
        societyName: DataTypes.STRING,

        userId: DataTypes.INTEGER,
        username: DataTypes.STRING,
        userIconUrl: DataTypes.STRING
    },
    {
        sequelize,
        timestamps: true,
        createdAt: "createTimestamp",
        updatedAt: "updateTimestamp",
    }
);

module.exports = SocietyActivityMember;
