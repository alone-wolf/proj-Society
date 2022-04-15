package com.wh.society.ui.page.main.mine

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.wh.society.api.data.user.UserPicture
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.everyNForRow
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterialApi
@Composable
fun MinePicList(requestHolder: RequestHolder) {

    val pics = requestHolder.apiViewModel.picDataList.data

    val confirmDeletePic: (UserPicture) -> Unit = {
        requestHolder.alert.tip("要删除这张图片吗？", onOk = {
            if (requestHolder.apiViewModel.userInfo.iconUrl == it.newFilename) {
                requestHolder.alert.tip("这张图片正在使用中，不可删除！")
            } else {
                requestHolder.apiViewModel.picDelete(it.newFilename) {
                    requestHolder.apiViewModel.picList()
                }
            }
        })
    }

    GlobalScaffold(
        page = GlobalNavPage.MainMinePicListPage,
        requestHolder = requestHolder,
        fab = {
            FloatingActionButton(
                onClick = requestHolder.imagePicker.forUser
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) {

        var showPager by remember {
            mutableStateOf(false)
        }
        var currentPageIndex by remember {
            mutableStateOf(0)
        }


        if (showPager) {
            val state = rememberPagerState(currentPageIndex)

            var scale by remember { mutableStateOf(1f) }
            var rotation by remember { mutableStateOf(0f) }
            var offset by remember { mutableStateOf(Offset.Zero) }

            LaunchedEffect(Unit) {
                state.interactionSource.interactions.collect {
                    scale = 1f
                    rotation = 0f
                    offset = Offset.Zero
                }
            }

            HorizontalPager(
                count = pics.size,
                state = state,
                verticalAlignment = Alignment.CenterVertically
            ) { page ->
                Box(modifier = Modifier.fillMaxSize()) {

                    val transformableState =
                        rememberTransformableState { zoomChange, offsetChange, rotationChange ->
                            scale *= zoomChange
                            rotation += rotationChange
                            offset += offsetChange
                        }
                    Image(
                        painter = rememberImagePainter(
                            data = pics[page].realIconUrl,
                            imageLoader = requestHolder.coilImageLoader,
                            builder = { crossfade(false) }
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                rotationZ = rotation,
                                translationX = offset.x,
                                translationY = offset.y
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        currentPageIndex = 0
                                        showPager = false
                                    }
                                )
                            }
                            .transformable(state = transformableState),
                        contentScale = ContentScale.Inside
                    )
                    OutlinedButton(
                        onClick = { confirmDeletePic(pics[page]) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                        Text(text = "删除")
                    }
                }
            }


        } else {
            LazyColumn(content = {

                everyNForRow(
                    items = pics,
                    n = 3,
                    itemContent = { picture, index, m ->
                        Image(
                            painter = rememberImagePainter(
                                data = picture.realIconUrl,
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
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            confirmDeletePic(picture)
                                        },
                                        onTap = {
                                            currentPageIndex = index
                                            showPager = true
                                        }
                                    )
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
}