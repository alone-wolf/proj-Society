package com.wh.society.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.*
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.society.bbs.PostReply
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.ui.page.detail.user.UserDetailPage
import com.wh.society.ui.page.detail.society.SocietyDetailPage
import com.wh.society.ui.page.detail.society.SocietyInfoEditor
import com.wh.society.ui.page.detail.society.activity.SocietyActivityDetailPage
import com.wh.society.ui.page.detail.society.activity.SocietyActivityListPage
import com.wh.society.ui.page.detail.society.bbs.BBSDetailPage
import com.wh.society.ui.page.detail.society.bbs.BBSPostDetail
import com.wh.society.ui.page.detail.society.bbs.BBSPostEditor
import com.wh.society.ui.page.detail.society.chat.SocietyChatInnerPage
import com.wh.society.ui.page.detail.society.member.SocietyMemberDetailPage
import com.wh.society.ui.page.detail.society.member.SocietyMemberListPage
import com.wh.society.ui.page.detail.society.member.request.SocietyMemberRequestListPage
import com.wh.society.ui.page.detail.society.notice.SocietyNoticeListPage
import com.wh.society.ui.page.detail.society.picture.SocietyPictureListPage
import com.wh.society.ui.page.detail.user.UserChatPrivatePage
import com.wh.society.ui.page.login.FindPasswordPage
import com.wh.society.ui.page.login.LoginPage
import com.wh.society.ui.page.login.RegisterPage
import com.wh.society.ui.page.main.MainPage
import com.wh.society.ui.page.main.mine.*
import com.wh.society.ui.page.setting.SettingPage

sealed class MainNavPage(val title: String, val route: String, val icon: ImageVector) {

    object Society : MainNavPage(
        title = "??????",
        route = "society",
        icon = Icons.Default.List
    )

    object BBS : MainNavPage(
        title = "??????",
        route = "bbs",
        icon = Icons.Default.List
    )

    object Mine : MainNavPage(
        title = "??????",
        route = "mine",
        icon = Icons.Default.Person
    )

    companion object {
        val navMap = mapOf(
            Mine.route to Mine,
            Society.route to Society,
            BBS.route to BBS,
        )
        val navMapNoBBS = mapOf(
            Mine.route to Mine,
            Society.route to Society,
        )
    }
}

