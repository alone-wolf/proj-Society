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

@Composable
fun UserSocietyMemberRequestListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo

    var requestList by remember {
        mutableStateOf(emptyList<SocietyMemberRequest>())
    }

    val dataPrepare: () -> Unit = {
        activity.http.allUserSocietyMemberRequest(userInfo.id) { requestList = it }
    }

    LaunchedEffect(Unit){
        dataPrepare.invoke()
    }

    LazyColumn(
        content = {
            items(
                items = requestList,
                key = { item: SocietyMemberRequest -> item.hashCode() },
                itemContent = {
                    Row {
                        Text(text = it.toString())
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    )
}