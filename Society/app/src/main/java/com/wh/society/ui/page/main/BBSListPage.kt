package com.wh.society.ui.page.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.componment.RequestHolder
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.BBSItem

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun BBSListPage(requestHolder: RequestHolder) {
    LazyColumn(
        content = {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
//                        .background(color = Color.Cyan)
                ) {
//                    Text(text = "已入驻")
                }
            }
            items(
                items = requestHolder.bbsList,
                key = { item: BBS -> item.hashCode() },
                itemContent = { bbs: BBS ->
                    BBSItem(requestHolder = requestHolder, bbs = bbs)
                })

            empty(requestHolder.bbsList)


            spacer()
        }, modifier = Modifier.fillMaxWidth()
    )
}