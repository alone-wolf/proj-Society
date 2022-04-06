package com.wh.admin.ui.page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.admin.MainActivity
import com.wh.admin.ext.borderButton
import com.wh.admin.ext.textFiled

@Composable
fun SocietyEditorPage(activity: MainActivity) {
    val societyShadow = activity.selectedSociety.shadow()

    LazyColumn(
        content = {
            textFiled(societyShadow.name, "社团名称") { societyShadow.name = it }
            textFiled(societyShadow.describe, "社团简介") { societyShadow.describe = it }
            textFiled(societyShadow.bbsName, "论坛名称") { societyShadow.bbsName = it }
            textFiled(societyShadow.bbsDescribe, "论坛简介") { societyShadow.bbsDescribe = it }

            borderButton("保存") {

                activity.http.adminSocietyUpdate(societyShadow){
                    activity.http.allSociety()

                    // TODO 增加更新本地选择的社团数据的逻辑
                    activity.nav.back.invoke()
                }
//                activity.http.adminUserUpdate(userShadow) {
//                    activity.http.allUser()
//                    activity.http.adminUserById(userShadow.id) {
//                        activity.selectedUserInfo = it
//                    }
//                    activity.nav.back.invoke()
//                }
            }
            borderButton("取消") { activity.nav.back.invoke() }
        },
        modifier = Modifier.fillMaxSize()
    )
}