package com.wh.admin

import android.graphics.Point
import android.os.Bundle
import android.view.Display
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.*
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.google.accompanist.pager.*
import com.wh.admin.componment.ServerDataViewModel
import com.wh.admin.data.society.Society
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.data.user.UserInfo
import com.wh.admin.ext.NavHost
import com.wh.admin.store.SettingStore
import com.wh.admin.ui.theme.SocietyTheme
import kotlinx.coroutines.CoroutineScope
import kotlin.properties.Delegates

val listItemModifierWithPadding =
    Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)

val corner8 = RoundedCornerShape(8.dp)

class MainActivity : ComponentActivity() {

    private val TAG = "WH_"

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
    lateinit var settingStore: SettingStore
    lateinit var scaffoldState: ScaffoldState
    lateinit var coroutineScope: CoroutineScope

    @OptIn(ExperimentalPagerApi::class)
    lateinit var pagerState: PagerState

    lateinit var navController: NavHostController

    var selectedUserInfo by mutableStateOf(UserInfo())
    var selectedSociety by mutableStateOf(Society())
    var selectedPost by mutableStateOf(Post())

    private fun NavHostController.goto(n: NavDes) {
        this.navigate(n.route)
    }

    val navBack = {
        navController.popBackStack()
    }

    val navToUserDetail = { u: UserInfo ->
        selectedUserInfo = u
        navController.goto(NavDes.UserDetail)
    }

    val navToUserPostList = {
        navController.goto(NavDes.UserPostList)
    }

    val navToUserReplyList = {
        navController.goto(NavDes.UserReplyList)
    }

    val navToUserSocietyMemberList = {
        navController.goto(NavDes.UserSocietyMemberList)
    }

    val navToSocietyDetail = { s: Society ->
        selectedSociety = s
        navController.goto(NavDes.SocietyDetail)
    }


    val navToPostDetail = { p: Post ->
        selectedPost = p
        navController.goto(NavDes.PostDetail)
    }

    val navToUserCreator = {
        navController.goto(NavDes.UserCreator)
    }

    val navToSocietyCreator = {
        navController.goto(NavDes.SocietyCreator)
    }



    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalMaterialApi::class
    )
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultDisplay: Display = windowManager.defaultDisplay
        val point = Point()
        defaultDisplay.getSize(point)
        val x: Int = point.x

        serverDataViewModel = ViewModelProvider(this)[ServerDataViewModel::class.java]
        settingStore = SettingStore(this)

        setContent {
            SocietyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    LaunchedEffect(Unit) {
                        serverDataViewModel.getAllUser() {}
                        serverDataViewModel.getAllSociety() {}
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
                        topBar = { TopAppBar(title = { Text(text = currentTitle) }) },
                        floatingActionButton = {
                            if (currentRoute == NavDes.Main.route) {
                                if (pagerState.currentPage != 2) {
                                    FloatingActionButton(onClick = {
                                        when (pagerState.currentPage) {
                                            0 -> navToUserCreator()
                                            1 -> navToSocietyCreator()
                                        }
                                    }) {
                                        Icon(Icons.Default.Add, "")
                                    }
                                }
                            }
                        }
                    ) {

                        NavHost(
                            navController = navController,
                            navDesArray = NavDes.aa,
                            startDes = NavDes.Main,
                            activity = this
                        )

                    }
                }
            }
        }
    }
}



