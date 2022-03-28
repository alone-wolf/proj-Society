package com.wh.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import com.google.accompanist.pager.*
import com.wh.admin.componment.ServerDataViewModel
import com.wh.admin.data.society.Society
import com.wh.admin.data.society.SocietyMember
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.data.society.bbs.PostReply
import com.wh.admin.data.user.UserInfo
import com.wh.admin.store.SettingStore
import com.wh.admin.ui.theme.SocietyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val listItemModifierWithPadding =
    Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)

val corner8 = RoundedCornerShape(8.dp)

@Composable
fun SingleLineText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, maxLines = 1, overflow = TextOverflow.Ellipsis)
}

class MainActivity : ComponentActivity() {

    sealed class NavDes(val route: String, val title: String) {
        object Main : NavDes("main", "Main")
        object UserDetail : NavDes("user-detail", "User Detail")
        object UserPostList : NavDes("user-post-list", "User Post List")
        object UserReplyList : NavDes("user-reply-list", "User Reply List")
        object UserSocietyMemberList : NavDes("user-society-member-list", "User Society Member")
        object SocietyDetail : NavDes("society-detail", "Society Detail")
        object PostDetail : NavDes("post-detail", "Post Detail")
        object UserCreator : NavDes("user-creator", "User Creator")
        object SocietyCreator : NavDes("society-creator", "Society Creator")

        companion object {

            @ExperimentalAnimationApi
            @ExperimentalMaterialApi
            fun a(): Array<NavDes> {
                return NavDes::class.nestedClasses
                    .filter { !it.isCompanion }
                    .map { it.objectInstance as NavDes }
                    .toTypedArray()
            }

            @ExperimentalMaterialApi
            @OptIn(ExperimentalAnimationApi::class)
            val aa = a()
        }
    }

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

    private fun NavGraphBuilder.composable(
        n: NavDes,
        v: @Composable (NavBackStackEntry) -> Unit
    ) {
        composable(n.route, content = v)
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
        androidx.compose.material.ExperimentalMaterialApi::class
    )
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                            startDestination = NavDes.Main.route
                        ) {
                            composable(NavDes.Main) {
                                MainPage(activity = this@MainActivity)
                            }
                            composable(NavDes.UserDetail) {
                                UserDetailPage(activity = this@MainActivity)
                            }
                            composable(NavDes.UserPostList) {
                                UserPostListPage(activity = this@MainActivity)
                            }
                            composable(NavDes.UserReplyList) {
                                UserReplyListPage(activity = this@MainActivity)
                            }
                            composable(NavDes.UserSocietyMemberList) {
                                UserSocietyMemberListPage(activity = this@MainActivity)
                            }
                            composable(NavDes.SocietyDetail) {
                                SocietyDetailPage(activity = this@MainActivity)
                            }
                            composable(NavDes.PostDetail) {
                                PostDetailPage(activity = this@MainActivity)
                            }
                            composable(NavDes.UserCreator) {

                            }
                            composable(NavDes.SocietyCreator) {

                            }
                        }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainPage(activity: MainActivity) {
    Column(modifier = Modifier.fillMaxSize()) {
        TabLayout(
            pagerState = activity.pagerState,
            titles = listOf("User", "Society", "Setting"),
            activity = activity
        )
        HorizontalPager(
            count = 3,
            state = activity.pagerState,
            modifier = Modifier.fillMaxSize()
        ) { i ->
            when (i) {
                0 -> UserList(activity = activity)
                1 -> SocietyList(activity = activity)
                2 -> SettingOptionList(activity = activity)
            }
        }
    }
}

