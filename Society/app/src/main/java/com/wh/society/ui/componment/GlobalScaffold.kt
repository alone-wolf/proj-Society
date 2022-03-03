package com.wh.society.ui.componment

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage

@ExperimentalMaterialApi
@Composable
fun GlobalScaffold(
    page: GlobalNavPage,
    remoteOperate:()->Unit={},
    requestHolder: RequestHolder,
    actions: @Composable RowScope.() -> Unit = {},
    fab: @Composable ()->Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {

    LaunchedEffect(Unit){
        requestHolder.operatePlatform.currentRoute = page.route
        requestHolder.operatePlatform.currentOperate = remoteOperate
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = page.label) },
                navigationIcon = {
                    IconButton(onClick = { requestHolder.globalNav.goBack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                actions = actions
            )
        },
        floatingActionButton = fab,
        content = content
    )
}