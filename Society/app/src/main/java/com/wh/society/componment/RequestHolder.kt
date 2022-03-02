package com.wh.society.componment

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavController
import coil.ImageLoader
import com.wh.society.MainActivity
import com.wh.society.api.data.*
import com.wh.society.api.data.society.*
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.society.bbs.PostReply
import com.wh.society.api.data.user.UserInfo
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.store.SettingStore
import com.wh.society.ui.componment.RoundedTextFiled
import com.wh.society.viewModel.ApiViewModel
import com.wh.society.viewModel.NotifyViewModel
import io.noties.markwon.Markwon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

interface RequestHolder {

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
    val coilImageLoader: ImageLoader
    val markdown: Markwon

    val trans: DataTrans

    val alertRequest: AlertRequestCompact

    val imagePicker: ImagePickerRequest
    val myContentResolver: ContentResolver
    val activity: MainActivity

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    fun loginLogic() {
        if (settingStore.phoneStudentIdEmail.isNotBlank() && settingStore.password.isNotBlank()) {
            apiViewModel.userLogin(
                settingStore.phoneStudentIdEmail,
                settingStore.password
            ) { loginReturn ->

                startSocketIOService(loginReturn.userId, loginReturn.cookieToken)

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

        stopSocketIOService()

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

    val startSocketIOService: (Int, String) -> Unit
    val stopSocketIOService: () -> Unit

    abstract class GlobalNavRequest(
        private val navController: NavController,
        private val requestHolder: RequestHolder
    ) {

        fun goBack() {
            navController.popBackStack()
        }

        fun goto(page:GlobalNavPage){
            navController.navigate(page.route)
        }

        fun <T>goto(page: GlobalNavPage,a:Any){
            page.navExtraOperation(requestHolder, (a as T)!!)
            navController.navigate(page.route)
        }

        fun gotoWithBack(page: GlobalNavPage){
            navController.navigate(page.route){
                navController.popBackStack()
            }
        }

        @ExperimentalMaterialApi
        fun gotoUserInfo(userInfo: UserInfo) {
            requestHolder.trans.userInfo = userInfo
            navController.navigate(GlobalNavPage.DetailUserInfo.route)
        }


        @ExperimentalAnimationApi
        @ExperimentalMaterialApi
        fun gotoMainMineSocietyList(userMemberList: ReturnListData<SocietyMember>) {
            requestHolder.trans.userMember = userMemberList
            navController.navigate(GlobalNavPage.MainMineSocietyListPage.route)
        }

        @ExperimentalMaterialApi
        fun gotoMainMineSocietyRequestList(societyMemberRequestList: ReturnListData<SocietyMemberRequest>) {
            requestHolder.trans.societyMemberRequestList = societyMemberRequestList
            navController.navigate(GlobalNavPage.MainMineSocietyRequestListPage.route)
        }

        @ExperimentalMaterialApi
        fun gotoMainMinePostList(postList: ReturnListData<Post>) {
            requestHolder.trans.postList = postList
            navController.navigate(GlobalNavPage.MainMinePostListPage.route)
        }

        @ExperimentalMaterialApi
        fun gotoMainMinePostReplyList(postReplyList: ReturnListData<PostReply>) {
            requestHolder.trans.postReplyList = postReplyList
            navController.navigate(GlobalNavPage.MainMinePostReplyListPage.route)
        }

        @ExperimentalAnimationApi
        @ExperimentalMaterialApi
        fun gotoDetailSociety(society: Society) {
            requestHolder.trans.society = society
            navController.navigate(GlobalNavPage.DetailSociety.route)
        }

        @ExperimentalAnimationApi
        @ExperimentalMaterialApi
        fun gotoDetailBBS(bbs: BBS) {
            requestHolder.trans.bbs = bbs
            navController.navigate(GlobalNavPage.DetailBBS.route) {
            }
        }

        @ExperimentalMaterialApi
        fun gotoBBSPostDetail(postId: Int) {
            requestHolder.trans.postId = postId
            navController.navigate(GlobalNavPage.DetailPost.route)
        }

        @ExperimentalAnimationApi
        @ExperimentalMaterialApi
        fun gotoSocietyChatInner(society: Society) {
            requestHolder.trans.society = society
            navController.navigate(GlobalNavPage.SocietyChatInnerPage.route)
        }

        @ExperimentalMaterialApi
        fun gotoSocietyMemberList(societyMember: ReturnListData<SocietyMember>) {
            requestHolder.trans.societyMemberList = societyMember
            navController.navigate(GlobalNavPage.SocietyMemberListPage.route)
        }

        @ExperimentalMaterialApi
        fun gotoSocietyMemberDetail(societyMember: SocietyMember) {
            requestHolder.trans.societyMember = societyMember
            navController.navigate(GlobalNavPage.SocietyMemberDetailPage.route)
        }

        @ExperimentalMaterialApi
        fun gotoSocietyActivityDetail(societyActivity: SocietyActivity) {
            requestHolder.trans.societyActivity = societyActivity
            navController.navigate(GlobalNavPage.SocietyActivityDetailPage.route)
        }

        @ExperimentalMaterialApi
        fun gotoSocietyPictureListPage(societyPictureList: ReturnListData<SocietyPicture>) {
            requestHolder.trans.societyPictureList = societyPictureList
            navController.navigate(GlobalNavPage.SocietyPictureListPage.route)
        }
    }

    abstract class AlertRequestCompact(
        private val resources: Resources,
        private val requestHolder: RequestHolder
    ) : AlertRequest(resources) {
        //        fun alertNewPost(bbs: BBS) {
//            super.alert(
//                title = "New Post",
//                content = {
//
//                },
//                onOk = {},
//                onCancel = {}
//            )
//        }
        var reply by mutableStateOf("")
        var text1 by mutableStateOf("")

        fun alertFor1TextFiled(title: String, onOk: (String) -> Unit) {
            super.alert(
                title = title,
                content = {
                    RoundedTextFiled(value = text1, onValueChange = { text1 = it })
                },
                onOk = { onOk(text1);text1 = "" },
                onCancel = { text1 = "" }
            )
        }


//        fun postPost(post: Post) {
//            super.alert("Confirm", "sure to post this?", onOk = {}, onCancel = {})
//        }
//
//        fun postPostReply(onOk: (String) -> Unit) {
//            super.alert("reply post", {
//                RoundedTextFiled(value = reply, onValueChange = { reply = it })
//            }, onOk = { onOk(reply);reply = "" }, { reply = "" })
//        }
    }

    abstract class AlertRequest(private val resources: Resources) {
        val show2BtnDialog: MutableState<Boolean> = mutableStateOf(false)
        val show1BtnDialog: MutableState<Boolean> = mutableStateOf(false)
        var title: String = ""
        var content: @Composable () -> Unit = {}
        var onOKAction: () -> Unit = {}
        var onCancelAction: () -> Unit = {}


        fun alert(
            title: String,
            content: @Composable () -> Unit,
            onOk: () -> Unit,
        ) {
            this.title = title
            this.content = content
            this.onOKAction = onOk
            this.show1BtnDialog.value = true
        }

        fun alert(
            title: String,
            content: @Composable () -> Unit,
            onOk: () -> Unit,
            onCancel: () -> Unit
        ) {
            this.title = title
            this.content = content
            this.onOKAction = onOk
            this.onCancelAction = onCancel
            this.show2BtnDialog.value = true
        }

        fun alert(title: String, content: String, onOk: () -> Unit, onCancel: () -> Unit = {}) {
            alert(title, { Text(text = content) }, onOk, onCancel)
        }

        fun alert(title: String, content: String, onOk: () -> Unit) {
            alert(title, { Text(text = content) }, onOk)
        }

        fun alert(
            @StringRes title: Int,
            @StringRes content: Int,
            onOk: () -> Unit,
            onCancel: () -> Unit = {}
        ) {
            alert(resources.getString(title), resources.getString(content), onOk, onCancel)
        }

        fun alert(
            @StringRes title: Int,
            @StringRes content: Int,
            onOk: () -> Unit = {},
        ) {
            alert(resources.getString(title), resources.getString(content), onOk)
        }

        fun alert(
            @StringRes title: Int,
            content: @Composable () -> Unit,
            onOk: () -> Unit,
            onCancel: () -> Unit = {}
        ) {
            alert(resources.getString(title), content, onOk, onCancel)
        }

        fun alert(
            @StringRes title: Int,
            content: @Composable () -> Unit,
            onOk: () -> Unit,
        ) {
            alert(resources.getString(title), content, onOk)
        }

        fun alert(
            @StringRes title: Int,
            content: String,
            onOk: () -> Unit,
            onCancel: () -> Unit = {}
        ) {
            alert(resources.getString(title), content, onOk, onCancel)
        }

        fun alert(
            @StringRes title: Int,
            content: String,
            onOk: () -> Unit,
        ) {
            alert(resources.getString(title), content, onOk)
        }
    }

    abstract class ImagePickerRequest(
        private val imagePicker: ActivityResultLauncher<String>,
        private val requestHolder: RequestHolder
    ) {
        var afterImagePick: (MultipartBody.Part) -> Unit = { i: MultipartBody.Part -> }

        fun forUser() {
            afterImagePick = { imageBodyPart ->
                requestHolder.apiViewModel.picCreate(imageBodyPart) {
                    requestHolder.apiViewModel.picList()
                }
            }
            imagePicker.launch("image/*")
        }

        @ExperimentalMaterialApi
        fun forSociety() {
            afterImagePick = { imageBodyPart ->
                requestHolder.apiViewModel.societyPictureCreate(
                    imageBodyPart,
                    requestHolder.trans.society.id
                ) {
                    if (requestHolder.operatePlatform.currentRoute == GlobalNavPage.SocietyPictureListPage.route) {
                        requestHolder.operatePlatform.currentOperate.invoke()
                    }
                }
            }
            imagePicker.launch("image/*")
        }

    }

    abstract class DataTrans {
        var society: Society = Society()
        var bbs: BBS = BBS()
        var userInfo = UserInfo()
        var postList = ReturnListData.blank<Post>()
        var postReplyList = ReturnListData.blank<PostReply>()
        var societyMemberRequestList = ReturnListData.blank<SocietyMemberRequest>()
        var societyMember = SocietyMember()
        var societyMemberList = ReturnListData.blank<SocietyMember>()
        var societyActivity = SocietyActivity()
        var postId = 0
        var societyPictureList = ReturnListData.blank<SocietyPicture>()
        var userMember = ReturnListData.blank<SocietyMember>()

        fun clear() {
            society = Society()
            bbs = BBS()
            userInfo = UserInfo()
            postList = ReturnListData.blank()
            postReplyList = ReturnListData.blank()
            societyMemberRequestList = ReturnListData.blank()
            societyMember = SocietyMember()
            societyMemberList = ReturnListData.blank()
            societyActivity = SocietyActivity()
            postId = 0
            societyPictureList = ReturnListData.blank()
            userMember = ReturnListData.blank()
        }
    }

}

