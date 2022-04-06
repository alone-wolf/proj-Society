package com.wh.admin.ui.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wh.admin.MainActivity
import com.wh.admin.data.society.SocietyMemberRequest
import com.wh.admin.data.society.bbs.Post

@Composable
fun SocietyMemberRequestListPage(activity: MainActivity) {
    val society = activity.selectedSociety

    var memberRequestList by remember {
        mutableStateOf(emptyList<SocietyMemberRequest>())
    }

    val dataPrepare = {
        activity.http.allSocietyMemberRequest(society.id) {
            memberRequestList = it
        }
    }

    LaunchedEffect(Unit) {
        dataPrepare.invoke()
    }

    LazyColumn(content = {
        items(
            items = memberRequestList,
            key = { item: SocietyMemberRequest -> item.hashCode() },
            itemContent = {
                Row {
                    Text(text = it.request)
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}