package com.wh.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.wh.admin.componment.AlertRequest
import com.wh.admin.componment.HttpRequest
import com.wh.admin.componment.NavRequest
import com.wh.admin.componment.ServerDataViewModel
import com.wh.admin.data.society.Society
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.data.user.UserInfo
import com.wh.admin.ext.NavHost
import com.wh.admin.ui.theme.SocietyTheme
import kotlinx.coroutines.CoroutineScope

val listItemModifierWithPadding =
    Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)

val corner8 = RoundedCornerShape(8.dp)


class MainActivity : ComponentActivity() {

    private val TAG = "WH_"

    val http by lazy { HttpRequest(this) }

    val coilImageLoader: ImageLoader by lazy {
        ImageLoader.Builder(this)
            .apply {
                availableMemoryPercentage(0.5)
                bitmapPoolPercentage(0.5)
                crossfade(450)
            }
            .build()
    }

    lateinit var serverDataViewModel: ServerDataViewModel
    lateinit var scaffoldState: ScaffoldState
    lateinit var coroutineScope: CoroutineScope

    @OptIn(ExperimentalPagerApi::class)
    lateinit var pagerState: PagerState
    lateinit var navController: NavHostController

    var selectedUserInfo by mutableStateOf(UserInfo())
    var selectedSociety by mutableStateOf(Society())
    var selectedPost by mutableStateOf(Post())

    val nav by lazy { NavRequest(this) }
    val alert = AlertRequest()


    @OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        serverDataViewModel = ViewModelProvider(this)[ServerDataViewModel::class.java]

        setContent {
            SocietyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    LaunchedEffect(Unit) {
                        http.allUser()
                        http.allSociety()
                        http.allActivity()
                    }

                    scaffoldState = rememberScaffoldState()
                    coroutineScope = rememberCoroutineScope()

                    pagerState = rememberPagerState()
                    navController = rememberNavController()

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    val currentRoute = currentDestination?.route

                    var currentTitle by remember {
                        mutableStateOf("Society Admin")
                    }

                    NavDes.aa.find {
                        it.route == currentRoute
                    }?.title?.let {
                        currentTitle = it
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState,
                        topBar = {
                            TopAppBar(
                                title = { Text(text = currentTitle) },
                                actions = {
                                    if (currentRoute == NavDes.Main.route) {
                                        if (pagerState.currentPage != 2) {
                                            IconButton(
                                                onClick = {
                                                    when (pagerState.currentPage) {
                                                        0 -> http.allUser()
                                                        1 -> http.allSociety()
                                                    }
                                                },
                                                content = {
                                                    Icon(Icons.Default.Refresh, "")
                                                }
                                            )
                                        }
                                    }
                                }
                            )
                        },
                        floatingActionButton = {
                            if (currentRoute == NavDes.Main.route) {
                                if (pagerState.currentPage != 2) {
                                    FloatingActionButton(onClick = {
                                        when (pagerState.currentPage) {
                                            0 -> nav.toUserCreator()
                                            1 -> nav.toSocietyCreator()
                                        }
                                    }) {
                                        Icon(Icons.Default.Add, "")
                                    }
                                }
                            } else if (currentRoute == NavDes.UserSocietyMemberList.route) {
                                FloatingActionButton(onClick = {
                                    // ?????????
                                    nav.toUserSocietyJoiner.invoke()
                                }) {
                                    Icon(Icons.Default.Add, "")
                                }
                            }
                        },
                    ) {

                        NavHost(
                            navController = navController,
                            navDesArray = NavDes.aa,
                            startDes = NavDes.Main,
                            activity = this
                        )

                        alert.Alert1()
                        alert.Alert2()
                        alert.AlertX()
                    }
                }
            }
        }
    }
}



