package com.wh.society.ui.page.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.conditionItem
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun UserDetailPage(requestHolder: RequestHolder) {

    GlobalScaffold(page = GlobalNavPage.DetailUserInfo, requestHolder = requestHolder) {
        LazyColumn(
            content = {
                item {
                    Text(text = requestHolder.transUserInfo.toString())
                }

                conditionItem(
                    show = requestHolder.userInfo.id != requestHolder.transUserInfo.id,
                    content = {
                        Button(onClick = {
                            requestHolder.globalNav.gotoUserPrivateChat()
                        }) {
                            Text(text = "Private Chat")
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}