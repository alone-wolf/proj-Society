package com.wh.society.ui.page.detail.society.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.SocietyActivityMember
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyActivityDetailPage(requestHolder: RequestHolder) {

    var memberList by remember {
        mutableStateOf(ReturnListData.blank<SocietyActivityMember>())
    }

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.societyActivityMemberList(requestHolder.trans.societyActivity.id) {
            memberList = it
        }
    }


    GlobalScaffold(page = GlobalNavPage.SocietyActivityDetailPage, requestHolder = requestHolder) {

        LazyColumn(
            content = {
                val activity = requestHolder.trans.societyActivity
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
                            Text(text = activity.title)
                            Text(text = activity.activity)
                            Text(text = activity.createTimestamp)
                            Text(text = activity.societyName)
                            Text(text = activity.level.toString())
                        }
                    }
                }

                items(
                    items = memberList.data,
                    key = { item: SocietyActivityMember -> item.hashCode() },
                    itemContent = {
                        Card(onClick = {}) {
                            Text(text = it.toString())
                        }
                    }
                )

                spacer()
            },
            modifier = Modifier.fillMaxWidth()
        )

    }
}