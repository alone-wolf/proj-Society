package com.wh.society.ui.page.main.mine

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.ui.componment.GlobalScaffold
import com.wh.society.ui.componment.PostItem

@ExperimentalMaterialApi
@Composable
fun MinePostListPage(requestHolder: RequestHolder) {

    GlobalScaffold(
        page = GlobalNavPage.MainMinePostListPage,
        requestHolder = requestHolder
    ) {
        LazyColumn(
            content = {
                empty(requestHolder.trans.postList)

                items(
                    items = requestHolder.trans.postList.data,
                    key = { item: Post -> item.id },
                    itemContent = { item: Post ->
                        PostItem(requestHolder = requestHolder, post = item)
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}