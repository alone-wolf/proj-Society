package com.wh.society.ui.page.main.mine

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.everyNForRow
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun MinePicList(requestHolder: RequestHolder) {
    GlobalScaffold(
        page = GlobalNavPage.MainMinePicListPage,
        requestHolder = requestHolder,
        fab = {
            FloatingActionButton(
                onClick = {
                    requestHolder.imagePicker.forUser()
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) {

        LazyColumn(content = {

            everyNForRow(
                items = requestHolder.apiViewModel.picDataList.data,
                n = 3,
                itemContent = { it, m ->
                    Image(
                        painter = rememberImagePainter(
                            data = it.realIconUrl,
                            imageLoader = requestHolder.coilImageLoader,
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = "",
                        modifier = m
                            .height(120.dp)
                            .padding(2.dp)
                            .shadow(elevation = 3.dp)
                            .clickable {
                                requestHolder.alert.alert("提示", "要删除这张图片吗？", onOk = {
                                    if (requestHolder.apiViewModel.userInfo.data!!.iconUrl == it.newFilename) {
                                        requestHolder.alert.alert(
                                            "提示",
                                            "这张图片正在使用中，不可删除！",
                                            onOk = {})
                                    } else {
                                        requestHolder.apiViewModel.picDelete(it.newFilename) {
                                            requestHolder.apiViewModel.picList()
                                        }
                                    }
                                }, onCancel = {})
                            }
                            .border(width = 2.dp, color = Color.White),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }
            )
            empty(requestHolder.apiViewModel.picDataList)
            spacer()
        }, modifier = Modifier.fillMaxSize())
    }
}