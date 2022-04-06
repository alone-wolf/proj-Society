package com.wh.admin.ui.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wh.admin.MainActivity
import com.wh.admin.data.society.bbs.Post

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

    LaunchedEffect(Unit){
        dataPrepare.invoke()
    }

    LazyColumn(content = {
        items(
            items = postList,
            key = { item: Post -> item.hashCode() },
            itemContent = {
                Row {
                    Text(text = it.title)
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}