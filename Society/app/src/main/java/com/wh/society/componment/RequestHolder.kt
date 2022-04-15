package com.wh.society.componment

import android.content.ContentResolver
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.wh.society.BaseMainActivity
import com.wh.society.api.data.*
import com.wh.society.api.data.society.*
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.request.*
import com.wh.society.navigation.GlobalNavPage
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
    val mainNavController: NavHostController
    val trans: DataTrans

    val alert: AlertRequestCompact
    val imagePicker: ImagePickerRequest
    val toast: ToastRequest

    val myContentResolver: ContentResolver
    val markdown: Markwon
    val coilImageLoader: ImageLoader

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    fun loginLogic() {
        if (settingStore.phoneStudentIdEmail.isNotBlank() && settingStore.password.isNotBlank()) {
            apiViewModel.userLogin(
                phoneStudentIdEmail = settingStore.phoneStudentIdEmail,
                password = settingStore.password,
                onError = toast.toast
            ) { loginReturn ->

                settingStore.token = loginReturn.cookieToken

                if (settingStore.receivePush) {
                    sioService.start(loginReturn.userId, loginReturn.cookieToken)
                }

                apiViewModel.userInfo { userInfo ->

                    settingStore.autoLogin = true

                    coroutineScope.launch {
                        delay(500)
                        globalNav.goto(GlobalNavPage.Main)
                    }

                }
                apiViewModel.societyList(onError = {
                })
                apiViewModel.picList()
            }
        } else {
            toast.toast("请输入 手机号/邮箱 和 密码")
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    fun logoutLogic() {
        sioService.stop()

        apiViewModel.userLogout {
            settingStore.autoLogin = false

            globalNav.gotoWithBack(GlobalNavPage.LoginPage)
            apiViewModel.userInfo = UserInfo()
            apiViewModel.societyList = emptyList()
            apiViewModel.bbsList = emptyList()
            apiViewModel.collegeList = ReturnListData.blank()
            apiViewModel.loginToken = ReturnObjectData.blank()
            apiViewModel.picDataList = ReturnListData.blank()

            settingStore.token = ""

            trans.clear()
        }
    }

    val sioService: SioServiceRequest
        get() = SioServiceRequest(activity)

}

