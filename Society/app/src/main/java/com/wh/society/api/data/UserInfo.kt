package com.wh.society.api.data

import com.wh.society.api.ServerApi
import com.wh.society.api.data.impl.RealIconUrl
import com.wh.society.impl.IContain

class UserInfo : IContain,RealIconUrl {
    var id: Int = 0
    var username: String = ""
    var email: String = ""
    var studentNumber: String = ""
    var iconUrl: String = ""
    var phone: String = ""
    var name: String = ""
    var college: String = ""
    var createTimestamp: String = ""
    var updateTimestamp: String = ""

    override val realIconUrl: String
        get() = if (iconUrl.startsWith("http://") || iconUrl.startsWith("https://") || iconUrl.isBlank())
            iconUrl
        else
            ServerApi.picUrl(iconUrl)

    override val checkArray: Array<String>
        get() = arrayOf(username)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserInfo

        if (id != other.id) return false
        if (username != other.username) return false
        if (email != other.email) return false
        if (studentNumber != other.studentNumber) return false
        if (iconUrl != other.iconUrl) return false
        if (phone != other.phone) return false
        if (name != other.name) return false
        if (college != other.college) return false
        if (createTimestamp != other.createTimestamp) return false
        if (updateTimestamp != other.updateTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + studentNumber.hashCode()
        result = 31 * result + iconUrl.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + college.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserInfo(id=$id, username='$username', email='$email', studentNumber='$studentNumber', iconUrl='$iconUrl', phone='$phone', name='$name', college='$college', createTimestamp='$createTimestamp', updateTimestamp='$updateTimestamp')"
    }


}