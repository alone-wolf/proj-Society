package com.wh.society.ui.page.detail.society.bbs

import androidx.compose.animation.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import com.wh.society.api.data.society.bbs.BBSInfo
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.ReturnObjectData
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.BBSDetailTopInfoCard
import com.wh.society.ui.componment.GlobalScaffold
import com.wh.society.ui.componment.PostItem


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun BBSDetailPage(requestHolder: RequestHolder) {
    // is also post list page
    var thisBBSInfo by remember {
        mutableStateOf(ReturnObjectData.blank<BBSInfo>())
    }

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.societyBBSInfo(requestHolder.trans.bbs.id) { s ->
            thisBBSInfo = s
        }
    }

    val thisInfo = thisBBSInfo.notNullOrBlank(BBSInfo())

    GlobalScaffold(
        page = GlobalNavPage.DetailBBS,
        requestHolder = requestHolder,
        actions = {
            IconButton(onClick = {
                requestHolder.apiViewModel.societyBBSInfo(requestHolder.trans.bbs.id) { s ->
                    thisBBSInfo = s
                }
            }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "")
            }
        },
        fab = {
            AnimatedVisibility(
                visible = thisInfo.bbsAllowGuestPost,
                enter = fadeIn() + slideInHorizontally(initialOffsetX = { it / 2 }),
                exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it / 2 })
            ) {
                FloatingActionButton(onClick = {
                    requestHolder.globalNav.goto(GlobalNavPage.DetailPostEditor)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        }
    ) {
        LazyColumn(content = {
            item { BBSDetailTopInfoCard(requestHolder = requestHolder, thisInfo = thisInfo) }
            items(
                items = thisInfo.posts,
                key = { item: Post -> item.id },
                itemContent = { item: Post ->
                    PostItem(requestHolder = requestHolder, post = item)
                })
            spacer()
        })
    }
}