package com.wh.society

import android.content.*
import android.os.Bundle
import android.text.util.Linkify
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.google.accompanist.insets.ProvideWindowInsets
import com.wh.society.api.data.*
import com.wh.society.api.data.society.*
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.componment.OperatePlatform
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.service.SocketIOService
import com.wh.society.store.SettingStore
import com.wh.society.ui.componment.MyAlertDialog
import com.wh.society.ui.page.detail.*
import com.wh.society.ui.page.detail.bbs.BBSDetailPage
import com.wh.society.ui.page.detail.bbs.BBSPostDetail
import com.wh.society.ui.page.detail.bbs.BBSPostEditor
import com.wh.society.ui.page.login.FindPasswordPage
import com.wh.society.ui.page.login.LoginPage
import com.wh.society.ui.page.login.RegisterPage
import com.wh.society.ui.page.main.MainPage
import com.wh.society.ui.page.main.mine.*
import com.wh.society.ui.page.setting.SettingPage
import com.wh.society.ui.theme.SocietyTheme
import com.wh.society.util.ActivityOpener
import com.wh.society.util.SystemUtil
import com.wh.society.viewModel.ApiViewModel
import com.wh.society.viewModel.NotifyViewModel
import io.noties.markwon.Markwon
import io.noties.markwon.image.coil.CoilImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import kotlinx.coroutines.CoroutineScope

class MainActivity : AppCompatActivity(), RequestHolder {

    override val operatePlatform: OperatePlatform = OperatePlatform()

    private val viewModelFactory by lazy { (application as App).viewModelFactory }
    override val apiViewModel: ApiViewModel by viewModels { viewModelFactory }
    override val notifyViewModel: NotifyViewModel by viewModels { viewModelFactory }
    override lateinit var coroutineScope: CoroutineScope
    lateinit var globalNavController: NavController
    override val globalNav: RequestHolder.GlobalNavRequest by lazy {
        object : RequestHolder.GlobalNavRequest(globalNavController, this) {}
    }
    override val coilImageLoader: ImageLoader by lazy {
        ImageLoader.Builder(this)
            .apply {
                availableMemoryPercentage(0.5)
                bitmapPoolPercentage(0.5)
                crossfade(450)
                allowHardware(true)
                placeholder(R.drawable.ic_baseline_image_24)
            }
            .build()
    }
    override val markdown: Markwon by lazy {
        Markwon.builder(this)
            .usePlugin(CoilImagesPlugin.create(this, coilImageLoader))
            .usePlugin(LinkifyPlugin.create(Linkify.WEB_URLS))
            .build()
    }

    override val settingStore: SettingStore by lazy { (application as App).storeKeeper.settingStore }

    override val societyList: List<Society> by lazy { apiViewModel.societyList.data }
    override val collegeList: ReturnListData<College> by lazy { apiViewModel.collegeList }
    override val bbsList: List<BBS> by lazy { apiViewModel.bbsList }


    override val trans: RequestHolder.DataTrans by lazy {
        object : RequestHolder.DataTrans() {}
    }

    override lateinit var deviceName: String

    override val alertRequest: RequestHolder.AlertRequestCompact by lazy {
        object : RequestHolder.AlertRequestCompact(this.resources, this) {}
    }

    override lateinit var imagePicker: RequestHolder.ImagePickerRequest

    override val startSocketIOService: (Int, String) -> Unit by lazy {
        { userId: Int, cookieToken: String ->
            SocketIOService.startSIOService(userId, cookieToken, this)
        }
    }

    override val stopSocketIOService: () -> Unit by lazy {
        { SocketIOService.stopSIOService(this) }
    }

    override val myContentResolver: ContentResolver by lazy {
        this.contentResolver
    }
    override val activity: MainActivity by lazy {
        this
    }

    private val onOperationRequestReceiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                p1?.let {
                    val path = it.getStringExtra("path")
//                    val event = it.getStringExtra("opt-event")

                    if (operatePlatform.currentRoute == path) {
                        operatePlatform.currentOperate()
                    }
                }
            }

        }
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(onOperationRequestReceiver, IntentFilter(SocketIOService.OPERATE_PLATFORM))

        imagePicker = object : RequestHolder.ImagePickerRequest(
            ActivityOpener
                .imagePickerActivity(this, this), this
        ) {}

        deviceName = SystemUtil.getDeviceModel()


        setContent {
            coroutineScope = rememberCoroutineScope()
            globalNavController = rememberNavController()
            LaunchedEffect(Unit) {
                if (settingStore.autoLogin) {
                    loginLogic()
                }
            }

            SocietyTheme {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {

                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        NavHost(
                            navController = globalNavController as NavHostController,
                            startDestination = GlobalNavPage.LoginPage.route
                        ) {
                            // login block
                            composable(GlobalNavPage.LoginPage) {
                                LoginPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.RegisterPage) {
                                RegisterPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.FindPasswordPage) {
                                FindPasswordPage(requestHolder = this@MainActivity)
                            }

                            composable(GlobalNavPage.Setting) {
                                SettingPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.DetailUserInfo) {
                                UserDetailPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.UserChatPrivate) {
                                UserChatPrivatePage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.DetailSociety) {
                                SocietyDetailPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.DetailBBS) {
                                BBSDetailPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.DetailPost) {
                                BBSPostDetail(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.DetailPostEditor) {
                                BBSPostEditor(requestHolder = this@MainActivity)
                            }

                            composable(GlobalNavPage.Main) {
                                MainPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.MainMineSocietyListPage) {
                                MineSocietyList(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.MainMineSocietyRequestListPage) {
                                MineSocietyRequestListPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.MainMinePostListPage) {
                                MinePostListPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.MainMinePostReplyListPage) {
                                MinePostReplyListPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.MainMineInfoEditorPage) {
                                MineInfoEditor(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.MainMinePicListPage) {
                                MinePicList(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.MainMineNotifyListPage) {
                                MineNotifyListPage(requestHolder = this@MainActivity)
                            }

                            composable(GlobalNavPage.SocietyChatInnerPage) {
                                SocietyChatInnerPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.SocietyMemberListPage) {
                                SocietyMemberListPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.SocietyMemberDetailPage) {
                                SocietyMemberDetailPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.SocietyActivityListPage) {
                                SocietyActivityListPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.SocietyActivityRequestListPage) {
                                SocietyActivityRequestListPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.SocietyActivityDetailPage) {
                                SocietyActivityDetailPage(requestHolder = this@MainActivity)
                            }
                            composable(GlobalNavPage.SocietyPictureListPage) {
                                SocietyPictureListPage(requestHolder = this@MainActivity)
                            }

                            composable(GlobalNavPage.SocietyInfoEditorPage){
                                SocietyInfoEditor(requestHolder = this@MainActivity)
                            }
                        }

                        MyAlertDialog(alertRequest)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(onSIOServiceEventReceiver)
    }

    private fun NavGraphBuilder.composable(
        globalNavPage: GlobalNavPage,
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        composable(globalNavPage.route, content = content)
    }
}

