package com.wh.society.ui.page.detail.society

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.Society
import com.wh.society.api.data.society.SocietyPicture
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.borderButton
import com.wh.society.typeExt.textFiled
import com.wh.society.ui.componment.GlobalScaffold
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun SocietyInfoEditor(requestHolder: RequestHolder) {

    val tempSociety = requestHolder.trans.society.shadow()

    var societyPicList by remember {
        mutableStateOf(ReturnListData.blank<SocietyPicture>())
    }

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.societyPictureList(requestHolder.trans.society.id) {
            societyPicList = it
        }
    }


    GlobalScaffold(
        page = GlobalNavPage.SocietyInfoEditorPage,
        requestHolder = requestHolder
    ) {

        LazyColumn(
            content = {

                // image select
                item {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        elevation = 5.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    data = tempSociety.realIconUrl,
                                    imageLoader = requestHolder.coilImageLoader
                                ),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(60.dp)
                                    .border(width = 2.dp, color = Color.White, shape = CircleShape)
                                    .shadow(5.dp, shape = CircleShape)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center
                            )

                            LazyRow(
                                content = {
                                    items(
                                        items = societyPicList.data,
                                        key = { item: SocietyPicture -> item.id },
                                        itemContent = { it ->
                                            Box(modifier = Modifier.clickable {
                                                tempSociety.iconUrl = it.newFilename
                                            }) {
                                                Image(
                                                    painter = rememberImagePainter(
                                                        data = it.realIconUrl,
                                                        imageLoader = requestHolder.coilImageLoader
                                                    ),
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .size(60.dp),
                                                    contentScale = ContentScale.Crop,
                                                    alignment = Alignment.Center
                                                )
                                                if (tempSociety.iconUrl == it.newFilename) {
                                                    Icon(
                                                        imageVector = Icons.Default.Check,
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .align(Alignment.Center)
                                                            .size(60.dp)
                                                            .background(
                                                                Color(0.3F, 0.7F, 0.3F, 0.5f),
                                                                shape = CircleShape
                                                            )
                                                            .alpha(0.3F),
                                                        tint = Color.White
                                                    )
                                                }
                                            }
                                        })
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(28.dp))
                                    .background(Color.Gray)
                            )
                        }
                    }
                }

                textFiled(
                    value = tempSociety.name,
                    onUpdate = { tempSociety.name = it },
                    label = "社团名称"
                )

                textFiled(
                    value = tempSociety.describe,
                    onUpdate = { tempSociety.describe = it },
                    label = "社团简介"
                )

                textFiled(
                    value = tempSociety.college,
                    onUpdate = { tempSociety.college = it },
                    label = "所属学院"
                )

                textFiled(
                    value = tempSociety.bbsName,
                    onUpdate = { tempSociety.bbsName = it },
                    label = "论坛名称"
                )

                textFiled(
                    value = tempSociety.bbsDescribe,
                    onUpdate = { tempSociety.bbsDescribe = it },
                    label = "论坛简介"
                )

                borderButton(
                    text = "保存",
                    onClick = {
                        requestHolder.apiViewModel.societyUpdate(
                            society= tempSociety,
                            onError = {
                                requestHolder.coroutineScope.launch {
                                    requestHolder.toast.toast(it)
                                }
                            },
                            onReturn = {
                                requestHolder.apiViewModel.societyInfo(tempSociety.id,
                                    onError = {
                                        requestHolder.coroutineScope.launch {
                                            requestHolder.toast.toast(it)
                                        }
                                    },
                                    onReturn = {
                                        requestHolder.trans.society = it.notNullOrBlank(Society())
                                    }
                                )
                                requestHolder.apiViewModel.societyList(
                                    onError = {
                                        requestHolder.coroutineScope.launch {
                                            requestHolder.toast.toast(it)
                                        }
                                    }
                                )
                                requestHolder.globalNav.goBack()
                            }

                        )
                    }
                )

            },
            modifier = Modifier.fillMaxWidth()
        )

    }
}