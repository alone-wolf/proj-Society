package com.wh.society.ui.page.detail.society.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.wh.society.api.data.shadow.SocietyNoticeShadow
import com.wh.society.api.data.society.SocietyNotice
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold
import com.wh.society.ui.componment.RoundedTextFiled

@ExperimentalMaterialApi
@Composable
fun SocietyNoticeListPage(requestHolder: RequestHolder) {

    var societyNoticeList by remember {
        mutableStateOf(emptyList<SocietyNotice>())
    }

    val updateSocietyNoticeList = {
        requestHolder.apiViewModel.societyNoticeList(requestHolder.trans.society.id) {
            societyNoticeList = it.asReversed()
        }
    }

    LaunchedEffect(Unit) {
        updateSocietyNoticeList.invoke()
    }


    GlobalScaffold(
        page = GlobalNavPage.SocietyNoticeListPage,
        requestHolder = requestHolder,
        fab = if (requestHolder.trans.isAdmin) {
            {
                FloatingActionButton(onClick = {
                    val societyNotice = SocietyNoticeShadow()
                    societyNotice.postUserId = requestHolder.apiViewModel.userInfo.id
                    societyNotice.societyId = requestHolder.trans.society.id
                    requestHolder.alert.alert(
                        title = "发布社团公告",
                        content = {

                            var showDropdownMenu by remember {
                                mutableStateOf(false)
                            }

                            val plm = mapOf(
                                0 to "全部用户可见",
                                10 to "仅成员可见",
                                100 to "仅管理员可见"
                            )

                            Column{
                                RoundedTextFiled(
                                    value = societyNotice.title,
                                    onValueChange = { societyNotice.title = it },
                                    placeHolder = "标题"
                                )
                                RoundedTextFiled(
                                    value = societyNotice.notice,
                                    onValueChange = { societyNotice.notice = it },
                                    placeHolder = "公告内容"
                                )
                                Box {
                                    DropdownMenu(
                                        expanded = showDropdownMenu,
                                        onDismissRequest = { showDropdownMenu = false }) {
                                        plm.entries.forEach {
                                            DropdownMenuItem(onClick = {
                                                showDropdownMenu = false
                                                societyNotice.permissionLevel = it.key
                                            }) {
                                                Text(text = "${it.key} ${it.value}")
                                            }
                                        }
                                    }
                                    OutlinedButton(onClick = { showDropdownMenu = true }) {
                                        Text(text = "${societyNotice.permissionLevel} ${plm[societyNotice.permissionLevel]}")
                                    }
                                }
                            }
                        },
                        onOk = {
                            requestHolder.apiViewModel.societyNoticeCreate(
                                societyNotice = societyNotice,
                                onReturn = { updateSocietyNoticeList.invoke() },
                                onError = requestHolder.toast.toast
                            )
                        }
                    )
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        } else {
            {}
        }
    ) {

        LazyColumn(
            content = {

                items(
                    items = societyNoticeList,
                    key = { item: SocietyNotice -> item.id }
                ) { it ->
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (requestHolder.trans.isAdmin) {
                                requestHolder.alert.alert(
                                    title = "社团公告:",
                                    content = "${it.title}\n${it.notice}\n发布时间${it.createTSFmt()}",
                                    btns = mapOf(
                                        "Close" to {},
                                        "Delete" to {
                                            requestHolder.apiViewModel.societyNoticeDelete(
                                                noticeId = it.id,
                                                onReturn = updateSocietyNoticeList,
                                                onError = requestHolder.toast.toast
                                            )
                                        }
                                    )
                                )
                            } else {
                                requestHolder.alert.alert(
                                    "社团公告:",
                                    "${it.title}\n${it.notice}\n发布时间${it.createTSFmt()}",
                                    onOk = {}
                                )
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 14.dp)) {
                        Row {
                            Image(
                                painter = rememberImagePainter(
                                    data = requestHolder.trans.society.realIconUrl,
                                    imageLoader = requestHolder.coilImageLoader
                                ), "",
                                modifier = Modifier
                                    .size(45.dp)
                                    .shadow(5.dp, CircleShape)
                                    .background(color = Color.White)
                                    .clip(shape = CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.padding(start = 16.dp))
                            Column {
                                Text(text = it.title, fontSize = 18.sp)
                                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                    Text(
                                        text = "${it.postUsername} · ${it.createTSFmt()}",
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        Text(text = it.notice, fontSize = 16.sp)
                    }
                }
                empty(societyNoticeList)
                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}