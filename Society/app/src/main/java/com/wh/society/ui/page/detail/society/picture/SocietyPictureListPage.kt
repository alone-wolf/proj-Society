package com.wh.society.ui.page.detail.society.picture

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.wh.society.api.data.society.SocietyPicture
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.everyNForRow
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold
import kotlinx.coroutines.flow.collect
import kotlin.math.roundToInt

@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterialApi
@Composable
fun SocietyPictureListPage(requestHolder: RequestHolder) {

    var pics by remember {
        mutableStateOf(requestHolder.trans.societyPictureList)
    }

    val confirmDeletePic: (SocietyPicture) -> Unit = { it ->
        requestHolder.alert.alert("提示", "要删除这张图片吗？", onOk = {
            if (requestHolder.trans.society.iconUrl == it.newFilename) {
                requestHolder.alert.alert(
                    "提示",
                    "这张图片正在使用中，不可删除！",
                    onOk = {})
            } else {
                requestHolder.apiViewModel.societyPictureDelete(
                    societyId = requestHolder.trans.society.id,
                    picToken = it.newFilename
                ) {
                    requestHolder.apiViewModel.societyPictureList(
                        societyId = requestHolder.trans.society.id
                    ) {
                        pics = it
                    }
                }
            }
        }, onCancel = {})
    }

    GlobalScaffold(
        page = GlobalNavPage.SocietyPictureListPage,
        requestHolder = requestHolder,
        remoteOperate = {
            requestHolder.apiViewModel.societyPictureList(requestHolder.trans.society.id) {
                pics = it
            }
        },
        fab = {
            FloatingActionButton(onClick = {
                requestHolder.imagePicker.forSociety()
            }) {
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
                count = pics.data.size,
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
                            data = pics.data[page].realIconUrl,
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
                        onClick = { confirmDeletePic(pics.data[page]) },
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
            LazyColumn(
                content = {
                    everyNForRow(
                        items = pics.data,
                        n = 4,
                        itemContent = { picture: SocietyPicture, index: Int, m: Modifier ->
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
                                    .height(100.dp)
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
                                alignment = Alignment.Center,
                            )

                        }
                    )
                    empty(requestHolder.trans.societyPictureList)
                    spacer()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }


    }
}