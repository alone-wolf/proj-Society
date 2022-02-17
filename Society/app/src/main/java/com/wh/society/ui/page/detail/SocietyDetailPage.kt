package com.wh.society.ui.page.detail

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
import com.wh.society.api.data.BBS
import com.wh.society.api.data.MemberRequest
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.SocietyJoint
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.conditionItem
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.SocietyDetailTopInfoPart

@ExperimentalMaterialApi
@Composable
fun SocietyDetailPage(requestHolder: RequestHolder) {
    var thisSocietyJointList by remember { mutableStateOf(ReturnListData.blank<SocietyJoint>()) }
    var memberRequestList by remember { mutableStateOf(ReturnListData.blank<MemberRequest>()) }

    val myJoint = thisSocietyJointList.data.find { it.userId == requestHolder.userInfo.id }
    val isJoint: Boolean = myJoint != null


    LaunchedEffect(myJoint) {
        requestHolder.apiViewModel.societyJoint(requestHolder.transSociety.id) { s ->
            thisSocietyJointList = s
        }

        myJoint?.let {
            if (it.permissionLevel == 111) {
                requestHolder.apiViewModel.societyMemberRequestList(requestHolder.transSociety.id) { it ->
                    memberRequestList = it
                }
            }
        }

    }


    BottomSheetScaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = GlobalNavPage.DetailSociety.label) },
                navigationIcon = {
                    IconButton(onClick = { requestHolder.globalNav.goBack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    SocietyDetailTopInfoPart(requestHolder, thisSocietyJointList)
                }

                // member row list
                item {
                    LazyRow(
                        content = {
                            items(
                                items = thisSocietyJointList.data.sortedByDescending { it.permissionLevel },
                                key = { item: SocietyJoint -> item.id },
                                itemContent = { item ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .width(50.dp)
                                            .clickable {
                                                requestHolder.globalNav.gotoSocietyMemberDetail(item)
                                            },
                                    ) {
                                        Image(
                                            painter = rememberImagePainter(
                                                data = item.userIconUrl,
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
                                            maxLines = 1,
                                            color = if (item.permissionLevel == 111) Color.Red else Color.Unspecified
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
                                            requestHolder.globalNav.gotoSocietyMemberList(
                                                thisSocietyJointList.data
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
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 3.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(4.dp)
                    )
                }

                // member request for admin

                conditionItem(isJoint && myJoint!!.permissionLevel == 111) {
                    Text(text = memberRequestList.data.size.toString())
                }

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
                        requestHolder.globalNav.gotoDetailBBS(
                            BBS.fromSociety(requestHolder.transSociety),
                            addBack = true
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
                            requestHolder.globalNav.gotoSocietyChatInner(requestHolder.transSociety)
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
                                requestHolder.userInfo.id,
                                requestHolder.transSociety.id,
                                request = "${requestHolder.userInfo.username}'s leave request for ${requestHolder.transSociety.name}",
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
                                requestHolder.userInfo.id,
                                requestHolder.transSociety.id,
                                request = "${requestHolder.userInfo.username}'s join request for ${requestHolder.transSociety.name}",
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