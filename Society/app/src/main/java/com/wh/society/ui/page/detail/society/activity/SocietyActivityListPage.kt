package com.wh.society.ui.page.detail.society.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.societyActivityList(thisSociety.id) { it ->
            activityList = it.data
        }
    }

    GlobalScaffold(
        page = GlobalNavPage.SocietyActivityListPage,
        requestHolder = requestHolder,
        // 管理员增加一个 加号 fab
        fab = {
            var activityTitle by remember {
                mutableStateOf("")
            }
            var activity by remember {
                mutableStateOf("")
            }
            FloatingActionButton(onClick = {
                requestHolder.alert.alert(
                    title = "新活动",
                    content = {
                        Column {
                            TextField(
                                value = activityTitle,
                                onValueChange = { activityTitle = it },
                                label = { Text(text = "Activity Title") }
                            )
                            TextField(
                                value = activity,
                                onValueChange = { activity = it },
                                label = { Text(text = "Content") }
                            )
                        }
                    },
                    onOk = {

                        val societyActivity = SocietyActivity()
                        societyActivity.societyId = thisSociety.id
                        societyActivity.deviceName = requestHolder.deviceName
                        societyActivity.title = activityTitle
                        societyActivity.activity = activity

                        requestHolder.apiViewModel.societyActivityCreate(societyActivity) {
                            requestHolder.apiViewModel.societyActivityList(thisSociety.id) { it ->
                                activityList = it.data
                            }
                        }

                    },
                    onCancel = {}
                )

            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) {
        LazyColumn(
            content = {
                items(
                    items = activityList.asReversed(),
                    key = { item: SocietyActivity -> item.id },
                    itemContent = { it ->
                        Card(
                            onClick = {
                                requestHolder.globalNav.goto(
                                    GlobalNavPage.SocietyActivityDetailPage,
                                    it
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(
                                    vertical = 10.dp,
                                    horizontal = 16.dp
                                )
                            ) {
                                Text(
                                    text = it.title,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(end = 8.dp)
                                )

                                Text(
                                    text = it.createTSFmt(),
                                    fontSize = 14.sp
                                )
                                Text(text = it.activity)
                                Text(text = "thisUserJoin:${it.thisUserJoin}")
                            }
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
