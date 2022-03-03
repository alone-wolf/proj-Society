package com.wh.society.ui.page.detail.society.member.request

import androidx.compose.foundation.layout.fillMaxSize
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
import com.wh.society.api.data.society.SocietyMemberRequest
import com.wh.society.api.data.society.SocietyNotice
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyMemberRequestListPage(requestHolder: RequestHolder) {

    GlobalScaffold(
        page = GlobalNavPage.SocietyMemberRequestListPage,
        requestHolder = requestHolder
    ) {

        LazyColumn(
            content = {
                items(
                    items = requestHolder.trans.societyMemberRequestList.data.asReversed(),
                    key = { item: SocietyMemberRequest -> item.id },
                    itemContent = { it ->
                        Text(text = it.toString())
                    }
                )
                empty(requestHolder.trans.societyMemberRequestList)
                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )

    }
}