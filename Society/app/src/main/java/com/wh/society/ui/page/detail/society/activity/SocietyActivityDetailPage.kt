package com.wh.society.ui.page.detail.society.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.wh.society.R
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.SocietyActivityMember
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyActivityDetailPage(requestHolder: RequestHolder) {

    var memberList by remember {
        mutableStateOf(ReturnListData.blank<SocietyActivityMember>())
    }

    val thisActivity = requestHolder.trans.societyActivity
    var thisActivityThisUserJoin by remember {
        mutableStateOf(thisActivity.thisUserJoin)
    }

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.societyActivityMember(thisActivity.id) {
            memberList = it
        }
    }

    val updateThisActivity: () -> Unit = {
        thisActivityThisUserJoin = !thisActivityThisUserJoin
        thisActivity.thisUserJoin = thisActivityThisUserJoin

        requestHolder.apiViewModel.societyActivityMember(thisActivity.id) {
            memberList = it
        }
    }


    GlobalScaffold(
        page = GlobalNavPage.SocietyActivityDetailPage,
        requestHolder = requestHolder
    ) {

        LazyColumn(
            content = {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(text = thisActivity.title)
                            Text(text = thisActivity.activity)
                            Text(text = thisActivity.createTSFmt())
                            Text(text = thisActivity.societyName)
                            Text(text = "${thisActivity.permLevel}可用")
                            Row {
//                                Icon(painter = painterResource(id = R.drawable.ic_baseline_check_box_24), contentDescription = "")
                                if (thisActivityThisUserJoin) {
                                    TextButton(onClick = {
                                        requestHolder.apiViewModel.societyActivityLeave(
                                            activityId = thisActivity.id,
                                            userId = requestHolder.apiViewModel.userInfo.id,
                                            onReturn = updateThisActivity,
                                            onError = requestHolder.toast.toast
                                        )
                                    }) {
                                        Text(text = "Leave")
                                    }
                                } else {
                                    TextButton(onClick = {
                                        requestHolder.apiViewModel.societyActivityJoin(
                                            activityId = thisActivity.id,
                                            userId = requestHolder.apiViewModel.userInfo.id,
                                            onReturn = updateThisActivity,
                                            onError = requestHolder.toast.toast
                                        )
                                    }) {
                                        Text(text = "Join")
                                    }
                                }
                            }
                        }
                    }
                }

                items(
                    items = memberList.data,
                    key = { item: SocietyActivityMember -> item.hashCode() },
                    itemContent = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .padding(vertical = 10.dp, horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    data = it.realIconUrl,
                                    imageLoader = requestHolder.coilImageLoader
                                ), "",
                                modifier = Modifier
                                    .size(60.dp)
                                    .shadow(5.dp, CircleShape)
                                    .background(color = Color.White)
                                    .clip(shape = CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = it.username,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 16.dp),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    }
                )

                spacer()
            },
            modifier = Modifier.fillMaxWidth()
        )

    }
}