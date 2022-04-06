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
import com.wh.admin.data.society.bbs.PostReply

@Composable
fun UserReplyListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo
    var replies by remember {
        mutableStateOf(emptyList<PostReply>())
    }

    LaunchedEffect(Unit) {
        activity.http.allUserReply(userInfo.id) { replies = it }
    }

    LazyColumn(content = {
        items(
            items = replies,
            key = { item: PostReply -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "标题：${it.postTitle}")
                    SingleLineText(text = "回复内容：${it.reply}")
                    SingleLineText(text = "设备尾巴：${it.deviceName}")
                    SingleLineText(text = "社团名称：${it.societyName}")
                    SingleLineText(text = "发帖时间：${it.createTSFmt()}")
                    SingleLineText(text = "更新时间：${it.updateTSFmt()}")
                    Row {
                        TextButton(onClick = {
                            activity.alert.alertConfirm {
                                activity.http.adminPostReplyDelete(it.id) {
                                    activity.http.allUserReply(userInfo.id) { replies = it }
                                }
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