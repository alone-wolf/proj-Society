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
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.ext.empty

@Composable
fun UserPostListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo

    var posts by remember {
        mutableStateOf(emptyList<Post>())
    }

    val dataPrepare: () -> Unit = {
        activity.http.allUserPost(userInfo.id) {
            posts = it
        }
    }

    LaunchedEffect(Unit) {
        dataPrepare.invoke()
    }

    LazyColumn(content = {
        items(
            items = posts,
            key = { item: Post -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity.nav.toPostDetail(it)
                    }
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "标题：${it.title}")
                    SingleLineText(text = "社团名称：${it.societyName}")
                    SingleLineText(text = "发帖时间：${it.createTSFmt()}")
                    SingleLineText(text = "更新时间：${it.updateTSFmt()}")
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

        empty(posts)

    }, modifier = Modifier.fillMaxSize())
}