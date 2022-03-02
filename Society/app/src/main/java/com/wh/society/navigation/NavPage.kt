package com.wh.society.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.wh.society.navigation.MainNavPage.*

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

sealed class GlobalNavPage(val label: String, val route: String) {
    object LoginPage : GlobalNavPage(
        "Login Page",
        "login/login-page"
    )

    object RegisterPage : GlobalNavPage(
        "Register Page",
        "login/register-page"
    )

    object FindPasswordPage : GlobalNavPage(
        "Find Password",
        "login/find-password"
    )

    object Setting : GlobalNavPage(
        "Setting",
        "setting"
    )

    object DetailUserInfo : GlobalNavPage(
        "User Info",
        "detail/user/info"
    )

    object UserChatPrivate : GlobalNavPage(
        "User Private Chat",
        "user/chat/private"
    )

    object DetailSociety : GlobalNavPage(
        "Society Detail",
        "detail/society"
    )

    object DetailBBS : GlobalNavPage(
        "BBS Detail",
        "detail/bbs"
    )

    object DetailPost : GlobalNavPage(
        "Post Detail",
        "detail/bbs/post"
    )

    object DetailPostEditor : GlobalNavPage(
        "Post Editor",
        "detail/bbs/post/editor"
    )

    object Main : GlobalNavPage(
        "Main",
        "main"
    )

    object MainMineSocietyListPage : GlobalNavPage(
        "Society You Join",
        "main/mine/societyList"
    )

    object MainMineSocietyRequestListPage : GlobalNavPage(
        "Society Join Request",
        "main/mine/societyRequestList"
    )

    object MainMinePostListPage : GlobalNavPage(
        "Your Posts",
        "main/mine/postList"
    )

    object MainMinePostReplyListPage : GlobalNavPage(
        "Your Replies",
        "main/mine/postReplyList"
    )

    object MainMineInfoEditorPage : GlobalNavPage(
        "Mine Info Editor",
        "main/mine/info/editor"
    )

    object MainMinePicListPage : GlobalNavPage(
        "Uploaded Pictures",
        "main/mine/pic"
    )

    object MainMineNotifyListPage : GlobalNavPage(
        "Notify List",
        "main/mine/notify"
    )

    object SocietyChatInnerPage : GlobalNavPage(
        "Society Inner Chat",
        "detail/society/chat/inner"
    )

    object SocietyMemberListPage : GlobalNavPage(
        "Society Members",
        "detail/society/member/list"
    )

    object SocietyMemberDetailPage : GlobalNavPage(
        "Society Member",
        "detail/society/member"
    )

    object SocietyActivityListPage : GlobalNavPage(
        "Society Activity List",
        "detail/society/activity/list"
    )

    object SocietyActivityRequestListPage : GlobalNavPage(
        "Society Activity Request List",
        "detail/society/activity/request/list"
    )

    object SocietyActivityDetailPage : GlobalNavPage(
        "Society Activity Detail",
        "detail/society/activity/detail"
    )

    object SocietyPictureListPage : GlobalNavPage(
        "Society Picture List",
        "detail/society/picture/list"
    )

    object SocietyInfoEditorPage:GlobalNavPage(
        "Society Info Editor",
        "detail/society/info/editor"
    )
//    object Detail
}

