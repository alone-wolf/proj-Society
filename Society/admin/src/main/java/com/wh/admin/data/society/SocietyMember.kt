package com.wh.admin.data.society

import com.wh.admin.componment.AdminApi
import com.wh.admin.data.impl.IRealIconUrl
import com.wh.admin.data.impl.IZTimestamp

class SocietyMember : IRealIconUrl,IZTimestamp {
    var id:Int = 0
    var userId: Int = 0
    var societyId: Int = 0
    var permissionLevel: Int = 1
    var joinTimestamp: Long = 0L
    var username:String = ""
    var userIconUrl:String = ""
    override var createTimestamp: String = ""
    override var updateTimestamp: String = ""

    override val realIconUrl: String
        get() = if (userIconUrl.startsWith("http://") || userIconUrl.startsWith("https://") || userIconUrl.isBlank())
            userIconUrl
        else
            AdminApi.userPicUrl(userIconUrl)

    fun levelToString(): String {
        return when{
            permissionLevel>100->"Admin"
            permissionLevel>10->"Member"
            permissionLevel>0->"Guest"
            else -> "Unknown"
        }
    }

//    fun toUserInfo(): UserInfo {
//        return UserInfo().apply {
//            this.id = this@SocietyJoint.userId
//            this.username = this@SocietyJoint.username
//            this.
//        }
//    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocietyMember

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (societyId != other.societyId) return false
        if (permissionLevel != other.permissionLevel) return false
        if (joinTimestamp != other.joinTimestamp) return false
        if (username != other.username) return false
        if (userIconUrl != other.userIconUrl) return false
        if (createTimestamp != other.createTimestamp) return false
        if (updateTimestamp != other.updateTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId
        result = 31 * result + societyId
        result = 31 * result + permissionLevel
        result = 31 * result + joinTimestamp.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + userIconUrl.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "SocietyJoint(id=$id, userId=$userId, societyId=$societyId, level=$permissionLevel, joinTimestamp=$joinTimestamp, username='$username', userIconUrl='$userIconUrl', createTimestamp='$createTimestamp', updateTimestamp='$updateTimestamp')"
    }


}