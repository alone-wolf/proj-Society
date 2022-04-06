package com.wh.admin.ui.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wh.admin.MainActivity
import com.wh.admin.data.society.SocietyActivity
import com.wh.admin.data.society.bbs.Post

@Composable
fun SocietyActivityListPage(activity: MainActivity) {
    val society = activity.selectedSociety

    var activityList by remember {
        mutableStateOf(emptyList<SocietyActivity>())
    }

    val dataPrepare = {
        activity.http.allSocietyActivity(society.id) {
            activityList = it
        }
    }

    LaunchedEffect(Unit) {
        dataPrepare.invoke()
    }

    LazyColumn(content = {
        items(
            items = activityList,
            key = { item: SocietyActivity -> item.hashCode() },
            itemContent = {
                Row {
                    Text(text = it.title)
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}