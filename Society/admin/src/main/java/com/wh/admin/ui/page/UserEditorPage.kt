package com.wh.admin.ui.page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.admin.MainActivity
import com.wh.admin.ext.borderButton
import com.wh.admin.ext.textFiled

@Composable
fun UserEditorPage(activity: MainActivity) {
    val userShadow = activity.selectedUserInfo.shadow()
    LazyColumn(content = {
        textFiled(userShadow.username, "用户名") { userShadow.username = it }
        textFiled(userShadow.name, "姓名") { userShadow.name = it }
        textFiled(userShadow.phone, "手机号") { userShadow.phone = it }
        textFiled(userShadow.email, "邮箱") { userShadow.email = it }
        textFiled(userShadow.password, "密码") { userShadow.password = it }
        borderButton("保存") {
            activity.http.adminUserUpdate(userShadow) {
                activity.http.allUser()
                activity.http.adminUserById(userShadow.id) {
                    activity.selectedUserInfo = it
                }
                activity.nav.back.invoke()
            }
        }
        borderButton("取消") { activity.nav.back.invoke() }
    }, modifier = Modifier.fillMaxSize())
}