package com.wh.society.ui.page.detail.society.bbs

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.ReturnObjectData
import com.wh.society.api.data.shadow.PostShadow
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold


@ExperimentalMaterialApi
@Composable
fun BBSPostEditor(requestHolder: RequestHolder) {

//    var postData by remember {
//        mutableStateOf(Post())
//    }

    val post = PostShadow()


//    LaunchedEffect(Unit) {
//        requestHolder.apiViewModel.societyBBSPostById(requestHolder.trans.postId) {
//            postData = it.notNullOrBlank(Post())
//        }
//    }

    GlobalScaffold(page = GlobalNavPage.DetailPostEditor, requestHolder = requestHolder, actions = {
        IconButton(onClick = {
            post.userId = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).id
            post.societyId = requestHolder.trans.bbs.id
            post.deviceName = requestHolder.deviceName
            requestHolder.apiViewModel.societyBBSPostCreate(post) {
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
                    value = post.title,
                    onValueChange = { post.title = it },
                    placeholder = { Text(text = "title here ...") },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Box {
                    var showDropDown by remember {
                        mutableStateOf(false)
                    }
                    val map = mapOf(
                        0 to "0：所有人可见",
                        10 to "10：成员可见",
                        100 to "100：管理可见"
                    )
                    DropdownMenu(
                        expanded = showDropDown,
                        onDismissRequest = { showDropDown = false }) {
                        map.entries.forEach {
                            DropdownMenuItem(onClick = { post.level = it.key }) {
                                Text(text = it.value)
                            }
                        }
                    }
                    TextButton(onClick = { showDropDown = true }) {
                        Text(text = map[post.level].toString())
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                TextField(
                    value = post.post,
                    onValueChange = { post.post = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

        }
    }
}