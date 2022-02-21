package com.wh.admin.ui.componment

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import com.wh.admin.componment.RequestHolder


//@Composable
//fun Scaffold

//@ExperimentalMaterialApi
//@Composable
//fun GlobalScaffold(
//    page: GlobalNavPage,
//    requestHolder: RequestHolder,
//    actions: @Composable RowScope.() -> Unit = {},
//    fab: @Composable ()->Unit = {},
//    content: @Composable (PaddingValues) -> Unit
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = page.label) },
//                navigationIcon = {
//                    IconButton(onClick = { requestHolder.globalNav.goBack() }) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
//                    }
//                },
//                actions = actions
//            )
//        },
//        floatingActionButton = fab,
//        content = content
//    )
//}