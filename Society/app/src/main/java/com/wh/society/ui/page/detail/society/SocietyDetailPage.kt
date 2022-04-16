package com.wh.society.ui.page.detail.society

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.wh.society.R
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.*
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.itemOnCondition
import com.wh.society.typeExt.smallListTitle
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold
import com.wh.society.ui.componment.SocietyDetailTopInfoPart


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SocietyDetailPage(requestHolder: RequestHolder) {

    var thisSocietyJointList by remember {
        mutableStateOf(ReturnListData.blank<SocietyMember>())
    }
    var memberRequestList by remember {
        mutableStateOf(ReturnListData.blank<SocietyMemberRequest>())
    }
    var activityList by remember {
        mutableStateOf(ReturnListData.blank<SocietyActivity>())
    }
    var societyPictureList by remember {
        mutableStateOf(ReturnListData.blank<SocietyPicture>())
    }
    var societyNoticeList by remember {
        mutableStateOf(emptyList<SocietyNotice>())
    }

    val thisSociety = requestHolder.trans.society
    val thisUser = requestHolder.apiViewModel.userInfo

    val myJoint = thisSocietyJointList.data.find { it.userId == thisUser.id }

    val isJoin: Boolean = myJoint != null
    requestHolder.trans.isJoint = isJoin


    LaunchedEffect(myJoint) {
        requestHolder.apiViewModel.societyJoint(thisSociety.id) { s ->
            thisSocietyJointList = s
        }
        requestHolder.apiViewModel.societyActivityList(thisSociety.id) { s ->
            activityList = s
        }
        requestHolder.apiViewModel.societyNoticeList(thisSociety.id) { it ->
            societyNoticeList = it
        }

        myJoint?.let {
            if (it.permissionLevel == 111) {
                requestHolder.apiViewModel.societyMemberRequestList(requestHolder.trans.society.id) { it ->
                    memberRequestList = it
                }
                requestHolder.apiViewModel.societyPictureList(requestHolder.trans.society.id) { it ->
                    societyPictureList = it
                }
            }
        }
    }

    val isAdmin = isJoin && myJoint!!.permissionLevel == 111
    requestHolder.trans.isAdmin = isAdmin

    GlobalScaffold(
        page = GlobalNavPage.DetailSociety,
        requestHolder = requestHolder,
        actions = if (isAdmin) {
            {
                IconButton(onClick = {
                    requestHolder.globalNav.goto(GlobalNavPage.SocietyInfoEditorPage)
                }) {
                    Icon(Icons.Default.Edit, "")
                }
            }
        } else {
            {}
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item { SocietyDetailTopInfoPart(requestHolder, thisSocietyJointList) }

            // member row list
            item {
                LazyRow(
                    content = {
                        items(
                            items = thisSocietyJointList.data.sortedByDescending { it.permissionLevel },
                            key = { item: SocietyMember -> item.id },
                            itemContent = { item ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .width(50.dp)
                                        .clickable {
                                            requestHolder.globalNav.goto(
                                                page = GlobalNavPage.SocietyMemberDetailPage,
                                                a = item
                                            )
                                        }
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (item.permissionLevel == 111) Color.Gray else Color.Unspecified),
                                ) {
                                    Image(
                                        painter = rememberImagePainter(
                                            data = item.realIconUrl,
                                            builder = {
                                                placeholder(R.drawable.ic_baseline_person_24)
                                            },
                                            imageLoader = requestHolder.coilImageLoader
                                        ),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(45.dp)
                                            .shadow(5.dp, CircleShape)
                                            .background(color = Color.White)
                                            .clip(shape = CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = item.username,
                                        fontSize = 10.sp,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                }
                            }
                        )

                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .width(50.dp)
                                    .clickable {
                                        requestHolder.globalNav.goto(
                                            GlobalNavPage.SocietyMemberListPage,
                                            thisSocietyJointList
                                        )
                                    },
                            ) {
                                Image(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(45.dp)
                                        .shadow(5.dp, CircleShape)
                                        .background(color = Color.White)
                                        .clip(shape = CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = "Members",
                                    fontSize = 10.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        }
                    },
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                        .border(
                            width = 3.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(4.dp)
                )
            }

            smallListTitle(
                title = "社团公告",
                n = societyNoticeList.size,
                onClick = {
                    requestHolder.globalNav.goto( GlobalNavPage.SocietyNoticeListPage)
                }
            )

            // member request list for admin
            smallListTitle(
                title = "成员申请",
                n = memberRequestList.data.size,
                show = isAdmin,
                onClick = {
                    requestHolder.globalNav.goto(
                        page = GlobalNavPage.SocietyMemberRequestListPage,
                        a = memberRequestList
                    )
                }
            )

            smallListTitle(
                title = "社团活动",
                n = activityList.data.size,
                onClick = {
                    requestHolder.globalNav.goto(GlobalNavPage.SocietyActivityListPage)
                }
            )

            // society pic manage for admin
            smallListTitle(
                title = "社团图片",
                n = societyPictureList.data.size,
                show = isAdmin,
                onClick = {
                    requestHolder.globalNav.goto(
                        page = GlobalNavPage.SocietyPictureListPage,
                        a = societyPictureList
                    )
                }
            )

            // bottom buttons

            item {
                Button(
                    onClick = {
                        requestHolder.globalNav.goto(
                            GlobalNavPage.DetailBBS,
                            BBS.fromSociety(requestHolder.trans.society)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Goto BBS")
                }
            }

            itemOnCondition(isJoin) {
                Button(
                    onClick = {
                        requestHolder.globalNav.goto(
                            GlobalNavPage.SocietyChatInnerPage,
                            requestHolder.trans.society
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .height(50.dp),
                ) {
                    Text(text = "Goto Inner Chat")
                }
            }

            itemOnCondition(isJoin) {
                Button(
                    onClick = {
                        requestHolder.apiViewModel.societyMemberRequestCreate(
                            requestHolder.apiViewModel.userInfo.id,
                            requestHolder.trans.society.id,
                            request = "${
                                requestHolder.apiViewModel.userInfo.username
                            }'s leave request for ${requestHolder.trans.society.name}",
                            isJoin = false
                        ) {}
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(text = "Send Leave Request")
                }
            }

            itemOnCondition(!isJoin) {
                Button(
                    onClick = {
                        requestHolder.apiViewModel.societyMemberRequestCreate(
                            requestHolder.apiViewModel.userInfo.id,
                            requestHolder.trans.society.id,
                            request = "${
                                requestHolder.apiViewModel.userInfo.username
                            }'s join request for ${requestHolder.trans.society.name}",
                            isJoin = true
                        ) {}
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Send Join Request")
                }
            }

            spacer()

        }
    }

}