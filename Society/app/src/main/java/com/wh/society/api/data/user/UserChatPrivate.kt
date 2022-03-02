package com.wh.society.api.data.user

import com.wh.society.api.ServerApi
import com.wh.society.api.data.impl.RealIconUrl

class UserChatPrivate : RealIconUrl {
    var id: Int = 0
    var userId: Int = 0
    var username: String = ""
    var userIconUrl: String = ""
    var opUserId: Int = 0
    var message: String = ""
    override val realIconUrl: String
        get() = if (userIconUrl.startsWith("http://") || userIconUrl.startsWith("https://") || userIconUrl.isBlank())
            userIconUrl
        else
            ServerApi.picUrl(userIconUrl)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserChatPrivate

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (username != other.username) return false
        if (userIconUrl != other.userIconUrl) return false
        if (opUserId != other.opUserId) return false
        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId
        result = 31 * result + username.hashCode()
        result = 31 * result + userIconUrl.hashCode()
        result = 31 * result + opUserId
        result = 31 * result + message.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChatPrivate(id=$id, userId=$userId, username='$username', userIconUrl='$userIconUrl', opUserId=$opUserId, message='$message')"
    }


}

//id: { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
// user0
//userId: DataTypes.INTEGER,
//username: DataTypes.STRING,
//userIconUrl: {
//    defaultValue: "",
//    type: DataTypes.STRING
//},
// user1
//opUserId: DataTypes.INTEGER,
//
//message: DataTypes.STRING