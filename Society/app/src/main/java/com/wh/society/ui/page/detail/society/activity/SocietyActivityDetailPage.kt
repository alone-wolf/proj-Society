package com.wh.society.ui.page.detail.society.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.SocietyActivityMember
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.imageNames
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyActivityDetailPage(requestHolder: RequestHolder) {

    var memberList by remember {
        mutableStateOf(ReturnListData.blank<SocietyActivityMember>())
    }

    val thisActivity = requestHolder.trans.societyActivity
    var thisActivityThisUserJoin by remember {
        mutableStateOf(thisActivity.thisUserJoin)
    }

    val prepareMember: () -> Unit = {
        requestHolder.apiViewModel.societyActivityMember(thisActivity.id) {
            memberList = it
        }
    }

    LaunchedEffect(Unit) {
        prepareMember.invoke()
    }

    val updateThisActivity: () -> Unit = {
        thisActivityThisUserJoin = !thisActivityThisUserJoin
        thisActivity.thisUserJoin = thisActivityThisUserJoin

        requestHolder.apiViewModel.societyActivityMember(thisActivity.id) {
            memberList = it
        }
    }


    GlobalScaffold(
        page = GlobalNavPage.SocietyActivityDetailPage,
        requestHolder = requestHolder
    ) {

        LazyColumn(
            content = {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(text = thisActivity.title)
                            Text(text = thisActivity.activity)
                            Text(text = thisActivity.createTSFmt())
                            Text(text = thisActivity.societyName)
                            Text(text = "${thisActivity.permLevel}可用")
                            Row {
                                if (thisActivityThisUserJoin) {
                                    TextButton(onClick = {
                                        requestHolder.apiViewModel.societyActivityLeave(
                                            activityId = thisActivity.id,
                                            userId = requestHolder.apiViewModel.userInfo.id,
                                            onReturn = updateThisActivity,
                                            onError = requestHolder.toast.toast
                                        )
                                    }) {
                                        Text(text = "退出活动")
                                    }
                                } else {
                                    TextButton(onClick = {
                                        requestHolder.apiViewModel.societyActivityJoin(
                                            activityId = thisActivity.id,
                                            userId = requestHolder.apiViewModel.userInfo.id,
                                            onReturn = updateThisActivity,
                                            onError = requestHolder.toast.toast
                                        )
                                    }) {
                                        Text(text = "加入活动")
                                    }
                                }
                            }
                        }
                    }
                }

                imageNames(
                    items = memberList.data,
                    names = { it.username },
                    imageUrls = { it.realIconUrl },
                    requestHolder = requestHolder,
                    onClick = {
                        if (requestHolder.trans.isAdmin) {
                            requestHolder.alert.alert(
                                title = "操作",
                                content = "选择要进行的操作",
                                btns = mapOf(
                                    "踢出" to {
                                        requestHolder.apiViewModel.societyActivityMemberDelete(
                                            memberId = it.id,
                                            onReturn = prepareMember,
                                            onError = requestHolder.toast.toast
                                        )
                                    },
                                    "取消" to {}
                                )
                            )
                        }
                    }
                )

                spacer()
            },
            modifier = Modifier.fillMaxWidth()
        )

    }
}