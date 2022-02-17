const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class UserLogin extends Model {}

UserLogin.init(
  {
    id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
    userId: DataTypes.INTEGER,
    cookieToken: {
      type: DataTypes.UUID,
      defaultValue: Sequelize.UUIDV4,
    },
    loginTimestamp: { type: DataTypes.STRING, defaultValue: "" },
    expireTimestamp: { type: DataTypes.STRING, defaultValue: "" },
  },
  {
    sequelize,
    timestamps: true,
    createdAt: "createTimestamp",
    updatedAt: "updateTimestamp",
  }
);

module.exports = UserLogin;
