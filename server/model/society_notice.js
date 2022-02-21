const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class SocietyNotice extends Model { }

SocietyNotice.init(
    {
        id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
        societyId: DataTypes.INTEGER,
        societyName: DataTypes.STRING,
        postUserId: DataTypes.INTEGER,
        postUsername: DataTypes.STRING,
        permissionLevel: {
            defaultValue: 10,
            type: DataTypes.INTEGER
        },
        title: DataTypes.STRING,
        notice: DataTypes.STRING
    },
    {
        sequelize,
        timestamps: true,
        createdAt: "createTimestamp",
        updatedAt: "updateTimestamp",
    }
);

module.exports = SocietyNotice;
