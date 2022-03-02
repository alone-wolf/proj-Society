package com.wh.society.ui.componment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage

@Composable
fun NavHost(
    navController: NavHostController,
    pageArray: Array<GlobalNavPage>,
    startPage: GlobalNavPage = pageArray.first(),
    requestHolder: RequestHolder
) {
    val builder: NavGraphBuilder.() -> Unit = {
        composableList(requestHolder, *pageArray)
    }
    androidx.navigation.compose.NavHost(
        navController,
        remember(null, startPage.route, builder) {
            navController.createGraph(startPage.route, null, builder)
        },
        Modifier
    )
}


fun NavGraphBuilder.composable(requestHolder: RequestHolder, page: GlobalNavPage) {
    composable(page.route, content = { page.content(requestHolder) })
}

fun NavGraphBuilder.composableList(
    requestHolder: RequestHolder,
    vararg page: GlobalNavPage
) {
    page.forEach { composable(requestHolder, it) }
}