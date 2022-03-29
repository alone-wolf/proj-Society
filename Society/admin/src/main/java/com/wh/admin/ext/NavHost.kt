package com.wh.admin.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.wh.admin.MainActivity
import com.wh.admin.NavDes

@Composable
fun NavHost(
    navController: NavHostController,
    navDesArray: Array<NavDes>,
    startDes: NavDes,
    activity: MainActivity
) {
    val builder: NavGraphBuilder.() -> Unit = {
        composableList(activity, *navDesArray)
    }
    androidx.navigation.compose.NavHost(
        navController,
        remember(null, startDes.route, builder) {
            navController.createGraph(startDes.route, null, builder)
        },
        Modifier
    )
}

fun NavGraphBuilder.composableList(
    activity: MainActivity,
    vararg page: NavDes
) {
    page.forEach { composable(activity, it) }
}

fun NavGraphBuilder.composable(activity: MainActivity, navDes: NavDes) {
    composable(navDes.route, content = { navDes.content(activity) })
}