sealed class GlobalNavPage(
    val title: String,
    val route: String,
    val content: @Composable (RequestHolder) -> Unit,
    val navExtraOperation: (r: RequestHolder, a: Any) -> Unit = { _, _ -> },
) {

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    object LoginPage : GlobalNavPage(
        "??????",
        "login/login-page",
        { LoginPage(requestHolder = it) },
    )

    @ExperimentalMaterialApi
    object RegisterPage : GlobalNavPage(
        "????????????",
        "login/register-page",
        { RegisterPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object FindPasswordPage : GlobalNavPage(
        "????????????",
        "login/find-password",
        { FindPasswordPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object Setting : GlobalNavPage(
        "??????",
        "setting",
        { SettingPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object DetailUserInfo : GlobalNavPage(
        "????????????",
        "detail/user/info",
        { UserDetailPage(requestHolder = it) },
        { r, a -> r.trans.userInfo = a as UserInfo }
    )

    @ExperimentalMaterialApi
    object UserChatPrivate : GlobalNavPage(
        "????????????",
        "user/chat/private",
        { UserChatPrivatePage(requestHolder = it) }
    )

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    object DetailSociety : GlobalNavPage(
        "????????????",
        "detail/society",
        { SocietyDetailPage(requestHolder = it) },
        { r, a -> r.trans.society = a as Society }
    )

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    object DetailBBS : GlobalNavPage(
        "????????????",
        "detail/bbs",
        { BBSDetailPage(requestHolder = it) },
        { r, a -> r.trans.bbs = a as BBS }
    )

    @ExperimentalMaterialApi
    object DetailPost : GlobalNavPage(
        "????????????",
        "detail/bbs/post",
        { BBSPostDetail(requestHolder = it) },
        { r, a -> r.trans.postId = a as Int }
    )

    @ExperimentalMaterialApi
    object DetailPostEditor : GlobalNavPage(
        "???????????????",
        "detail/bbs/post/editor",
        { BBSPostEditor(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    object Main : GlobalNavPage(
        "?????????",
        "main",
        { MainPage(requestHolder = it) }
    )

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    object MainMineSocietyListPage : GlobalNavPage(
        "???????????????",
        "main/mine/societyList",
        { MineSocietyList(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object MainMineSocietyRequestListPage : GlobalNavPage(
        "??????????????????",
        "main/mine/societyRequestList",
        { MineSocietyRequestListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object MainMinePostListPage : GlobalNavPage(
        "???????????????",
        "main/mine/postList",
        { MinePostListPage(requestHolder = it) },
        { r, a -> r.trans.postList = a as ReturnListData<Post> }
    )

    @ExperimentalMaterialApi
    object MainMinePostReplyListPage : GlobalNavPage(
        "???????????????",
        "main/mine/postReplyList",
        { MinePostReplyListPage(requestHolder = it) },
        { r, a -> r.trans.postReplyList = a as ReturnListData<PostReply> }
    )

    @ExperimentalMaterialApi
    object MainMineInfoEditorPage : GlobalNavPage(
        "?????????????????????",
        "main/mine/info/editor",
        { MineInfoEditor(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object MainMinePicListPage : GlobalNavPage(
        "???????????????",
        "main/mine/pic",
        { MinePicList(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object MainMineNotifyListPage : GlobalNavPage(
        "????????????",
        "main/mine/notify",
        { MineNotifyListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    object SocietyChatInnerPage : GlobalNavPage(
        "?????????????????????",
        "detail/society/chat/inner",
        { SocietyChatInnerPage(requestHolder = it) },
        { r, a -> r.trans.society = a as Society }
    )

    @ExperimentalMaterialApi
    object SocietyMemberListPage : GlobalNavPage(
        "????????????",
        "detail/society/member/list",
        { SocietyMemberListPage(requestHolder = it) },
        { r, a -> r.trans.societyMemberList = a as ReturnListData<SocietyMember> }
    )

    @ExperimentalMaterialApi
    object SocietyMemberDetailPage : GlobalNavPage(
        "??????????????????",
        "detail/society/member",
        { SocietyMemberDetailPage(requestHolder = it) },
        { r, a -> r.trans.societyMember = a as SocietyMember }
    )

    @ExperimentalMaterialApi
    object SocietyActivityListPage : GlobalNavPage(
        "??????????????????",
        "detail/society/activity/list",
        { SocietyActivityListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object SocietyActivityDetailPage : GlobalNavPage(
        "??????????????????",
        "detail/society/activity/detail",
        { SocietyActivityDetailPage(requestHolder = it) },
        { r, a -> r.trans.societyActivity = a as SocietyActivity }
    )

    @ExperimentalMaterialApi
    object SocietyPictureListPage : GlobalNavPage(
        "??????????????????",
        "detail/society/picture/list",
        { SocietyPictureListPage(requestHolder = it) },
        { r, a -> r.trans.societyPictureList = a as ReturnListData<SocietyPicture> }
    )

    @ExperimentalMaterialApi
    object SocietyInfoEditorPage : GlobalNavPage(
        "?????????????????????",
        "detail/society/info/editor",
        { SocietyInfoEditor(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object SocietyNoticeListPage : GlobalNavPage(
        "??????????????????",
        "detail/society/notice/list",
        { SocietyNoticeListPage(requestHolder = it) }
    )

    @ExperimentalMaterialApi
    object SocietyMemberRequestListPage : GlobalNavPage(
        "????????????????????????",
        "detail/society/member/request/list",
        { SocietyMemberRequestListPage(requestHolder = it) },
        { r, a -> r.trans.societyMemberRequestList = a as ReturnListData<SocietyMemberRequest> }
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

        @OptIn(
            ExperimentalAnimationApi::class,
            ExperimentalMaterialApi::class
        )
        val aa = a()
    }
}

