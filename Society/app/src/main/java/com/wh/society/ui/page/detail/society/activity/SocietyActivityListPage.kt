package com.wh.society.ui.page.detail.society.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.SocietyActivity
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyActivityListPage(requestHolder: RequestHolder) {

    var activityList by remember { mutableStateOf(ReturnListData.blank<SocietyActivity>()) }

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.societyActivityList(requestHolder.trans.society.id) { it ->
            activityList = it
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
                            Box {

                            }
                        }
                    },
                    onOk = {

                        val societyActivity = SocietyActivity()
                        societyActivity.societyId = requestHolder.trans.society.id
                        societyActivity.deviceName = requestHolder.deviceName
                        societyActivity.title = activityTitle
                        societyActivity.activity = activity

                        requestHolder.apiViewModel.societyActivityCreate(societyActivity) {
                            requestHolder.apiViewModel.societyActivityList(requestHolder.trans.society.id) { it ->
                                activityList = it
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
                    items = activityList.data.asReversed(),
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

//private operator fun String.times(i: Int): String {
//    var a  = ""
//
//}
