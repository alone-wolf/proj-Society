package com.wh.society.api.data.society

class SocietyActivityRequest {
    var id:Int = 0
    var societyId:Int = 0
    var societyName:String = ""

    var userId:Int = 0
    var username:String = ""
    var userIconUrl:String = ""

    var isJoin:Boolean = true
    var request:String = ""

    var isDealDone:Boolean = false
    var isAgreed:Boolean = false


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocietyActivityRequest

        if (id != other.id) return false
        if (societyId != other.societyId) return false
        if (societyName != other.societyName) return false
        if (userId != other.userId) return false
        if (username != other.username) return false
        if (userIconUrl != other.userIconUrl) return false
        if (isJoin != other.isJoin) return false
        if (request != other.request) return false
        if (isDealDone != other.isDealDone) return false
        if (isAgreed != other.isAgreed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + societyId
        result = 31 * result + societyName.hashCode()
        result = 31 * result + userId
        result = 31 * result + username.hashCode()
        result = 31 * result + userIconUrl.hashCode()
        result = 31 * result + isJoin.hashCode()
        result = 31 * result + request.hashCode()
        result = 31 * result + isDealDone.hashCode()
        result = 31 * result + isAgreed.hashCode()
        return result
    }

    override fun toString(): String {
        return "SocietyActivityRequest(id=$id, societyId=$societyId, societyName='$societyName', userId=$userId, username='$username', userIconUrl='$userIconUrl', isJoin=$isJoin, request='$request', isDealDone=$isDealDone, isAgreed=$isAgreed)"
    }
}