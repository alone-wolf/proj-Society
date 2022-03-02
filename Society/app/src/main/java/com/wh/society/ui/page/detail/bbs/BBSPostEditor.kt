package com.wh.society.ui.page.detail.bbs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.ReturnObjectData
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold


@ExperimentalMaterialApi
@Composable
fun BBSPostEditor(requestHolder: RequestHolder) {

    var postData by remember {
        mutableStateOf(ReturnObjectData.blank<Post>())
    }

    LaunchedEffect(Unit){
        requestHolder.apiViewModel.societyBBSPostById(requestHolder.trans.postId){
            postData = it
        }
    }

    var title by remember {
        mutableStateOf(postData.data!!.title)
    }
    var post by remember {
        mutableStateOf(postData.data!!.post)
    }
    var level by remember {
        mutableStateOf(postData.data!!.level)
    }

    GlobalScaffold(page = GlobalNavPage.DetailPostEditor, requestHolder = requestHolder, actions = {
        IconButton(onClick = {
            val userId = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).id
            val societyId = requestHolder.trans.bbs.id
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