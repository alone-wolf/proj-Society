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
import com.wh.admin.data.society.bbs.Post

@Composable
fun UserPostListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo

    var posts by remember {
        mutableStateOf(emptyList<Post>())
    }

    LaunchedEffect(Unit) {
        activity.http.getAllUserPost(userInfo.id) {
            posts = it
        }
    }

    LazyColumn(content = {
        items(
            items = posts,
            key = { item: Post -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity.nav.navToPostDetail(it)
                    }
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "标题：${it.title}")
                    SingleLineText(text = "帖子内容：${it.post}")
                    SingleLineText(text = "社团名称：${it.societyName}")
                    SingleLineText(text = "发帖时间：${it.createTSFmt()}")
                    SingleLineText(text = "更新时间：${it.updateTSFmt()}")
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}