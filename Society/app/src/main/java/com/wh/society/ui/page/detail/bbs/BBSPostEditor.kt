package com.wh.society.ui.page.detail.bbs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold

private var _title = ""
private var _post = ""
private var _level = 0

@ExperimentalMaterialApi
@Composable
fun BBSPostEditor(requestHolder: RequestHolder) {
    requestHolder.transPost?.let {
        _title = it.title
        _post = it.post
        _level = it.level
    }

    var title by remember {
        mutableStateOf(_title)
    }
    var post by remember {
        mutableStateOf(_post)
    }
    var level by remember {
        mutableStateOf(_level)
    }

    GlobalScaffold(page = GlobalNavPage.DetailPostEditor, requestHolder = requestHolder, actions = {
        IconButton(onClick = {
            val userId = requestHolder.userInfo.id
            val societyId = requestHolder.transBBS.id
            requestHolder.apiViewModel.societyBBSPostCreate(
                societyId = societyId,
                userId = userId,
                title = title,
                post = post,
                level = level,
                deviceName = requestHolder.deviceName
            ) {
                requestHolder.globalNav.goBack()
            }
        }) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "")
        }
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text(text = "title here ...") },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                TextField(
                    value = post,
                    onValueChange = { post = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )

            }
        }
    }
}