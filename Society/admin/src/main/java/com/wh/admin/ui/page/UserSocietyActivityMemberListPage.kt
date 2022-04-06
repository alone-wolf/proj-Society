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
import com.wh.admin.data.society.SocietyActivity

@Composable
fun UserSocietyActivityMemberListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo

    val activityList = activity.serverDataViewModel.allActivity

    var activityJointList by remember {
        mutableStateOf(emptyList<SocietyActivity>())
    }

    val dataPrepare: () -> Unit = {

        activity.http.allUserSocietyActivityMember(userInfo.id) { activityMemberList ->
            activityJointList =
                activityList.filter { activityMemberList.any { item -> item.societyId == it.societyId } }
        }
    }

    LaunchedEffect(Unit) {
        dataPrepare.invoke()
    }

    LazyColumn(content = {
        items(
            items = activityJointList,
            key = { item: SocietyActivity -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
//                        activity.nav.toPostDetail(it
                    }
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
//                    SingleLineText(text = "标题：${it.}")
                    SingleLineText(text = "社团名称：${it.societyName}")
//                    SingleLineText(text = "发帖时间：${it.createTSFmt()}")
//                    SingleLineText(text = "更新时间：${it.updateTSFmt()}")
                    Row {
                        TextButton(onClick = {
                            activity.alert.alertConfirm {
                                activity.http.adminPostDelete(it.id, dataPrepare)
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