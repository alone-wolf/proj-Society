const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class SocietyMemberRequest extends Model { }

SocietyMemberRequest.init(
    {
        id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
        societyId: DataTypes.INTEGER,
        societyName: DataTypes.STRING,
        userId: DataTypes.INTEGER,
        username: DataTypes.STRING,
        isJoin: { // isJoin or isLeave
            defaultValue: true,
            type: DataTypes.BOOLEAN
        },

        request: DataTypes.STRING,
        isDealDone: {
            defaultValue: false,
            type: DataTypes.BOOLEAN
        },
        isAgreed: {
            defaultValue: false,
            type: DataTypes.BOOLEAN
        }
    },
    {
        sequelize,
        timestamps: true,
        createdAt: "createTimestamp",
        updatedAt: "updateTimestamp",
    }
);

module.exports = SocietyMemberRequest;
