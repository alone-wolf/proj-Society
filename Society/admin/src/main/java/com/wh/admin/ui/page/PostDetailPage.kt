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
import com.wh.admin.data.society.bbs.PostReply

@Composable
fun PostDetailPage(activity: MainActivity) {

    val post = activity.selectedPost

    var replies by remember {
        mutableStateOf(emptyList<PostReply>())
    }

    LaunchedEffect(Unit) {
        activity.http.getAllPostReply(post.id) { replies = it }
    }

    LazyColumn(content = {

        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable {}
                .padding(horizontal = 20.dp, vertical = 10.dp)) {
                SingleLineText(text = "标题：${post.title}")
                SingleLineText(text = "帖子内容：${post.post}")
                SingleLineText(text = "社团名称：${post.societyName}")
                SingleLineText(text = "发帖时间：${post.createTSFmt()}")
                SingleLineText(text = "更新时间：${post.updateTSFmt()}")
            }
        }

        items(
            items = replies,
            key = { item: PostReply -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "回复内容：${it.reply}")
                    SingleLineText(text = "设备尾巴：${it.deviceName}")
                    SingleLineText(text = "回复时间：${it.createTSFmt()}")
                    SingleLineText(text = "更新时间：${it.updateTSFmt()}")
                }
            }
        )
    }, modifier = Modifier.fillMaxSize())

}