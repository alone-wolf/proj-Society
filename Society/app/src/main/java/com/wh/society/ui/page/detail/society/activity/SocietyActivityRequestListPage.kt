package com.wh.society.ui.page.detail.society.activity

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.SocietyActivityRequest
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyActivityRequestListPage(requestHolder: RequestHolder) {

    var requestList by remember { mutableStateOf(ReturnListData.blank<SocietyActivityRequest>()) }

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.societyActivityRequestList(requestHolder.trans.society.id) {
            requestList = it
        }
    }

    GlobalScaffold(
        page = GlobalNavPage.SocietyActivityRequestListPage,
        requestHolder = requestHolder,
//        fab = {
//            FloatingActionButton(onClick = {
//                requestHolder.apiViewModel.societyActivityRequestCreate(
//                    societyId = requestHolder.trans.society.id,
//                    userId = requestHolder.apiViewModel.userInfo.data!!.id,
//                    request = "aaaaa",
//                    isJoin = true
//                ) {
//                    requestHolder.apiViewModel.societyActivityRequestList(requestHolder.trans.society.id) {
//                        requestList = it
//                    }
//                }
//            }) {
//                Icon(imageVector = Icons.Default.Add, contentDescription = "")
//            }
//        }
    ) {
        LazyColumn(
            content = {
                items(
                    items = requestList.data,
                    key = { item: SocietyActivityRequest -> item.id },
                    itemContent = { it ->
                        Card(onClick = { /*TODO*/ }) {
                            Text(text = it.toString())
                        }
                    }
                )

                empty(requestList)

                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}