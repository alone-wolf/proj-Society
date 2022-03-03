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
import com.wh.society.componment.request.*
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.store.SettingStore
import com.wh.society.ui.componment.MyAlertDialog
import com.wh.society.ui.componment.NavHost
import com.wh.society.ui.theme.SocietyTheme
import com.wh.society.util.ActivityOpener
import com.wh.society.util.SystemUtil
import com.wh.society.viewModel.ApiViewModel
import com.wh.society.viewModel.NotifyViewModel
import io.noties.markwon.Markwon
import io.noties.markwon.image.coil.CoilImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import kotlinx.coroutines.CoroutineScope

abstract class BaseMainActivity : AppCompatActivity(), RequestHolder {

    private val viewModelFactory by lazy { (application as App).viewModelFactory }
    override val apiViewModel: ApiViewModel by viewModels { viewModelFactory }
    override val notifyViewModel: NotifyViewModel by viewModels { viewModelFactory }

    override val activity: BaseMainActivity by lazy { this }

    override val trans: DataTrans = DataTrans()
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


    override lateinit var deviceName: String

    override val alert: AlertRequestCompact by lazy {
        AlertRequestCompact(this.resources, this)
    }

    override val operatePlatform: OperatePlatform = OperatePlatform()
    override lateinit var imagePicker: ImagePickerRequest

    override val myContentResolver: ContentResolver by lazy { this.contentResolver }


    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        operatePlatform.reg(this)

        imagePicker = ImagePickerRequest(
            imagePicker = ActivityOpener.imagePickerActivity(activity = this, requestHolder = this),
            requestHolder = this
        )

        deviceName = SystemUtil.getDeviceModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        operatePlatform.unreg(this)
    }
}

