package com.wh.society.ui.page.main

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import com.wh.common.typeExt.firstN
import com.wh.society.api.data.*
import com.wh.society.api.data.society.Society
import com.wh.society.api.data.society.SocietyMember
import com.wh.society.api.data.society.SocietyMemberRequest
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.society.bbs.PostReply
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.smallListTitle
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.*

private var userPostList by mutableStateOf(ReturnListData.blank<Post>())
private var userPostReplyList by mutableStateOf(ReturnListData.blank<PostReply>())
private var userJointList by mutableStateOf(emptyList<SocietyMember>())
private var userJoinRequestList by mutableStateOf(emptyList<SocietyMemberRequest>())

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MinePage(requestHolder: RequestHolder) {

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.userPostList(
            requestHolder.apiViewModel.userInfo.id
        ) { it ->
            requestHolder.trans.postList = it
            userPostList = it
        }
        requestHolder.apiViewModel.userPostReplyList(
            requestHolder.apiViewModel.userInfo.id
        ) { it -> userPostReplyList = it }
        requestHolder.apiViewModel.userJoint(
            requestHolder.apiViewModel.userInfo.id
        ) { it -> userJointList = it.data }
        requestHolder.apiViewModel.userJoinRequestList(
            requestHolder.apiViewModel.userInfo.id
        ) { it -> userJoinRequestList = it.data }
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Box {
                Image(
                    painter = rememberImagePainter(
                        data = requestHolder.apiViewModel.userInfo.realIconUrl,
                        builder = {
                            crossfade(true)
                            transformations(
                                BlurTransformation(requestHolder.activity, 5F, 16F)
                            )
                        }),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.FillWidth
                )

                Spacer(
                    modifier = Modifier
                        .width(80.dp)
                        .height(120.dp)
                        .align(Alignment.Center)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    UserBigIcon(
                        requestHolder = requestHolder,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                    Text(
                        text = requestHolder.apiViewModel.userInfo.username,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }

        }
        minePageTopInfoCard(requestHolder = requestHolder)

        // 加入的社团 标题
        smallListTitle(
            title = "加入的社团",
            n = userJointList.size,
            onClick = {
                requestHolder.globalNav.goto(GlobalNavPage.MainMineSocietyListPage, userJointList)
            }
        )
        // 加入的社团列表

        items(
            items = userJointList.asReversed().firstN(5),
            key = { item: SocietyMember -> item.hashCode() },
            itemContent = { it: SocietyMember ->
                requestHolder.societyList.find { item: Society -> it.societyId == item.id }?.let {
                    SocietyItem(requestHolder, it)
                }
            }
        )

        smallListTitle(
            title = "我的社团申请",
            n = userJoinRequestList.size,
            onClick = { requestHolder.globalNav.goto(GlobalNavPage.MainMineSocietyRequestListPage) }
        )
        items(
            items = userJoinRequestList.asReversed().firstN(5),
            key = { item: SocietyMemberRequest -> item.hashCode() },
            itemContent = {

            }
        )

        // 发布的帖子 标题
        smallListTitle(
            title = "发布的帖子",
            n = userPostList.data.size,
            onClick = {
                requestHolder.globalNav.goto(GlobalNavPage.MainMinePostListPage, userPostList)
            }
        )

        items(
            items = userPostList.data.asReversed().firstN(5),
            key = { item: Post -> item.hashCode() },
            itemContent = { item ->
                MineUserPostItem(requestHolder, item)
            }
        )

        // 发表的回复 标题
        smallListTitle(
            title = "发表的回复",
            n = userPostReplyList.data.size,
            onClick = {
                requestHolder.globalNav.goto(
                    page = GlobalNavPage.MainMinePostReplyListPage,
                    a = userPostReplyList
                )
            }
        )
        items(
            items = userPostReplyList.data.asReversed().firstN(5),
            key = { item: PostReply -> item.hashCode() },
            itemContent = { item ->
                MineUserPostReplyItem(requestHolder, item)
            }
        )

        smallListTitle(
            title = "上传的照片",
            n = requestHolder.apiViewModel.picDataList.data.size,
            onClick = {
                requestHolder.globalNav.goto(GlobalNavPage.MainMinePicListPage)
            }
        )

        item {
            Button(
                onClick = {
                    requestHolder.logoutLogic()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(text = "Logout")
            }
        }

        spacer()
    }
}

@ExperimentalAnimationApi
private fun LazyListScope.minePageTopInfoCard(requestHolder: RequestHolder) {
    item {
        MinePageTopInfoCard(requestHolder = requestHolder)
    }
}