package com.wh.society.api.data.society.bbs

import com.wh.society.api.ServerApi
import com.wh.society.api.data.impl.IRequestBody
import com.wh.society.api.data.impl.IZTimestamp
import com.wh.society.api.data.impl.IRealIconUrl
import org.json.JSONObject

open class PostReply:IRealIconUrl,IRequestBody,IZTimestamp{
    var id: Int = 0
    var societyId: Int = 0
    var societyName: String = ""
    var postId: Int = 0
    var postTitle: String = ""
    var userId: Int = 0
    var userIconUrl: String = ""
    var username: String = ""
    open var reply: String = ""
    var deviceName: String = ""
    override var createTimestamp: String = ""
    override var updateTimestamp: String = ""

    companion object{
        fun new(societyId:Int,postId:Int,userId:Int,reply:String,deviceName:String): PostReply {
            return PostReply().apply {
                this.societyId = societyId
                this.postId = postId
                this.userId = userId
                this.reply = reply
                this.deviceName = deviceName
            }
        }
    }


    override fun toJSONObject(): JSONObject {
        val j = JSONObject()
        j.put("societyId",societyId)
        j.put("postId",postId)
        j.put("userId",userId)
        j.put("reply",reply)
        j.put("deviceName",deviceName)
        return j
    }

    override val realIconUrl: String
        get() = if (userIconUrl.startsWith("http://") || userIconUrl.startsWith("https://") || userIconUrl.isBlank())
            userIconUrl
        else
            ServerApi.userPicUrl(userIconUrl)

    override fun toString(): String {
        return "PostReply(id=$id, societyId=$societyId, societyName='$societyName', postId=$postId, postTitle='$postTitle', userId=$userId, userIconUrl='$userIconUrl', username='$username', reply='$reply', deviceName='$deviceName', createTimestamp='$createTimestamp', updateTimestamp='$updateTimestamp')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostReply

        if (id != other.id) return false
        if (societyId != other.societyId) return false
        if (societyName != other.societyName) return false
        if (postId != other.postId) return false
        if (postTitle != other.postTitle) return false
        if (userId != other.userId) return false
        if (userIconUrl != other.userIconUrl) return false
        if (username != other.username) return false
        if (reply != other.reply) return false
        if (deviceName != other.deviceName) return false
        if (createTimestamp != other.createTimestamp) return false
        if (updateTimestamp != other.updateTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + societyId
        result = 31 * result + societyName.hashCode()
        result = 31 * result + postId
        result = 31 * result + postTitle.hashCode()
        result = 31 * result + userId
        result = 31 * result + userIconUrl.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + reply.hashCode()
        result = 31 * result + deviceName.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }


}