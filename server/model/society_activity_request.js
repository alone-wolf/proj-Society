const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class SocietyActivityRequest extends Model { }

SocietyActivityRequest.init(
    {
        id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
        societyId: DataTypes.INTEGER,
        societyName: DataTypes.STRING,
        userId: DataTypes.INTEGER,
        username: DataTypes.STRING,
        userIconUrl: DataTypes.STRING,
        deviceName: DataTypes.STRING
    },
    {
        sequelize,
        timestamps: true,
        createdAt: "createTimestamp",
        updatedAt: "updateTimestamp",
    }
);

module.exports = SocietyActivityRequest;
