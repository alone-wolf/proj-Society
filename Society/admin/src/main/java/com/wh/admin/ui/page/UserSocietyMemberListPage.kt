package com.wh.admin.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.admin.MainActivity
import com.wh.admin.SingleLineText
import com.wh.admin.data.society.Society
import com.wh.admin.data.society.SocietyMember

@Composable
fun UserSocietyMemberListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo
    var memberList by remember {
        mutableStateOf(emptyList<SocietyMember>())
    }

    var societyList by remember {
        mutableStateOf(emptyList<Society>())
    }
    LaunchedEffect(Unit) {
        memberList = activity.serverDataViewModel.getAllUserSocietyMember(userInfo.id) {}
        societyList = activity.serverDataViewModel.allSociety.filter { s ->
            memberList.any { sm -> s.id == sm.societyId }
        }
    }

    LazyColumn(content = {
        items(
            items = societyList,
            key = { item: Society -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "社团名称：${it.name}")
                    SingleLineText(text = "社团简介：${it.describe}")
//                    SingleLineText(text = "设备尾巴：${it.deviceName}")
//                    SingleLineText(text = "社团名称：${it.societyName}")
                    SingleLineText(text = "发帖时间：${it.createTimestamp}")
                    SingleLineText(text = "更新时间：${it.updateTimestamp}")
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}