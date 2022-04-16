package com.wh.society.ui.page.detail.society.member.request

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.society.SocietyMemberRequest
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyMemberRequestListPage(requestHolder: RequestHolder) {

    var societyMemberRequestList by remember {
        mutableStateOf(emptyList<SocietyMemberRequest>())
    }

    val updateRequestList: () -> Unit = {
        requestHolder.apiViewModel.societyMemberRequestList(requestHolder.trans.society.id) {
            societyMemberRequestList = it.data.asReversed()
        }
    }

    LaunchedEffect(Unit) {
        updateRequestList.invoke()
    }

    GlobalScaffold(
        page = GlobalNavPage.SocietyMemberRequestListPage,
        requestHolder = requestHolder
    ) {

        LazyColumn(
            content = {
                items(
                    items = societyMemberRequestList,
                    key = { item: SocietyMemberRequest -> item.id },
                    itemContent = { it ->
                        Card(modifier = Modifier.padding(vertical = 16.dp, horizontal = 10.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 16.dp)
                            ) {
                                Column {
                                    Text(text = it.request)
                                    Row {
                                        if (it.isJoin) {
                                            Text(text = "加入申请 ")
                                        } else {
                                            Text(text = "退出申请 ")
                                        }
                                        if (it.isDealDone) {
                                            Text(text = "已处理 ")
                                            if (it.isAgreed) {
                                                Text(text = "已通过")
                                            } else {
                                                Text(text = "未通过")
                                            }
                                        } else {
                                            Text(text = "未处理")
                                        }
                                    }
                                    if (!it.isDealDone) {

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            TextButton(onClick = {
                                                requestHolder.apiViewModel.societyMemberRequestDeal(
                                                    requestId = it.id,
                                                    isAgreed = true,
                                                    onReturn = updateRequestList,
                                                    onError = requestHolder.toast.toast
                                                )
                                            }) {
                                                Text(text = "同意")
                                            }
                                            TextButton(onClick = {
                                                requestHolder.apiViewModel.societyMemberRequestDeal(
                                                    requestId = it.id,
                                                    isAgreed = false,
                                                    onReturn = updateRequestList,
                                                    onError = requestHolder.toast.toast
                                                )
                                            }) {
                                                Text(text = "驳回")
                                            }
                                        }

                                    }

                                }
                            }
                        }
                    }
                )
                empty(requestHolder.trans.societyMemberRequestList)
                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )

    }
}