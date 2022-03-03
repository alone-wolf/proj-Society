package com.wh.society.api.data.society

class SocietyNotice {
    var id: Int = 0
    var title: String = ""
    var notice: String = ""
    var postUserId: Int = 0
    var postUsername: Int = 0
    var societyId: Int = 0
    var societyName: String = ""

    var permissionLevel: Int = 10
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
        result = 31 * result + postUsername
        result = 31 * result + societyId
        result = 31 * result + societyName.hashCode()
        result = 31 * result + permissionLevel
        return result
    }

    override fun toString(): String {
        return "SocietyNotice(id=$id, title='$title', notice='$notice', postUserId=$postUserId, postUsername=$postUsername, societyId=$societyId, societyName='$societyName', permissionLevel=$permissionLevel)"
    }

}