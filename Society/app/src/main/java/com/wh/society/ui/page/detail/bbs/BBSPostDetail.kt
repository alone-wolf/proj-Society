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
import com.wh.society.api.data.PostReply
import com.wh.society.api.data.ReturnListData
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
    LaunchedEffect(Unit) {
        requestHolder.transPost?.let {
            requestHolder.apiViewModel.societyBBSPostReplyList(it.id) { a ->
                postReplyList = a
            }
        }
    }

    GlobalScaffold(
        page = GlobalNavPage.DetailPost,
        requestHolder = requestHolder,
        fab = {
            FloatingActionButton(onClick = {
                requestHolder.alertRequest.alertFor1TextFiled("reply post") {
                    requestHolder.apiViewModel.societyBBSPostReplyCreate(
                        societyId = requestHolder.transSociety.id,
                        postId = requestHolder.transPost!!.id,
                        userId = requestHolder.userInfo.id,
                        reply = it,
                        deviceName = requestHolder.deviceName
                    ) {
                        requestHolder.apiViewModel.societyBBSPostReplyList(
                            postId = requestHolder.transPost!!.id
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
            requestHolder.transPost?.let {
                if (it.userId == requestHolder.userInfo.id) {
                    IconButton(onClick = {
                        requestHolder.apiViewModel.societyBBSPostDelete(
                            userId = requestHolder.userInfo.id,
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
                    post = requestHolder.transPost!!,
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