@Composable
fun UserDetailPage(activity: MainActivity) {

    val userInfo = activity.selectedUserInfo

    LazyColumn(content = {
        item {
            Box(modifier = listItemModifierWithPadding) {
                Card(
                    modifier = Modifier.padding(bottom = 50.dp),
                    elevation = 5.dp
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = userInfo.realIconUrl,
                            imageLoader = activity.coilImageLoader,
                            builder = {
                                transformations(BlurTransformation(activity))
                            }
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Image(
                    painter = rememberImagePainter(
                        data = userInfo.realIconUrl,
                        imageLoader = activity.coilImageLoader
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(100.dp)
                        .shadow(elevation = 5.dp, CircleShape)
                        .clip(CircleShape)
                        .border(4.dp, color = Color.White, shape = CircleShape)
                        .align(Alignment.BottomEnd),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 8.dp, bottom = 30.dp)
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .shadow(3.dp, CircleShape)
                            .background(Color.Green, CircleShape)
                    ) {
                        Icon(Icons.Default.Edit, "")
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .shadow(3.dp, CircleShape)
                            .background(Color.Red, CircleShape)
                    ) {
                        Icon(Icons.Default.Delete, "")
                    }
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
            ) {
                SingleLineText(text = "姓名: ${userInfo.name}")
                SingleLineText(text = "用户名: ${userInfo.username}")
                SingleLineText(text = "学院: ${userInfo.college}")
                SingleLineText(text = "手机号: ${userInfo.phone}")
                SingleLineText(text = "邮箱: ${userInfo.email}")
                SingleLineText(text = "学号: ${userInfo.studentNumber}")
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable(onClick = activity.navToUserPostList)
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "用户发布的帖子", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable(onClick = activity.navToUserReplyList)
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "用户发表的回复", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable(onClick = activity.navToUserSocietyMemberList)
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "用户加入的社团", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable { }
                .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "用户的社团申请", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable { }
                .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "用户参加的活动", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable { }
                .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "用户的活动申请", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

    }, modifier = Modifier.fillMaxSize())
}

@Composable
fun UserPostListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo

    var posts by remember {
        mutableStateOf(emptyList<Post>())
    }

    LaunchedEffect(Unit) {
        posts = activity.serverDataViewModel.getAllUserPost(userInfo.id) {}
    }

    LazyColumn(content = {
        items(
            items = posts,
            key = { item: Post -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity.navToPostDetail(it)
                    }
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "标题：${it.title}")
                    SingleLineText(text = "帖子内容：${it.post}")
                    SingleLineText(text = "社团名称：${it.societyName}")
                    SingleLineText(text = "发帖时间：${it.createTimestamp}")
                    SingleLineText(text = "更新时间：${it.updateTimestamp}")
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}

@Composable
fun UserReplyListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo
    var replies by remember {
        mutableStateOf(emptyList<PostReply>())
    }

    LaunchedEffect(Unit) {
        replies = activity.serverDataViewModel.getAllUserReply(userInfo.id) {}
    }

    LazyColumn(content = {
        items(
            items = replies,
            key = { item: PostReply -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "标题：${it.postTitle}")
                    SingleLineText(text = "回复内容：${it.reply}")
                    SingleLineText(text = "设备尾巴：${it.deviceName}")
                    SingleLineText(text = "社团名称：${it.societyName}")
                    SingleLineText(text = "发帖时间：${it.createTimestamp}")
                    SingleLineText(text = "更新时间：${it.updateTimestamp}")
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}

@Composable
fun UserSocietyMemberListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo
    var memberList by remember {
        mutableStateOf(emptyList<SocietyMember>())
    }

    var societyList by remember {
        mutableStateOf(emptyList<Society>())
    }
    LaunchedEffect(Unit) {
        memberList = activity.serverDataViewModel.getAllUserSocietyMember(userInfo.id) {}
        societyList = activity.serverDataViewModel.allSociety.filter { s ->
            memberList.any { sm -> s.id == sm.societyId }
        }
    }

    LazyColumn(content = {
        items(
            items = societyList,
            key = { item: Society -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "社团名称：${it.name}")
                    SingleLineText(text = "社团简介：${it.describe}")
//                    SingleLineText(text = "设备尾巴：${it.deviceName}")
//                    SingleLineText(text = "社团名称：${it.societyName}")
                    SingleLineText(text = "发帖时间：${it.createTimestamp}")
                    SingleLineText(text = "更新时间：${it.updateTimestamp}")
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())

}

@Composable
fun SocietyDetailPage(activity: MainActivity) {

    val society = activity.selectedSociety

    LazyColumn(content = {
        item {
            Box(modifier = listItemModifierWithPadding) {
                Card(
                    modifier = Modifier.padding(bottom = 50.dp),
                    elevation = 5.dp
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = society.realIconUrl,
                            imageLoader = activity.coilImageLoader,
                            builder = {
                                transformations(BlurTransformation(activity))
                            }
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Image(
                    painter = rememberImagePainter(
                        data = society.realIconUrl,
                        imageLoader = activity.coilImageLoader
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(100.dp)
                        .shadow(elevation = 5.dp, CircleShape)
                        .clip(CircleShape)
                        .border(4.dp, color = Color.White, shape = CircleShape)
                        .align(Alignment.BottomEnd),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 8.dp, bottom = 30.dp)
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .shadow(3.dp, CircleShape)
                            .background(Color.Green, CircleShape)
                    ) {
                        Icon(Icons.Default.Edit, "")
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .shadow(3.dp, CircleShape)
                            .background(Color.Red, CircleShape)
                    ) {
                        Icon(Icons.Default.Delete, "")
                    }
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
            ) {
                SingleLineText(text = "名称: ${society.name}")
                SingleLineText(text = "简介: ${society.describe}")
                SingleLineText(text = "学院: ${society.college}")
                SingleLineText(text = "论坛名称: ${society.bbsName}")
                SingleLineText(text = "论坛简介: ${society.bbsDescribe}")
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
//                    .clickable(onClick = activity.navToUserPostList)
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "论坛帖子", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
//                    .clickable(onClick = activity.navToUserReplyList)
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "帖子回复", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
//                .clickable(onClick = activity.navToUserSocietyMemberList)
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "社团成员", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable { }
                .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "成员申请", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable { }
                .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "社团活动", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable { }
                .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "活动申请", fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }

    }, modifier = Modifier.fillMaxSize())
}

@Composable
fun PostDetailPage(activity: MainActivity) {

    val post = activity.selectedPost

    var replies by remember {
        mutableStateOf(emptyList<PostReply>())
    }

    LaunchedEffect(Unit) {
        replies = activity.serverDataViewModel.getAllPostReply(post.id) {}
    }

    LazyColumn(content = {

        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable {}
                .padding(horizontal = 20.dp, vertical = 10.dp)) {
                SingleLineText(text = "标题：${post.title}")
                SingleLineText(text = "帖子内容：${post.post}")
                SingleLineText(text = "社团名称：${post.societyName}")
                SingleLineText(text = "发帖时间：${post.createTimestamp}")
                SingleLineText(text = "更新时间：${post.updateTimestamp}")
            }
        }

        items(
            items = replies,
            key = { item: PostReply -> item.hashCode() },
            itemContent = { it ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "回复内容：${it.reply}")
                    SingleLineText(text = "设备尾巴：${it.deviceName}")
                    SingleLineText(text = "回复时间：${it.createTimestamp}")
                    SingleLineText(text = "更新时间：${it.updateTimestamp}")
                }
            }
        )
    }, modifier = Modifier.fillMaxSize())

}

@ExperimentalPagerApi
@Composable
fun TabLayout(pagerState: PagerState, titles: List<String>, activity: MainActivity) {
    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.fillMaxWidth(),
            indicatorHeight = 40.dp,
            indicatorWidth = 131.dp,
            inactiveColor = Color.Transparent,
            activeColor = Color.LightGray,
            indicatorShape = corner8,
            spacing = 0.dp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            titles.forEachIndexed { index, s ->
                val m = Modifier
                    .weight(1f / titles.size)
                    .height(40.dp)
                    .clip(corner8)
                    .clickable {
                        activity.coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                Column(
                    modifier = m,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = s, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun UserList(activity: MainActivity) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = activity.serverDataViewModel.allUser,
            key = { item: UserInfo -> item.hashCode() },
            itemContent = { it ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity.navToUserDetail(it)
                    }) {
                    Row(
                        modifier = listItemModifierWithPadding.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = it.realIconUrl,
                                imageLoader = activity.coilImageLoader
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(color = Color.LightGray),
                            contentScale = ContentScale.Crop,
                        )
                        Column {
                            SingleLineText(text = "姓名: ${it.name}")
                            SingleLineText(text = "用户名: ${it.username}")
                            SingleLineText(text = "学院: ${it.college}")
                            SingleLineText(text = "手机号: ${it.phone}")
                            SingleLineText(text = "邮箱: ${it.email}")
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun SocietyList(activity: MainActivity) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = activity.serverDataViewModel.allSociety,
            key = { item: Society -> item.hashCode() },
            itemContent = { it ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity.navToSocietyDetail(it)
                    }
                ) {
                    Row(
                        modifier = listItemModifierWithPadding.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = it.realIconUrl,
                                imageLoader = activity.coilImageLoader
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(color = Color.LightGray),
                            contentScale = ContentScale.Crop,
                        )
                        Column {
                            SingleLineText(text = "社团名称: ${it.name}")
                            SingleLineText(
                                text = "论坛名称: ${it.bbsName}"
                            )
                            SingleLineText(
                                text = "学院: ${it.optCollege}"
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun SettingOptionList(activity: MainActivity) {
    var allowUserRegister by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(Unit) {
        delay(500)
        allowUserRegister = activity.serverDataViewModel.adminUserRegisterAllow() {}
            .contentEquals("{\"code\":200,\"message\":\"OK\",\"data\":{\"userRegisterAllow\":true}}")
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {

            val bc by animateColorAsState(if (allowUserRegister) Color.Transparent else Color.LightGray)
            val animateColor by animateColorAsState(if (allowUserRegister) Color.Green else Color.Transparent)
            Row(
                modifier = listItemModifierWithPadding
                    .clip(corner8)
                    .clickable {
                        activity.coroutineScope.launch {
                            allowUserRegister = activity.serverDataViewModel
                                .adminUserRegisterAllowSwitch { }
                                .contentEquals("{\"code\":200,\"message\":\"OK\",\"data\":{\"userRegisterAllow\":true}}")
                        }
                    }
                    .border(width = 2.dp, bc, corner8)
                    .height(60.dp)
                    .background(animateColor, corner8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Allow User Register")
                AnimatedVisibility(allowUserRegister) {
                    Icon(Icons.Default.Check, "")
                }
                AnimatedVisibility(!allowUserRegister) {
                    Icon(Icons.Default.Close, "")
                }
            }
        }
        item {
            Row(
                modifier = listItemModifierWithPadding
                    .clip(corner8)
                    .clickable {

                    }
                    .border(width = 2.dp, Color.LightGray, corner8)
                    .height(60.dp)
                    .background(Color.Transparent, corner8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("导出全部用户数据")
                Icon(Icons.Default.Info, "")
            }
        }
        item {
            Row(
                modifier = listItemModifierWithPadding
                    .clip(corner8)
                    .clickable {

                    }
                    .border(width = 2.dp, Color.LightGray, corner8)
                    .height(60.dp)
                    .background(Color.Transparent, corner8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("追加导入用户数据")
                Icon(Icons.Default.Info, "")
            }
        }
        item {
            Row(
                modifier = listItemModifierWithPadding
                    .clip(corner8)
                    .clickable {

                    }
                    .border(width = 2.dp, Color.LightGray, corner8)
                    .height(60.dp)
                    .background(Color.Transparent, corner8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("覆盖导入用户数据")
                Icon(Icons.Default.Info, "")
            }
        }
        item {
            Row(
                modifier = listItemModifierWithPadding
                    .clip(corner8)
                    .clickable {

                    }
                    .border(width = 2.dp, Color.LightGray, corner8)
                    .height(60.dp)
                    .background(Color.Transparent, corner8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("生成用户数据表格模板")
                Icon(Icons.Default.Info, "")
            }
        }
        item {
            Row(
                modifier = listItemModifierWithPadding
                    .clip(corner8)
                    .clickable {

                    }
                    .border(width = 2.dp, Color.LightGray, corner8)
                    .height(60.dp)
                    .background(Color.Transparent, corner8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("导出全部社团数据")
                Icon(Icons.Default.Info, "")
            }
        }
        item {
            Row(
                modifier = listItemModifierWithPadding
                    .clip(corner8)
                    .clickable {

                    }
                    .border(width = 2.dp, Color.LightGray, corner8)
                    .height(60.dp)
                    .background(Color.Transparent, corner8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("追加导入社团数据")
                Icon(Icons.Default.Info, "")
            }
        }
        item {
            Row(
                modifier = listItemModifierWithPadding
                    .clip(corner8)
                    .clickable {

                    }
                    .border(width = 2.dp, Color.LightGray, corner8)
                    .height(60.dp)
                    .background(Color.Transparent, corner8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("覆盖导入社团数据")
                Icon(Icons.Default.Info, "")
            }
        }
        item {
            Row(
                modifier = listItemModifierWithPadding
                    .clip(corner8)
                    .clickable {

                    }
                    .border(width = 2.dp, Color.LightGray, corner8)
                    .height(60.dp)
                    .background(Color.Transparent, corner8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("生成社团数据表格模板")
                Icon(Icons.Default.Info, "")
            }
        }
    }
}