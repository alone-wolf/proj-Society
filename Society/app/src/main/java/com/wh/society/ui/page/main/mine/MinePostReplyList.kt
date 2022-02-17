package com.wh.society.ui.page.main.mine

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.society.api.data.PicData
import com.wh.society.api.data.PostReply
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold
import com.wh.society.ui.componment.MineUserPostReplyItem

@ExperimentalMaterialApi
@Composable
fun MinePostReplyListPage(requestHolder: RequestHolder) {
    GlobalScaffold(
        page = GlobalNavPage.MainMinePostReplyListPage,
        requestHolder = requestHolder
    ) {

        LazyColumn(
            content = {
                empty(requestHolder.transPostReplyList)

                items(
                    items = requestHolder.transPostReplyList.data,
                    key = { item: PostReply -> item.id },
                    itemContent = { item ->
                        MineUserPostReplyItem(requestHolder, item)
                    }
                )

                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )

    }
}