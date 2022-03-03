package com.wh.society.ui.page.detail.bbs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.society.bbs.PostReply
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.ReturnObjectData
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold
import com.wh.society.ui.componment.PostItem
import com.wh.society.ui.componment.PostReplyItem


@ExperimentalMaterialApi
@Composable
fun BBSPostDetail(requestHolder: RequestHolder) {
    var postReplyList by remember {
        mutableStateOf(ReturnListData.blank<PostReply>())
    }
    var post by remember {
        mutableStateOf(ReturnObjectData.blank<Post>())
    }
    LaunchedEffect(Unit) {
        requestHolder.trans.postId.let {
            requestHolder.apiViewModel.societyBBSPostReplyList(it) { a ->
                postReplyList = a
            }
            requestHolder.apiViewModel.societyBBSPostById(it) { a ->
                post = a
            }
        }
    }

    GlobalScaffold(
        page = GlobalNavPage.DetailPost,
        requestHolder = requestHolder,
        fab = {
            FloatingActionButton(onClick = {
                requestHolder.alert.alertFor1TextFiled("reply post") {
                    requestHolder.apiViewModel.societyBBSPostReplyCreate(
                        societyId = requestHolder.trans.society.id,
                        postId = requestHolder.trans.postId,
                        userId = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).id,
                        reply = it,
                        deviceName = requestHolder.deviceName
                    ) {
                        requestHolder.apiViewModel.societyBBSPostReplyList(
                            postId = requestHolder.trans.postId
                        ) { item ->
                            postReplyList = item
                        }
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        },
        actions = {
            post.data?.let {
                if (it.userId == requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).id) {
                    IconButton(onClick = {
                        requestHolder.apiViewModel.societyBBSPostDelete(
                            userId = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).id,
                            postId = it.id
                        ) {
                            requestHolder.globalNav.goBack()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                    }
                }
            }
        }
    ) {
        LazyColumn(content = {
            item {
                PostItem(
                    requestHolder = requestHolder,
                    post = post.notNullOrBlank(Post()),
                    postMaxLine = Int.MAX_VALUE,
                    ignoreClick = true,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            items(
                items = postReplyList.data,
                key = { item: PostReply -> item.id },
                itemContent = { item ->
                    PostReplyItem(
                        requestHolder = requestHolder,
                        postReply = item
                    )
                })

            spacer()
        })
    }

}