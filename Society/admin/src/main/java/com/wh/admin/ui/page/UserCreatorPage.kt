package com.wh.admin.ui.page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.admin.MainActivity
import com.wh.admin.data.shadow.UserInfoShadow
import com.wh.admin.ext.borderButton
import com.wh.admin.ext.textFiled
import kotlinx.coroutines.launch

@Composable
fun UserCreatorPage(activity: MainActivity) {
    val userShadow = UserInfoShadow()
    LazyColumn(content = {
        textFiled(userShadow.username, "用户名") { userShadow.username = it }
        textFiled(userShadow.name, "姓名") { userShadow.name = it }
        textFiled(userShadow.phone, "手机号") { userShadow.phone = it }
        textFiled(userShadow.email, "邮箱") { userShadow.email = it }
        borderButton("保存") {
            activity.coroutineScope.launch {
                activity.serverDataViewModel.adminUserCreate(userShadow, {
                    activity.coroutineScope.launch {
                        activity.serverDataViewModel.getAllUser { }
                    }
                    activity.navBack.invoke()
                }) {}
            }
        }
        borderButton("保存并继续") {
            activity.coroutineScope.launch {
                activity.serverDataViewModel.adminUserCreate(userShadow, {
                    userShadow.clear()
                }) {}
            }
        }
        borderButton("取消") { activity.navBack.invoke() }
    }, modifier = Modifier.fillMaxSize())
}