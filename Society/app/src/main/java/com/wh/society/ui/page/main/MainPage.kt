package com.wh.society.ui.page.main

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.navigation.MainNavPage

private var _currentPageLabel = "Mine"

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MainPage(requestHolder: RequestHolder) {
    val mainNavController = rememberNavController()
    var currentPageLabel: String by remember { mutableStateOf(_currentPageLabel) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = currentPageLabel) },
                actions = {
                    AnimatedVisibility(
                        visible = currentPageLabel == "Mine",
                        enter = fadeIn() + slideInHorizontally(initialOffsetX= { it / 2 }),
                        exit = fadeOut() + slideOutHorizontally(targetOffsetX= { it / 2 })
                    ) {
                        Row {
                            IconButton(onClick = {
                                requestHolder.globalNav.goto(GlobalNavPage.MainMineNotifyListPage)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = ""
                                )
                            }
                            IconButton(onClick = {
                                requestHolder.globalNav.goto(GlobalNavPage.MainMineInfoEditorPage)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = ""
                                )
                            }
                            IconButton(onClick = {
                                requestHolder.globalNav.goto(GlobalNavPage.Setting)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                })
        },
        bottomBar = {
            BottomNavigation(elevation = 3.dp) {
                val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                MainNavPage.navMap.values.forEach { mainNavPage ->
                    BottomNavigationItem(
                        selected = currentDestination?.hierarchy?.any { it.route == mainNavPage.route } == true,
                        onClick = {
                            mainNavController.navigate(mainNavPage.route) {
                                popUpTo(mainNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            currentPageLabel = mainNavPage.label
                            _currentPageLabel = mainNavPage.label
                        },
                        icon = {
                            Icon(
                                imageVector = mainNavPage.icon,
                                contentDescription = mainNavPage.label
                            )
                        },
                        label = { Text(text = mainNavPage.label) }
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = mainNavController,
            startDestination = "mine"
        ) {
            composable("mine") {
                MinePage(requestHolder = requestHolder)
            }
            composable("society") {
                SocietyListPage(requestHolder = requestHolder)
            }
            composable("bbs") {
                BBSListPage(requestHolder = requestHolder)
            }

        }
    }
}