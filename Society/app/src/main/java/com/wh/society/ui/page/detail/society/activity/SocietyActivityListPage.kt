package com.wh.society.ui.page.detail.society.activity

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wh.society.api.data.shadow.SocietyActivityShadow
import com.wh.society.api.data.society.SocietyActivity
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyActivityListPage(requestHolder: RequestHolder) {

    var activityList by remember { mutableStateOf(emptyList<SocietyActivity>()) }
    val thisSociety = requestHolder.trans.society

    val isAdmin = requestHolder.trans.isAdmin

    val prepareActivityList: () -> Unit = {
        requestHolder.apiViewModel.societyActivityList(thisSociety.id) { it ->
            activityList = it.data
        }
    }

    LaunchedEffect(Unit) {
        prepareActivityList.invoke()
    }

    GlobalScaffold(
        page = GlobalNavPage.SocietyActivityListPage,
        requestHolder = requestHolder,
        // 管理员增加一个 加号 fab
        fab = if (isAdmin) {
            {
                val activityShadow = SocietyActivityShadow()
                activityShadow.societyId = requestHolder.trans.society.id
                activityShadow.deviceName = requestHolder.deviceName

                var showDropdownMenu by remember {
                    mutableStateOf(false)
                }

                val plm = mapOf(
                    0 to "全部用户可见",
                    10 to "仅成员可见",
                    100 to "仅管理员可见"
                )

                FloatingActionButton(onClick = {
                    requestHolder.alert.alert(
                        title = "新活动",
                        content = {
                            Column {
                                TextField(
                                    value = activityShadow.title,
                                    onValueChange = { activityShadow.title = it },
                                    label = { Text(text = "活动标题") }
                                )
                                TextField(
                                    value = activityShadow.activity,
                                    onValueChange = { activityShadow.activity = it },
                                    label = { Text(text = "内容") }
                                )
                                Box {
                                    OutlinedButton(onClick = { showDropdownMenu = true }) {
                                        Text(text = "${activityShadow.level} ${plm[activityShadow.level]}")
                                    }
                                    DropdownMenu(
                                        expanded = showDropdownMenu,
                                        onDismissRequest = { showDropdownMenu = false }) {
                                        plm.entries.forEach {
                                            DropdownMenuItem(onClick = {
                                                showDropdownMenu = false
                                                activityShadow.level = it.key
                                            }) {
                                                Text(text = "权限设置 ${it.key} ${it.value}")
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        onOk = {
                            requestHolder.apiViewModel.societyActivityCreate(activityShadow) {
                                requestHolder.apiViewModel.societyActivityList(thisSociety.id) { it ->
                                    activityList = it.data
                                }
                            }

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
                    items = activityList.asReversed(),
                    key = { item: SocietyActivity -> item.id },
                    itemContent = { societyActivity ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            requestHolder.globalNav.goto(
                                                page = GlobalNavPage.SocietyActivityDetailPage,
                                                a = societyActivity
                                            )
                                        },
                                        onLongPress = {
                                            requestHolder.alert.alert(
                                                title = "操作",
                                                content = "选择要进行的操作",
                                                btns = mapOf(
                                                    "删除" to {
                                                        requestHolder.apiViewModel.societyActivityDelete(
                                                            activityId = societyActivity.id,
                                                            onReturn = prepareActivityList,
                                                            onError = requestHolder.toast.toast
                                                        )
                                                    },
                                                    "取消" to {}
                                                )
                                            )
                                        }
                                    )
                                }
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = societyActivity.title,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            Text(
                                text = societyActivity.createTSFmt(),
                                fontSize = 14.sp
                            )
                            Text(text = societyActivity.activity)
                            Text(text = "thisUserJoin:${societyActivity.thisUserJoin}")
                        }
                    }
                )

                empty(activityList)

                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
