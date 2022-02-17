const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class SocietyActivity extends Model { }

SocietyActivity.init(
    {
        id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
        societyId: DataTypes.INTEGER,
        societyName: DataTypes.STRING,
        deviceName: DataTypes.STRING,
        level: {
            type: DataTypes.INTEGER,
            defaultValue: 0, // 0:public 10:inner 100:adminor
        },
        title: DataTypes.STRING,
        activity: DataTypes.STRING,
    },
    {
        sequelize,
        timestamps: true,
        createdAt: "createTimestamp",
        updatedAt: "updateTimestamp",
    }
);

module.exports = SocietyActivity;
