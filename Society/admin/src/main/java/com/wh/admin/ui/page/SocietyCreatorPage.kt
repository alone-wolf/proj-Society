package com.wh.admin.ui.page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.admin.MainActivity
import com.wh.admin.ext.borderButton
import com.wh.admin.ext.textFiled
import com.wh.admin.data.shadow.SocietyShadow

@Composable
fun SocietyCreatorPage(activity: MainActivity) {
    val societyShadow = SocietyShadow()

    LazyColumn(
        content = {
            textFiled(societyShadow.name, "社团名称") { societyShadow.name = it }
            textFiled(societyShadow.describe, "社团简介") { societyShadow.describe = it }
            textFiled(societyShadow.bbsName, "论坛名称") { societyShadow.bbsName = it }
            textFiled(societyShadow.bbsDescribe, "论坛简介") { societyShadow.bbsDescribe = it }

            borderButton("保存") {

                activity.http.adminSocietyCreate(societyShadow){
                    activity.http.allSociety()
                    activity.nav.back.invoke()
                }
            }
            borderButton("取消") { activity.nav.back.invoke() }
        },
        modifier = Modifier.fillMaxSize()
    )
}