package com.wh.society.componment

import android.content.ContentResolver
import android.content.res.Resources
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavController
import coil.ImageLoader
import com.wh.society.MainActivity
import com.wh.society.api.data.*
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.store.SettingStore
import com.wh.society.ui.componment.RoundedTextFiled
import com.wh.society.viewModel.ApiViewModel
import com.wh.society.viewModel.NotifyViewModel
import io.noties.markwon.Markwon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface RequestHolder {

    val operatePlatform:OperatePlatform

    val apiViewModel: ApiViewModel
    val notifyViewModel:NotifyViewModel

    val societyList: List<Society>
    val collegeList: ReturnListData<College>
    val bbsList: List<BBS>

    val settingStore: SettingStore

    val coroutineScope: CoroutineScope

    val deviceName: String

    val globalNav: GlobalNavRequest
    val coilImageLoader: ImageLoader
    val markdown: Markwon

    var transSociety: Society
    var transBBS: BBS
    var transPost: Post?
    // used to store userInfo while
    var transUserInfo: UserInfo
    var transUserJoint: ReturnListData<SocietyJoint>
    var transPostList: ReturnListData<Post>
    var transPostReplyList: ReturnListData<PostReply>
    var transSocietyRequestList: ReturnListData<MemberRequest>
    var transSocietyJoint: SocietyJoint
    var transSocietyJointList: List<SocietyJoint>

    val alertRequest: AlertRequestCompact


    val imagePicker: ActivityResultLauncher<String>
    val myContentResolver:ContentResolver
    val activity:MainActivity

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
                        globalNav.gotoMain()
                    }

                }
                apiViewModel.societyList()
                apiViewModel.picList()
            }
        }
    }

    fun logoutLogic() {

        stopSocketIOService()

        apiViewModel.userLogout {
            settingStore.autoLogin = false

            globalNav.goBackLoginPage()
            apiViewModel.userInfo = ReturnObjectData.blank()
            apiViewModel.societyList = ReturnListData.blank()
            apiViewModel.bbsList = emptyList()
            apiViewModel.collegeList = ReturnListData.blank()
            apiViewModel.loginToken = ReturnObjectData.blank()
            apiViewModel.picDataList = ReturnListData.blank()
            transSociety = Society()
            transBBS = BBS()
            transPost = null
            transUserInfo = UserInfo()
            transPostList = ReturnListData.blank()
            transPostReplyList = ReturnListData.blank()
            transSocietyRequestList = ReturnListData.blank()
            transSocietyJoint = SocietyJoint()
            transSocietyJointList = emptyList()
        }
    }

    val startSocketIOService: (Int, String) -> Unit
    val stopSocketIOService:()->Unit

    abstract class GlobalNavRequest(
        private val navController: NavController,
        private val requestHolder: RequestHolder
    ) {

        fun goBack() {
            navController.popBackStack()
        }

        fun gotoMain() {
            navController.navigate(GlobalNavPage.Main.route) {
                navController.popBackStack()
            }
        }

        fun goBackLoginPage() {
            navController.navigate(GlobalNavPage.LoginPage.route) {
                navController.popBackStack()
            }
        }

        fun gotoRegister() {
            navController.navigate(GlobalNavPage.RegisterPage.route)
        }

        fun gotoFindPassword() {
            navController.navigate(GlobalNavPage.FindPasswordPage.route)
        }

        fun gotoSetting() {
            navController.navigate(GlobalNavPage.Setting.route)
        }

        fun gotoUserInfo(userInfo: UserInfo) {
            requestHolder.transUserInfo = userInfo
            navController.navigate(GlobalNavPage.DetailUserInfo.route)
        }
        fun gotoUserPrivateChat(){
            navController.navigate(GlobalNavPage.UserChatPrivate.route)
        }

        fun gotoMainMineInfoEditor() {
            navController.navigate(GlobalNavPage.MainMineInfoEditorPage.route)
        }

        fun gotoMainMineSocietyList(userJointList: ReturnListData<SocietyJoint>) {
            requestHolder.transUserJoint = userJointList
            navController.navigate(GlobalNavPage.MainMineSocietyListPage.route)
        }

        fun gotoMainMineSocietyRequestList(memberRequestList: ReturnListData<MemberRequest>) {
            requestHolder.transSocietyRequestList = memberRequestList
            navController.navigate(GlobalNavPage.MainMineSocietyRequestListPage.route)
        }

        fun gotoMainMinePostList(postList: ReturnListData<Post>) {
            requestHolder.transPostList = postList
            navController.navigate(GlobalNavPage.MainMinePostListPage.route)
        }

        fun gotoMainMinePostReplyList(postReplyList: ReturnListData<PostReply>) {
            requestHolder.transPostReplyList = postReplyList
            navController.navigate(GlobalNavPage.MainMinePostReplyListPage.route)
        }

        fun gotoMainMinePicList() {
            navController.navigate(GlobalNavPage.MainMinePicListPage.route)
        }

        fun gotoMainMineNotifyList(){
            navController.navigate(GlobalNavPage.MainMineNotifyListPage.route)
        }

        fun gotoDetailSociety(society: Society, addBack: Boolean = false) {
            requestHolder.transSociety = society
            navController.navigate(GlobalNavPage.DetailSociety.route) {
                if (addBack) {
                    navController.popBackStack()
                }
            }
        }

        fun gotoDetailBBS(bbs: BBS, addBack: Boolean = false) {
            requestHolder.transBBS = bbs
            navController.navigate(GlobalNavPage.DetailBBS.route) {
//                if (addBack) {
//                    navController.popBackStack()
//                }
            }
        }

        fun gotoBBSPostDetail(post: Post) {
            requestHolder.transPost = post
            navController.navigate(GlobalNavPage.DetailPost.route)
        }

        fun gotoBBSPostEditor(post: Post? = null) {
            requestHolder.transPost = post
            navController.navigate(GlobalNavPage.DetailPostEditor.route)
        }

        fun gotoSocietyChatInner(society: Society) {
            requestHolder.transSociety = society
            navController.navigate(GlobalNavPage.SocietyChatInnerPage.route)
        }

        fun gotoSocietyMemberList(societyJoint: List<SocietyJoint>) {
            requestHolder.transSocietyJointList = societyJoint
            navController.navigate(GlobalNavPage.SocietyMemberListPage.route)
        }

        fun gotoSocietyMemberDetail(societyJoint: SocietyJoint) {
            requestHolder.transSocietyJoint = societyJoint
            navController.navigate(GlobalNavPage.SocietyMemberDetailPage.route)
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

}

