package com.wh.society.ui.page.detail.society.chat

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.SocietyChatMessage
import com.wh.society.api.data.society.SocietyMember
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.ChatMessageItem
import com.wh.society.ui.componment.GlobalScaffold
import kotlinx.coroutines.launch

private var chatMessageList by mutableStateOf(ReturnListData.blank<SocietyChatMessage>())

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SocietyChatInnerPage(requestHolder: RequestHolder) {

    val lazyListState = rememberLazyListState()

    val thisSociety = requestHolder.trans.society

    var societyMember by remember {
        mutableStateOf(SocietyMember())
    }

    val updateChat: () -> Unit = {
        requestHolder.apiViewModel.societyChatInnerList(thisSociety.id) { it ->
            chatMessageList = it
        }
    }

    LaunchedEffect(Unit) {
        updateChat.invoke()

        // 获取发出请求用户的指定社团的成员信息
        requestHolder.apiViewModel.societyMemberBySocietyId(
            thisSociety.id,
            requestHolder.toast.toast
        ) {
            societyMember = it
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
            requestHolder.apiViewModel.societyChatInnerList(thisSociety.id) { it ->
                chatMessageList = it
            }
        },
        actions = {
            if (societyMember.permissionLevel == 111) {
                IconButton(onClick = {
                    requestHolder.alert.tip("确定要清除聊天记录？") {
                        requestHolder.apiViewModel.societyChatInnerClear(
                            thisSociety.id,
                            requestHolder.toast.toast
                        ) {
                            requestHolder.apiViewModel.societyChatInnerList(thisSociety.id) { it ->
                                chatMessageList = it
                            }
                        }
                    }
                }) {
                    Icon(Icons.Default.Delete, "")
                }
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
                        ChatMessageItem(
                            meId = requestHolder.apiViewModel.userInfo.id,
                            chatMessage = it,
                            requestHolder = requestHolder,
                            onClick = {
                                requestHolder.alert.confirm("删除这条聊天记录吗") {
                                    requestHolder.apiViewModel.societyChatInnerDelete(
                                        chatId = it.id,
                                        onReturn = updateChat,
                                        onError = requestHolder.toast.toast
                                    )
                                }
                            }
                        )
                    }
                    empty(chatMessageList)
                    spacer()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                focusManager.clearFocus()
                            }
                        )
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
                            societyId = requestHolder.trans.society.id,
                            userId = requestHolder.apiViewModel.userInfo.id,
                            message = message
                        ) {
                            requestHolder.apiViewModel.societyChatInnerList(
                                requestHolder.trans.society.id
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