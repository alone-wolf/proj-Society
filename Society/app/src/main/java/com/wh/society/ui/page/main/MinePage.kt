package com.wh.society.ui.page.main

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import coil.transform.Transformation
import com.wh.common.typeExt.firstN
import com.wh.society.api.data.*
import com.wh.society.componment.RequestHolder
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.*

private var userPostList by mutableStateOf(ReturnListData.blank<Post>())
private var userPostReplyList by mutableStateOf(ReturnListData.blank<PostReply>())
private var userJointList by mutableStateOf(ReturnListData.blank<SocietyJoint>())
private var userJoinRequestList by mutableStateOf(ReturnListData.blank<MemberRequest>())

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MinePage(requestHolder: RequestHolder) {
    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.userPostList(
            requestHolder.apiViewModel.userInfo.notNullOrBlank(
                UserInfo()
            ).id
        ) { it ->
            requestHolder.transPostList = it
            userPostList = it
        }
        requestHolder.apiViewModel.userPostReplyList(
            requestHolder.apiViewModel.userInfo.notNullOrBlank(
                UserInfo()
            ).id
        ) { it ->
            userPostReplyList = it
        }
        requestHolder.apiViewModel.userJoint(
            requestHolder.apiViewModel.userInfo.notNullOrBlank(
                UserInfo()
            ).id
        ) { it ->
            userJointList = it
        }
        requestHolder.apiViewModel.userJoinRequestList(
            requestHolder.apiViewModel.userInfo.notNullOrBlank(
                UserInfo()
            ).id
        ) { it ->
            userJoinRequestList = it
        }
    }

    LazyColumn(modifier = Modifier.fillMaxWidth(), content = {
        item {
            Box {
                Image(
                    painter = rememberImagePainter(
                        data = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).realIconUrl,
                        builder = {
                            crossfade(true)
                            transformations(BlurTransformation(requestHolder.activity))
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
//                        .background(color = Color.h)
                        .align(Alignment.Center)
//                        .clip(shape = Round)
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
                        text = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).username,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }

        }
        minePageTopInfoCard(requestHolder = requestHolder)

        // 加入的社团 标题
        minePageTitleItem(
            title = "加入的社团",
            n = userJointList.data.size,
            onClick = {
                requestHolder.globalNav.gotoMainMineSocietyList(userJointList)
            }
        )
        // 加入的社团列表

        items(
            items = userJointList.data.asReversed().firstN(5),
            key = { item: SocietyJoint -> item.hashCode() },
            itemContent = { it: SocietyJoint ->
                requestHolder.societyList.find { item: Society -> it.societyId == item.id }?.let {
                    SocietyItem(
                        requestHolder = requestHolder,
                        society = it,
                    )
                }
            }
        )

        minePageTitleItem(
            title = "我的社团申请",
            n = userJoinRequestList.data.size,
            onClick = {
                requestHolder.globalNav.gotoMainMineSocietyRequestList(userJoinRequestList)
            }
        )
        items(
            items = userJoinRequestList.data.asReversed().firstN(5),
            key = { item: MemberRequest -> item.hashCode() },
            itemContent = {

            }
        )

        // 发布的帖子 标题
        minePageTitleItem(
            title = "发布的帖子",
            n = userPostList.data.size,
            onClick = {
                requestHolder.globalNav.gotoMainMinePostList(userPostList)
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
        minePageTitleItem(
            title = "发表的回复",
            n = userPostReplyList.data.size,
            onClick = {
                requestHolder.globalNav.gotoMainMinePostReplyList(userPostReplyList)
            }
        )
        items(
            items = userPostReplyList.data.asReversed().firstN(5),
            key = { item: PostReply -> item.hashCode() },
            itemContent = { item ->
                MineUserPostReplyItem(requestHolder, item)
            }
        )

        minePageTitleItem(
            title = "上传的照片",
            n = requestHolder.apiViewModel.picDataList.data.size,
            onClick = {
                requestHolder.globalNav.gotoMainMinePicList()
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
    })
}

@ExperimentalMaterialApi
private fun LazyListScope.minePageTitleItem(title: String, n: Int, onClick: () -> Unit) {
    item {
        MineTitleItem(title, n, onClick)
    }
}

@ExperimentalAnimationApi
private fun LazyListScope.minePageTopInfoCard(requestHolder: RequestHolder) {
    item {
        MinePageTopInfoCard(requestHolder = requestHolder)
    }
}