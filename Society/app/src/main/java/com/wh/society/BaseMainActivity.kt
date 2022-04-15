package com.wh.society

import android.content.ContentResolver
import android.os.Bundle
import android.text.util.Linkify
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.ImageLoader
import com.wh.society.componment.OperatePlatform
import com.wh.society.componment.RequestHolder
import com.wh.society.componment.ViewModelFactory
import com.wh.society.componment.request.AlertRequestCompact
import com.wh.society.componment.request.DataTrans
import com.wh.society.componment.request.ImagePickerRequest
import com.wh.society.componment.request.ToastRequest
import com.wh.society.componment.SettingStore
import com.wh.society.util.ActivityOpener
import com.wh.society.util.SystemUtil
import com.wh.society.viewModel.ApiViewModel
import com.wh.society.viewModel.NotifyViewModel
import io.noties.markwon.Markwon
import io.noties.markwon.image.coil.CoilImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin

abstract class BaseMainActivity : AppCompatActivity(), RequestHolder {

    override val settingStore: SettingStore by lazy { SettingStore.instance(this) }

    private val viewModelFactory by lazy {
        ViewModelFactory(
            settingStore = SettingStore(this),
            application = application as App
        )
    }
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
    override val toast: ToastRequest by lazy {
        ToastRequest().also {
            it.toast = { it ->
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
    override val markdown: Markwon by lazy {
        Markwon.builder(this)
            .usePlugin(CoilImagesPlugin.create(this, coilImageLoader))
            .usePlugin(LinkifyPlugin.create(Linkify.WEB_URLS))
            .build()
    }



    override val deviceName: String
        get() {
            return if (settingStore.deviceName == "") {
                SystemUtil.getDeviceModel()
            } else {
                settingStore.deviceName
            }
        }

    override val alert: AlertRequestCompact by lazy {
        AlertRequestCompact(this.resources)
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


    }

    override fun onDestroy() {
        super.onDestroy()
        operatePlatform.unreg(this)
    }
}

