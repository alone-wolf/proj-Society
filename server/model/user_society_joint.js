const sequelize = require("../sequelize/sequelize");
const { DataTypes, Model, Sequelize } = require("sequelize");

class UserSocietyJoint extends Model { }

UserSocietyJoint.init(
  {
    id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
    userId: DataTypes.INTEGER,
    societyId: DataTypes.INTEGER,
    permissionLevel: {
      // 1 for guest
      // 11 for member
      // 111 for admin
      type: DataTypes.INTEGER,
      defaultValue: 11,
    },
    username: DataTypes.STRING,
    userIconUrl: DataTypes.STRING,
  },
  {
    sequelize,
    timestamps: true,
    createdAt: "createTimestamp",
    updatedAt: "updateTimestamp",
  }
);

module.exports = UserSocietyJoint;
