package com.wh.society.ui.page.main.mine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wh.common.typeExt.toTimestamp
import com.wh.society.api.db.entity.Notify
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun MineNotifyListPage(requestHolder: RequestHolder) {

    val all by requestHolder.notifyViewModel.all.collectAsState(initial = emptyList())
    GlobalScaffold(page = GlobalNavPage.MainMineNotifyListPage, requestHolder = requestHolder) {
        LazyColumn(
            content = {
                items(
                    items = all,
                    key = { item: Notify -> item.id },
                    itemContent = { it ->
                        Card(
                            elevation = 5.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 16.dp)
                            ) {
                                Text(text = it.title)
                                Text(text = it.message, fontSize = 16.sp)
                                Text(text = it.timestamp.toTimestamp())
                            }
                        }
                    }
                )
                empty(all)
                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}