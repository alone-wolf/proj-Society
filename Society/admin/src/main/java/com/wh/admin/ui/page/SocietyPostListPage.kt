package com.wh.admin.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.admin.MainActivity
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.ext.empty

@Composable
fun SocietyPostListPage(activity: MainActivity) {
    val society = activity.selectedSociety

    var postList by remember {
        mutableStateOf(emptyList<Post>())
    }

    val dataPrepare = {
        activity.http.allSocietyPost(society.id) {
            postList = it
        }
    }

    LaunchedEffect(Unit) {
        dataPrepare.invoke()
    }

    LazyColumn(content = {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 16.dp)
            ) {
                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 16.dp)
                    ) {
                        Text(text = "本论坛发帖数量：${postList.size}")
                    }
                }
            }
        }

        items(
            items = postList,
            key = { item: Post -> item.hashCode() },
            itemContent = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity.nav.toPostDetail(it)
                    }
                    .padding(vertical = 10.dp, horizontal = 16.dp)) {
                    Text(text = "帖子标题：${it.title}")
                    Text(text = "发帖社团：${it.societyName}")
                    Text(text = "发帖设备：${it.deviceName}")
                    Text(text = "发帖时间：${it.createTSFmt()}")
                }
            }
        )

        empty(postList)

    }, modifier = Modifier.fillMaxSize())
}