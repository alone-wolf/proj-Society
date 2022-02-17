const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class Society extends Model { }

Society.init(
  {
    id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
    name: { type: DataTypes.STRING, unique: true },
    openTimestamp: DataTypes.INTEGER,
    iconUrl: {
      defaultValue: "",
      type: DataTypes.STRING
    },
    photos: DataTypes.JSON,
    describe: DataTypes.STRING,
    bbsName: DataTypes.STRING,
    bbsIconUrl: {
      defaultValue: "",
      type: DataTypes.STRING
    },
    bbsDescribe: DataTypes.STRING,
    bbsAllowGuestPost: {
      defaultValue: true,
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

module.exports = Society;
