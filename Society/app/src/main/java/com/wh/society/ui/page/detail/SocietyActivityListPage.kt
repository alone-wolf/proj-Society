package com.wh.society.ui.page.detail

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
            FloatingActionButton(onClick = {
                requestHolder.apiViewModel.societyActivityCreate(
                    societyId = requestHolder.trans.society.id,
                    deviceName = requestHolder.deviceName,
                    title = System.currentTimeMillis().toString(),
                    activity = System.currentTimeMillis().toString(),
                    level = 11
                ) {
                    requestHolder.apiViewModel.societyActivityList(requestHolder.trans.society.id) { it ->
                        activityList = it
                    }
                }
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
                                requestHolder.globalNav.gotoSocietyActivityDetail(it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 1.dp)
                        ) {
                            Text(
                                text = it.title,
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                            )
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
