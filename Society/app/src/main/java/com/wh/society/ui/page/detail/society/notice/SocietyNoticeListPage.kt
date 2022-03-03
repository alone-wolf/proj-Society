package com.wh.society.ui.page.detail.society.notice

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.society.api.data.society.SocietyNotice
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyNoticeListPage(requestHolder: RequestHolder) {


    GlobalScaffold(
        page = GlobalNavPage.SocietyNoticeListPage,
        requestHolder = requestHolder,
        fab = if (requestHolder.trans.isAdmin) {
            {
                FloatingActionButton(onClick = {

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
                    items = requestHolder.trans.societyNoticeList.data.asReversed(),
                    key = { item: SocietyNotice -> item.id },
                    itemContent = { it ->
                        Text(text = it.toString())
                    }
                )
                empty(requestHolder.trans.societyNoticeList)
                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}