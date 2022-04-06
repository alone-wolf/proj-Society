package com.wh.admin.data.society

class SocietyActivityMember {
    var id:Int = 0

    var activityId:Int = 0
    var societyId:Int = 0
    var societyName:String = ""
    var userId:Int = 0
    var username:String = ""
    var userIconUrl:String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocietyActivityMember

        if (id != other.id) return false
        if (activityId != other.activityId) return false
        if (societyId != other.societyId) return false
        if (societyName != other.societyName) return false
        if (userId != other.userId) return false
        if (username != other.username) return false
        if (userIconUrl != other.userIconUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + activityId
        result = 31 * result + societyId
        result = 31 * result + societyName.hashCode()
        result = 31 * result + userId
        result = 31 * result + username.hashCode()
        result = 31 * result + userIconUrl.hashCode()
        return result
    }

    override fun toString(): String {
        return "SocietyActivityMember(id=$id, activityId=$activityId, societyId=$societyId, societyName='$societyName', userId=$userId, username='$username', userIconUrl='$userIconUrl')"
    }


}