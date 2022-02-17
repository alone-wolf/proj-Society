package com.wh.society.api.data

class Post {
    var id: Int = 0
    var societyId: Int = 0
    var societyName: String = ""
    var userId: Int = 0
    var username: String = ""
    var userIconUrl: String = ""
    var deviceName: String = ""
    var level: Int = 0
    var title: String = ""
    var post: String = ""
    var createTimestamp: String = ""
    var updateTimestamp: String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false
        if (societyId != other.societyId) return false
        if (societyName != other.societyName) return false
        if (userId != other.userId) return false
        if (username != other.username) return false
        if (userIconUrl != other.userIconUrl) return false
        if (deviceName != other.deviceName) return false
        if (level != other.level) return false
        if (title != other.title) return false
        if (post != other.post) return false
        if (createTimestamp != other.createTimestamp) return false
        if (updateTimestamp != other.updateTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + societyId
        result = 31 * result + societyName.hashCode()
        result = 31 * result + userId
        result = 31 * result + username.hashCode()
        result = 31 * result + userIconUrl.hashCode()
        result = 31 * result + deviceName.hashCode()
        result = 31 * result + level
        result = 31 * result + title.hashCode()
        result = 31 * result + post.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }


    override fun toString(): String {
        return "Post(id=$id, societyId=$societyId, societyName='$societyName', userId=$userId, username='$username', userIconUrl='$userIconUrl', deviceName='$deviceName', level=$level, title='$title', post='$post', createTimestamp='$createTimestamp', updateTimestamp='$updateTimestamp')"
    }


}