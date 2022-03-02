package com.wh.society.ui.page.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.society.Society
import com.wh.society.componment.RequestHolder
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.SocietyItem

@ExperimentalMaterialApi
@Composable
fun SocietyListPage(requestHolder: RequestHolder) {
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
                items = requestHolder.societyList,
                key = { item: Society -> item.id },
                itemContent = { society: Society ->
                    SocietyItem(requestHolder = requestHolder, society = society)
                })

            if (requestHolder.societyList.isEmpty()) {
                empty()
            }

            spacer()
        }, modifier = Modifier.fillMaxWidth()
    )
}