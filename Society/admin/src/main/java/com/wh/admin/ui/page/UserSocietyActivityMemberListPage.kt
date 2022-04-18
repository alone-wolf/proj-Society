package com.wh.admin.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.admin.MainActivity
import com.wh.admin.SingleLineText
import com.wh.admin.data.society.SocietyActivityMember

@Composable
fun UserSocietyActivityMemberListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo

    var activityJointList by remember {
        mutableStateOf(emptyList<SocietyActivityMember>())
    }

    val dataPrepare: () -> Unit = {

        activity.http.allUserSocietyActivityMember(userInfo.id) { activityMemberList ->
            activityJointList = activityMemberList
        }
    }

    LaunchedEffect(Unit) {
        dataPrepare.invoke()
    }

    LazyColumn(content = {
        items(
            items = activityJointList,
            key = { item: SocietyActivityMember -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "社团名称：${it.societyName}")
                    SingleLineText(text = "活动标题：${it.activityTitle}")
                    SingleLineText(text = "参加时间：${it.createTSFmt()}")
                    Row {
                        TextButton(onClick = {
                            activity.alert.alertConfirm {
                                activity.http.adminSocietyActivityMemberDelete(it.id, dataPrepare)
                            }
                        }) {
                            Text(text = "删除")
                        }
                    }
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}