package com.wh.society.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
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

sealed class MainNavPage(val label: String, val route: String, val icon: ImageVector) {

    object Society : MainNavPage(
        label = "Society",
        route = "society",
        icon = Icons.Default.List
    )

    object BBS : MainNavPage(
        label = "BBS",
        route = "bbs",
        icon = Icons.Default.List
    )

    object Mine : MainNavPage(
        label = "Mine",
        route = "mine",
        icon = Icons.Default.Person
    )

    companion object {
        val navMap = mapOf(
            Mine.route to Mine,
            Society.route to Society,
            BBS.route to BBS,
        )
    }
}

sealed class GlobalNavPage(
    val label: String,
    val route: String,
    val content: @Composable (RequestHolder) -> Unit,
    val navExtraOperation: (r: RequestHolder, a: Any) -> Unit = { _, _ -> }
) {
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    object LoginPage : GlobalNavPage(
        "Login Page",
        "login/login-page",
        { LoginPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object RegisterPage : GlobalNavPage(
        "Register Page",
        "login/register-page",
        { RegisterPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object FindPasswordPage : GlobalNavPage(
        "Find Password",
        "login/find-password",
        { FindPasswordPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object Setting : GlobalNavPage(
        "Setting",
        "setting",
        { SettingPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object DetailUserInfo : GlobalNavPage(
        "User Info",
        "detail/user/info",
        { UserDetailPage(requestHolder = it) },
        { r, a -> r.trans.userInfo = a as UserInfo }
    )

    @ExperimentalMaterialApi
    object UserChatPrivate : GlobalNavPage(
        "User Private Chat",
        "user/chat/private",
        { UserChatPrivatePage(requestHolder = it) }
    )

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    object DetailSociety : GlobalNavPage(
        "Society Detail",
        "detail/society",
        { SocietyDetailPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    object DetailBBS : GlobalNavPage(
        "BBS Detail",
        "detail/bbs",
        { BBSDetailPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object DetailPost : GlobalNavPage(
        "Post Detail",
        "detail/bbs/post",
        { BBSPostDetail(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object DetailPostEditor : GlobalNavPage(
        "Post Editor",
        "detail/bbs/post/editor",
        { BBSPostEditor(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    object Main : GlobalNavPage(
        "Main",
        "main",
        { MainPage(requestHolder = it) }
    )

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    object MainMineSocietyListPage : GlobalNavPage(
        "Society You Join",
        "main/mine/societyList",
        { MineSocietyList(requestHolder = it) },
    )

    @ExperimentalMaterialApi
    object MainMineSocietyRequestListPage : GlobalNavPage(
        "Society Join Request",
        "main/mine/societyRequestList",
        { MineSocietyRequestListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object MainMinePostListPage : GlobalNavPage(
        "Your Posts",
        "main/mine/postList",
        { MinePostListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object MainMinePostReplyListPage : GlobalNavPage(
        "Your Replies",
        "main/mine/postReplyList",
        { MinePostReplyListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object MainMineInfoEditorPage : GlobalNavPage(
        "Mine Info Editor",
        "main/mine/info/editor",
        { MineInfoEditor(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object MainMinePicListPage : GlobalNavPage(
        "Uploaded Pictures",
        "main/mine/pic",
        { MinePicList(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object MainMineNotifyListPage : GlobalNavPage(
        "Notify List",
        "main/mine/notify",
        { MineNotifyListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    object SocietyChatInnerPage : GlobalNavPage(
        "Society Inner Chat",
        "detail/society/chat/inner",
        { SocietyChatInnerPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object SocietyMemberListPage : GlobalNavPage(
        "Society Members",
        "detail/society/member/list",
        { SocietyMemberListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object SocietyMemberDetailPage : GlobalNavPage(
        "Society Member",
        "detail/society/member",
        { SocietyMemberDetailPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object SocietyActivityListPage : GlobalNavPage(
        "Society Activity List",
        "detail/society/activity/list",
        { SocietyActivityListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object SocietyActivityRequestListPage : GlobalNavPage(
        "Society Activity Request List",
        "detail/society/activity/request/list",
        { SocietyActivityRequestListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object SocietyActivityDetailPage : GlobalNavPage(
        "Society Activity Detail",
        "detail/society/activity/detail",
        { SocietyActivityDetailPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object SocietyPictureListPage : GlobalNavPage(
        "Society Picture List",
        "detail/society/picture/list",
        { SocietyPictureListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object SocietyInfoEditorPage : GlobalNavPage(
        "Society Info Editor",
        "detail/society/info/editor",
        { SocietyInfoEditor(requestHolder = it) }
    )


    companion object {

        @ExperimentalAnimationApi
        @ExperimentalMaterialApi
        fun a(): Array<GlobalNavPage> {
            return GlobalNavPage::class.nestedClasses
                .filter { !it.isCompanion }
                .map { it.objectInstance as GlobalNavPage }
                .toTypedArray()
        }
    }
}

