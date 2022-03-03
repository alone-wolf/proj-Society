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
import androidx.compose.material.icons.filled.ArrowBack
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
import com.wh.common.typeExt.firstN
import com.wh.society.R
import com.wh.society.api.data.*
import com.wh.society.api.data.society.*
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.itemsOnCondition
import com.wh.society.typeExt.smallListTitle
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.SocietyDetailTopInfoPart

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SocietyDetailPage(requestHolder: RequestHolder) {
    var thisSocietyJointList by remember { mutableStateOf(ReturnListData.blank<SocietyMember>()) }
    var memberRequestList by remember { mutableStateOf(ReturnListData.blank<SocietyMemberRequest>()) }
    var activityList by remember { mutableStateOf(ReturnListData.blank<SocietyActivity>()) }
    var activityRequestList by remember { mutableStateOf(ReturnListData.blank<SocietyActivityRequest>()) }
    var societyPictureList by remember { mutableStateOf(ReturnListData.blank<SocietyPicture>()) }

    val myJoint = thisSocietyJointList.data.find {
        it.userId == requestHolder.apiViewModel.userInfo.notNullOrBlank(
            UserInfo()
        ).id
    }
    val isJoint: Boolean = myJoint != null


    LaunchedEffect(myJoint) {
        requestHolder.apiViewModel.societyJoint(requestHolder.trans.society.id) { s ->
            thisSocietyJointList = s
        }
        requestHolder.apiViewModel.societyActivityList(requestHolder.trans.society.id) { s ->
            activityList = s
        }

        myJoint?.let {
            if (it.permissionLevel == 111) {
                requestHolder.apiViewModel.societyMemberRequestList(requestHolder.trans.society.id) { it ->
                    memberRequestList = it
                }
                requestHolder.apiViewModel.societyActivityRequestList(requestHolder.trans.society.id) {
                    activityRequestList = it
                }
                requestHolder.apiViewModel.societyPictureList(requestHolder.trans.society.id) {
                    societyPictureList = it
                }
            }
        }
    }

    val isAdmin = isJoint && myJoint!!.permissionLevel == 111


    BottomSheetScaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = GlobalNavPage.DetailSociety.label) },
                navigationIcon = {
                    IconButton(onClick = { requestHolder.globalNav.goBack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                actions = if (isAdmin) {
                    {
                        IconButton(onClick = {
                            requestHolder.globalNav.goto(GlobalNavPage.SocietyInfoEditorPage)
                        }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                        }
                    }
                } else {
                    {}
                }
            )
        },
        content = {
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
                                                requestHolder.globalNav.goto(GlobalNavPage.SocietyMemberDetailPage,item)
                                            }
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(if (item.permissionLevel == 111) Color.Gray else Color.Unspecified),
                                    ) {
                                        Image(
                                            painter = rememberImagePainter(
                                                data = item.realIconUrl,
                                                builder = {
                                                    this.placeholder(R.drawable.ic_baseline_person_24)
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

                // member request list for admin
                smallListTitle(
                    title = "成员申请",
                    n = memberRequestList.data.size,
                    show = isAdmin,
                    onClick = {}
                )

                itemsOnCondition(
                    show = isAdmin,
                    items = memberRequestList.data.asReversed().firstN(5),
                    key = { item: SocietyMemberRequest -> item.hashCode() },
                    itemContent = { it ->
                        Card(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "来自${it.username}的成员申请",
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                            )
                        }
                    }
                )

                // activity request for admin
                smallListTitle(
                    title = "社团活动申请",
                    n = activityRequestList.data.size,
                    show = isAdmin,
                    onClick = {
                        requestHolder.globalNav.goto(GlobalNavPage.SocietyActivityRequestListPage)
                    }
                )
                itemsOnCondition(
                    show = isAdmin,
                    items = activityRequestList.data.asReversed().firstN(5),
                    key = { item: SocietyActivityRequest -> item.hashCode() },
                    itemContent = {
                        Card(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 1.dp)
                        ) {
                            Text(
                                text = "来自${it.username}的活动申请",
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                            )
                        }
                    }
                )

                smallListTitle(
                    title = "社团活动",
                    n = activityList.data.size,
                    onClick = {
                        requestHolder.globalNav.goto(GlobalNavPage.SocietyActivityListPage)
                    }
                )
                items(
                    items = activityList.data.asReversed().firstN(5),
                    key = { item: SocietyActivity -> item.hashCode() },
                    itemContent = {
                        Card(
                            onClick = {
                                requestHolder.globalNav.goto(GlobalNavPage.SocietyActivityDetailPage,it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 1.dp)
                        ) {
                            Text(
                                text = it.title,
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                            )
                        }
                    }
                )

                smallListTitle(
                    title = "社团图片",
                    n = societyPictureList.data.size,
                    onClick = {
                        requestHolder.globalNav.goto(
                            page = GlobalNavPage.SocietyPictureListPage,
                            a = societyPictureList
                        )
                    },
                )

                spacer(height = 100.dp)

            }
        },

        // bottom sheet
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {

                Button(
                    onClick = {
                        requestHolder.globalNav.goto<BBS>(
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

                if (isJoint) {

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

                    Button(
                        onClick = {
                            requestHolder.apiViewModel.societyMemberRequestCreate(
                                requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).id,
                                requestHolder.trans.society.id,
                                request = "${
                                    requestHolder.apiViewModel.userInfo.notNullOrBlank(
                                        UserInfo()
                                    ).username
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
                } else {
                    Button(
                        onClick = {
                            requestHolder.apiViewModel.societyMemberRequestCreate(
                                requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).id,
                                requestHolder.trans.society.id,
                                request = "${
                                    requestHolder.apiViewModel.userInfo.notNullOrBlank(
                                        UserInfo()
                                    ).username
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
            }
        },
        sheetElevation = 15.dp,
        sheetPeekHeight = 100.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 8.dp)
    )
}