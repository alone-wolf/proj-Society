package com.wh.society.ui.page.detail.society.notice

import android.text.TextUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.wh.society.api.data.shadow.SocietyNoticeShadow
import com.wh.society.api.data.shadow.SocietyShadow
import com.wh.society.api.data.society.SocietyNotice
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.imageNames
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
                            Column(
                                modifier = Modifier.height(125.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
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
                            }
                        },
                        onOk = {
                            requestHolder.apiViewModel.societyNoticeCreate(
                                societyNotice = societyNotice,
                                onReturn = { updateSocietyNoticeList.invoke() },
                                onError = requestHolder.toast.toast
                            )
                        },
                        onCancel = {
                            societyNotice.title = ""
                            societyNotice.notice = ""
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