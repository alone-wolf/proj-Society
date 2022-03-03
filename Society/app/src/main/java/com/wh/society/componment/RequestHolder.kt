package com.wh.society.componment

import android.content.ContentResolver
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import coil.ImageLoader
import com.wh.society.BaseMainActivity
import com.wh.society.api.data.*
import com.wh.society.api.data.society.*
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.componment.request.*
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.store.SettingStore
import com.wh.society.viewModel.ApiViewModel
import com.wh.society.viewModel.NotifyViewModel
import io.noties.markwon.Markwon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface RequestHolder {

    val activity: BaseMainActivity
    val operatePlatform: OperatePlatform

    val apiViewModel: ApiViewModel
    val notifyViewModel: NotifyViewModel

    val societyList: List<Society>
    val collegeList: ReturnListData<College>
    val bbsList: List<BBS>

    val settingStore: SettingStore

    val coroutineScope: CoroutineScope

    val deviceName: String


    val globalNav: GlobalNavRequest
    val trans: DataTrans

    val alert: AlertRequestCompact
    val imagePicker: ImagePickerRequest

    val myContentResolver: ContentResolver
    val markdown: Markwon
    val coilImageLoader: ImageLoader

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    fun loginLogic() {
        if (settingStore.phoneStudentIdEmail.isNotBlank() && settingStore.password.isNotBlank()) {
            apiViewModel.userLogin(
                settingStore.phoneStudentIdEmail,
                settingStore.password
            ) { loginReturn ->

                sioService.start(loginReturn.userId,loginReturn.cookieToken)

                apiViewModel.userInfo { userInfo ->

                    settingStore.autoLogin = true

                    coroutineScope.launch {
                        delay(500)
                        globalNav.goto(GlobalNavPage.Main)
                    }

                }
                apiViewModel.societyList()
                apiViewModel.picList()
            }
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    fun logoutLogic() {
        sioService.stop()

        apiViewModel.userLogout {
            settingStore.autoLogin = false

            globalNav.gotoWithBack(GlobalNavPage.LoginPage)
            apiViewModel.userInfo = ReturnObjectData.blank()
            apiViewModel.societyList = ReturnListData.blank()
            apiViewModel.bbsList = emptyList()
            apiViewModel.collegeList = ReturnListData.blank()
            apiViewModel.loginToken = ReturnObjectData.blank()
            apiViewModel.picDataList = ReturnListData.blank()

            trans.clear()
        }
    }

    val sioService: SioServiceRequest
        get() = SioServiceRequest(activity)

}

