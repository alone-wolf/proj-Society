package com.wh.admin.data.user

import com.wh.admin.componment.ServerApi
import com.wh.admin.data.impl.IRequestBody
import com.wh.admin.data.impl.IZTimestamp
import com.wh.admin.data.shadow.UserInfoShadow
import com.wh.society.api.data.impl.IContain
import com.wh.admin.data.impl.IRealIconUrl
import org.json.JSONObject


open class UserInfo : IContain, IRealIconUrl, IRequestBody, IZTimestamp {
    open var id: Int = 0
    open var username: String = ""
    open var email: String = ""
    open var studentNumber: String = ""
    open var iconUrl: String = ""
    open var phone: String = ""
    open var name: String = ""
    open var college: String = ""
    open var password: String = ""
    override var createTimestamp: String = ""
    override var updateTimestamp: String = ""

    fun copy(): UserInfo {
        return UserInfo().apply {
            this.id = this@UserInfo.id
            this.username = this@UserInfo.username
            this.email = this@UserInfo.email
            this.studentNumber = this@UserInfo.studentNumber
            this.iconUrl = this@UserInfo.iconUrl
            this.phone = this@UserInfo.phone
            this.name = this@UserInfo.name
            this.college = this@UserInfo.college
            this.password = this@UserInfo.password
            this.createTimestamp = this@UserInfo.createTimestamp
            this.updateTimestamp = this@UserInfo.updateTimestamp
        }
    }

    override fun toJSONObject(): JSONObject {
        val a = JSONObject()
        a.put("id", id)
        a.put("username", username)
        a.put("email", email)
        a.put("studentNumber", studentNumber)
        a.put("iconUrl", iconUrl)
        a.put("phone", phone)
        a.put("name", name)
        a.put("college", college)
        a.put("password", password)
        return a
    }

    fun shadow(): UserInfoShadow {
        return UserInfoShadow.fromUserInfo(this)
    }

    override val realIconUrl: String
        get() = if (iconUrl.startsWith("http://") || iconUrl.startsWith("https://") || iconUrl.isBlank())
            iconUrl
        else
            ServerApi.userPicUrl(iconUrl)


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
        if (password != other.password) return false
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
        result = 31 * result + password.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserInfo(id=$id, username='$username', email='$email', studentNumber='$studentNumber', iconUrl='$iconUrl', phone='$phone', name='$name', college='$college', password='$password', createTimestamp='$createTimestamp', updateTimestamp='$updateTimestamp')"
    }


}