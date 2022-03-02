package com.wh.society.api.data

import com.wh.common.typeExt.toTimestamp
import com.wh.common.util.TimeUtils
import com.wh.society.api.ServerApi
import com.wh.society.api.data.impl.RealIconUrl
import com.wh.society.util.CurrentTimeUtil

class SocietyChatMessage:RealIconUrl {
    var id:Int = 0
    var societyId:Int = 0
    var userId:Int = 0
    var username:String = ""
    var userIconUrl:String = ""
    var message:String = ""
    var createTimestamp:String = ""
    var updateTimestamp:String = ""


    override val realIconUrl: String
        get() = if (userIconUrl.startsWith("http://") || userIconUrl.startsWith("https://") || userIconUrl.isBlank())
            userIconUrl
        else
            ServerApi.picUrl(userIconUrl)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocietyChatMessage

        if (id != other.id) return false
        if (societyId != other.societyId) return false
        if (userId != other.userId) return false
        if (username != other.username) return false
        if (userIconUrl != other.userIconUrl) return false
        if (message != other.message) return false
        if (createTimestamp != other.createTimestamp) return false
        if (updateTimestamp != other.updateTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + societyId
        result = 31 * result + userId
        result = 31 * result + username.hashCode()
        result = 31 * result + userIconUrl.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChatMessage(id=$id, societyId=$societyId, userId=$userId, username='$username', userIconUrl='$userIconUrl', message='$message', createTimestamp='$createTimestamp', updateTimestamp='$updateTimestamp')"
    }

    val createTimestamp2ymdhms: String
    get() = CurrentTimeUtil.utcTS2ms("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").toTimestamp(TimeUtils.fullDateTimeFormat)


}