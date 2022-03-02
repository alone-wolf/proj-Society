package com.wh.society.ui.page.main.mine

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.wh.society.api.data.society.Society
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.ui.componment.GlobalScaffold
import com.wh.society.ui.componment.SocietyItem

@ExperimentalMaterialApi
@Composable
fun MineSocietyList(requestHolder: RequestHolder) {

    GlobalScaffold(
        page = GlobalNavPage.MainMineSocietyListPage,
        requestHolder = requestHolder
    ) {
        LazyColumn(content = {
            items(
                items = requestHolder.trans.userMember.data,
                key = { it.id },
                itemContent = { it ->
                    requestHolder.societyList.find { item: Society -> it.societyId == item.id }
                        ?.let {
                            SocietyItem(
                                requestHolder = requestHolder,
                                society = it,
                            )
                        }
                }
            )
            empty(requestHolder.trans.userMember)

        })
    }
}