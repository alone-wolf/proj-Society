package com.wh.society.ui.page.detail.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.wh.society.api.data.user.UserChatPrivate
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.ChatMessageItem
import com.wh.society.ui.componment.GlobalScaffold
import kotlinx.coroutines.launch

private var chatPrivateList by mutableStateOf(ReturnListData.blank<UserChatPrivate>())

@ExperimentalMaterialApi
@Composable
fun UserChatPrivatePage(requestHolder: RequestHolder) {

    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.userChatPrivateList(requestHolder.trans.societyMember.userId) { s ->
            chatPrivateList = s
        }
    }

    val newestIndex = chatPrivateList.newestIndex
    LaunchedEffect(chatPrivateList) {
        lazyListState.animateScrollToItem(newestIndex)
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    GlobalScaffold(
        page = GlobalNavPage.UserChatPrivate,
        remoteOperate = {
            requestHolder.apiViewModel.userChatPrivateList(requestHolder.trans.societyMember.userId) { s ->
                chatPrivateList = s
            }
        },
        requestHolder = requestHolder
    ) {
        Box {
            LazyColumn(
                state = lazyListState,
                content = {
                    items(
                        items = chatPrivateList.data,
                        key = { item: UserChatPrivate -> item.id }
                    ) { it ->
                        ChatMessageItem(
                            meId = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).id,
                            chatMessage = it,
                            requestHolder = requestHolder
                        )
                    }
                    empty(chatPrivateList)
                    spacer()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
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
                        requestHolder.apiViewModel.userChatPrivateCreate(
                            opUserId = requestHolder.trans.societyMember.userId,
                            message = message
                        ) {
                            requestHolder.apiViewModel.userChatPrivateList(
                                requestHolder.trans.societyMember.userId
                            ) { it ->
                                chatPrivateList = it
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