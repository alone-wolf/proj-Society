package com.wh.admin.ui.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wh.admin.MainActivity
import com.wh.admin.data.society.Society
import com.wh.admin.data.society.SocietyMember
import com.wh.admin.data.society.bbs.Post

@Composable
fun SocietyMemberListPage(activity: MainActivity) {
    val society = activity.selectedSociety

    var memberList by remember {
        mutableStateOf(emptyList<SocietyMember>())
    }

    val dataPrepare = {
        activity.http.allUserSocietyMember(society.id) {
            memberList = it
        }
    }

    LaunchedEffect(Unit) {
        dataPrepare.invoke()
    }

    LazyColumn(content = {
        items(
            items = memberList,
            key = { item: SocietyMember -> item.hashCode() },
            itemContent = {
                Row {
                    Text(text = it.username)
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}