package com.wh.admin.data.shadow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import com.wh.admin.data.user.UserInfo

class UserInfoShadow : UserInfo() {

    override var username: String by mutableStateOf(super.username)
    override var email: String by mutableStateOf(super.email)
    override var iconUrl: String by mutableStateOf(super.iconUrl)
    override var phone: String by mutableStateOf(super.phone)
    override var name: String by mutableStateOf(super.name)
    override var college: String by mutableStateOf(super.college)
    override var password:String by mutableStateOf(super.password)

    fun clear(){
        username = ""
        email = ""
        iconUrl = ""
        phone = ""
        name = ""
        college = ""
        password = ""
    }

    companion object {
        fun fromUserInfo(u: UserInfo): UserInfoShadow {
            val a = UserInfoShadow()
            a.id = u.id
            a.username = u.username
            a.email = u.email
            a.studentNumber = u.studentNumber
            a.iconUrl = u.iconUrl
            a.phone = u.phone
            a.name = u.name
            a.college = u.college
            a.password = u.password
            return a
        }
    }


}