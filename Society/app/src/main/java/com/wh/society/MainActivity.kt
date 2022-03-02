package com.wh.society

import android.content.*
import android.os.Bundle
import android.text.util.Linkify
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
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
import com.wh.society.ui.componment.NavHost
import com.wh.society.ui.page.detail.*
import com.wh.society.ui.page.main.mine.*
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
    lateinit var globalNavController: NavHostController
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
                                navController = globalNavController,
                                pageArray = GlobalNavPage.a(),
                                startPage = GlobalNavPage.LoginPage,
                                requestHolder = this
                            )

                        MyAlertDialog(alertRequest)
                    }
                }
            }
        }
    }
}

