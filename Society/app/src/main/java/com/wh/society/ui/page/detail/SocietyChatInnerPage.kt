package com.wh.society.ui.page.detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.wh.society.api.data.SocietyChatMessage
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold
import kotlinx.coroutines.launch

private var chatMessageList by mutableStateOf(ReturnListData.blank<SocietyChatMessage>())

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SocietyChatInnerPage(requestHolder: RequestHolder) {

    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.societyChatInnerList(requestHolder.transSociety.id) { it ->
            chatMessageList = it
        }
    }
    val newestIndex = chatMessageList.newestIndex
    LaunchedEffect(chatMessageList) {
        lazyListState.animateScrollToItem(newestIndex)
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    GlobalScaffold(
        page = GlobalNavPage.SocietyChatInnerPage,
        remoteOperate = {
            requestHolder.apiViewModel.societyChatInnerList(requestHolder.transSociety.id) { it ->
                chatMessageList = it
            }
        },
        requestHolder = requestHolder
    ) {
        Box {
            LazyColumn(
                state = lazyListState,
                content = {
                    items(
                        items = chatMessageList.data,
                        key = { item: SocietyChatMessage -> item.id }
                    ) { it ->
                        if (requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).id == it.userId) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = "${it.createTimestamp}·${it.username}")
                                    Text(text = it.message, modifier = Modifier.height(40.dp))
                                }
                                Image(
                                    painter = rememberImagePainter(
                                        data = it.userIconUrl,
                                        imageLoader = requestHolder.coilImageLoader
                                    ),
                                    contentDescription = "",
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = it.userIconUrl,
                                        imageLoader = requestHolder.coilImageLoader
                                    ),
                                    contentDescription = "",
                                    modifier = Modifier.size(40.dp)
                                )
                                Column(horizontalAlignment = Alignment.Start) {
                                    Text(text = "${it.username}·${it.createTimestamp}")
                                    Text(text = it.message, modifier = Modifier.height(40.dp))
                                }

                            }
                        }
                    }
                    empty(chatMessageList)
                    spacer()
                },
                modifier = Modifier.fillMaxSize().clickable {
                    focusManager.clearFocus()
                }
            )



            Row(
                modifier = Modifier
                    .navigationBarsWithImePadding()
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var message by remember {
                    mutableStateOf("")
                }
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .fillMaxWidth(0.8F)
                        .onFocusEvent {
                            requestHolder.coroutineScope.launch {
                                lazyListState.animateScrollToItem(newestIndex)
                            }
                        }
                        .focusRequester(focusRequester)
                )
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        requestHolder.apiViewModel.societyChatInnerCreate(
                            societyId = requestHolder.transSociety.id,
                            userId = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).id,
                            message = message
                        ) {
                            requestHolder.apiViewModel.societyChatInnerList(
                                requestHolder.transSociety.id
                            ) { it ->
                                chatMessageList = it
                            }
                            message = ""
                        }
                    },
                    enabled = message.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "")
                }
            }
        }

    }
}