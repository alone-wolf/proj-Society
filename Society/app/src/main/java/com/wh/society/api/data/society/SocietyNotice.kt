package com.wh.society.api.data.society

import com.wh.society.api.data.impl.IRequestBody
import com.wh.society.api.data.impl.IZTimestamp
import org.json.JSONObject

open class SocietyNotice : IRequestBody, IZTimestamp {
    var id: Int = 0
    open var title: String = ""
    open var notice: String = ""
    open var postUserId: Int = 0
    var postUsername: String = ""
    open var societyId: Int = 0
    var societyName: String = ""

    open var permissionLevel: Int = 10
    override fun toJSONObject(): JSONObject {
        val j = JSONObject()
        j.put("title", title)
        j.put("notice", notice)
        j.put("postUserId", postUserId)
        j.put("societyId", societyId)
        j.put("permissionLevel", permissionLevel)
        return j
    }

    override fun toString(): String {
        return "SocietyNotice(id=$id, title='$title', notice='$notice', postUserId=$postUserId, postUsername=$postUsername, societyId=$societyId, societyName='$societyName', permissionLevel=$permissionLevel)"
    }

    override val updateTimestamp: String = ""
    override val createTimestamp: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocietyNotice

        if (id != other.id) return false
        if (title != other.title) return false
        if (notice != other.notice) return false
        if (postUserId != other.postUserId) return false
        if (postUsername != other.postUsername) return false
        if (societyId != other.societyId) return false
        if (societyName != other.societyName) return false
        if (permissionLevel != other.permissionLevel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + notice.hashCode()
        result = 31 * result + postUserId
        result = 31 * result + postUsername.hashCode()
        result = 31 * result + societyId
        result = 31 * result + societyName.hashCode()
        result = 31 * result + permissionLevel
        return result
    }

}