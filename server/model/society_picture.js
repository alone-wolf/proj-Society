const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class SocietyPicture extends Model { }

SocietyPicture.init(
    {
        id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
        societyId: { type: DataTypes.INTEGER },
        originalFilename: DataTypes.STRING,
        newFilename: DataTypes.STRING,
    },
    {
        sequelize,
        timestamps: true,
        createdAt: "createTimestamp",
        updatedAt: "updateTimestamp",
    }
);

module.exports = SocietyPicture;