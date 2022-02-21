const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class User extends Model { }

User.init(
  {
    id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
    name: DataTypes.STRING,
    username: DataTypes.STRING,
    college: {
      defaultValue: "未知",
      type: DataTypes.STRING
    },
    phone: { type: DataTypes.STRING, defaultValue: "" },
    email: { type: DataTypes.STRING, defaultValue: "" },
    studentNumber: { type: DataTypes.STRING, defaultValue: "" },
    password: DataTypes.STRING,
    describe: { type: DataTypes.STRING, defaultValue: "" },
    iconUrl: { type: DataTypes.STRING, defaultValue: "" },
    token: {
      type: DataTypes.UUID,
      defaultValue: Sequelize.UUIDV4,
    },
  },
  {
    sequelize,
    timestamps: true,
    createdAt: "createTimestamp",
    updatedAt: "updateTimestamp",
  }
);

module.exports = User;
