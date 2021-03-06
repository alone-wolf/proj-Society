package com.wh.admin.data.society

import com.wh.admin.data.impl.IZTimestamp

class SocietyMemberRequest:IZTimestamp {
    var id: Int = 0
    var societyId: Int = 0
    var societyName: String = ""
    var userId: Int = 0
    var username: String = ""
    var isJoin: Boolean = true
    var isPass:Boolean = false
    var request: String = ""
    var isDealDone:Boolean = false
    override var updateTimestamp:String = ""
    override val createTimestamp: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocietyMemberRequest

        if (id != other.id) return false
        if (societyId != other.societyId) return false
        if (societyName != other.societyName) return false
        if (userId != other.userId) return false
        if (username != other.username) return false
        if (isJoin != other.isJoin) return false
        if (isPass != other.isPass) return false
        if (request != other.request) return false
        if (isDealDone != other.isDealDone) return false
        if (updateTimestamp != other.updateTimestamp) return false
        if (createTimestamp != other.createTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + societyId
        result = 31 * result + societyName.hashCode()
        result = 31 * result + userId
        result = 31 * result + username.hashCode()
        result = 31 * result + isJoin.hashCode()
        result = 31 * result + isPass.hashCode()
        result = 31 * result + request.hashCode()
        result = 31 * result + isDealDone.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "SocietyMemberRequest(id=$id, societyId=$societyId, societyName='$societyName', userId=$userId, username='$username', isJoin=$isJoin, isPass=$isPass, request='$request', isDealDone=$isDealDone, updateTimestamp='$updateTimestamp', createTimestamp='$createTimestamp')"
    }
}