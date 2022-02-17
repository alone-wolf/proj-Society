package com.wh.society.ui.page.main.mine

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
                        Text(text = it.toString())
                    }
                )
                empty(all)
                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}