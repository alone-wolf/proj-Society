package com.wh.admin

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.wh.admin.ui.page.*

sealed class NavDes(
    val route: String,
    val title: String,
    val content: @Composable (MainActivity) -> Unit = {},
) {
    object Main : NavDes(
        "main",
        "社团·管理端",
        { MainPage(activity = it) }
    )

    object UserDetail : NavDes(
        "user-detail",
        "用户详情",
        { UserDetailPage(activity = it) }
    )

    object UserPostList : NavDes(
        "user-post-list",
        "用户发帖列表",
        { UserPostListPage(activity = it) }
    )

    object UserReplyList : NavDes(
        "user-reply-list",
        "用户回复列表",
        { UserReplyListPage(activity = it) }
    )

    object UserSocietyMemberList : NavDes(
        "user-society-member-list",
        "用户加入的社团",
        { UserSocietyMemberListPage(activity = it) }
    )

    object SocietyDetail : NavDes(
        "society-detail",
        "社团详情",
        { SocietyDetailPage(activity = it) }
    )

    object PostDetail : NavDes(
        "post-detail",
        "帖子详情",
        { PostDetailPage(activity = it) }
    )

    object UserCreator : NavDes(
        "user-creator",
        "创建用户",
        { UserCreatorPage(activity = it) }
    )

    object UserEditor : NavDes(
        "user-editor",
        "编辑用户",
        { UserEditorPage(activity = it) }
    )

    object UserSocietyJoiner : NavDes(
        "user-society-joiner",
        "用户加入社团（只显示未加入的社团）",
        { UserSocietyJoinerPage(activity = it) }
    )

    object UserSocietyMemberRequestList : NavDes(
        "user-society-member-request-list",
        "用户社团加入申请",
        { UserSocietyMemberRequestListPage(activity = it) }
    )

    object UserSocietyActivityMemberList:NavDes(
        "user-society-activity-member-list",
        "用户加入的社团活动",
        {UserSocietyActivityMemberListPage(activity = it)}
    )

    object SocietyCreator : NavDes(
        "society-creator",
        "创建社团",
        {SocietyCreatorPage(activity = it)}
    )

    object SocietyEditor : NavDes(
        "society-editor",
        "编辑社团",
        { SocietyEditorPage(activity = it) }
    )

    object SocietyPostList : NavDes(
        "society-post-list",
        "社团帖子列表",
        { SocietyPostListPage(activity = it) }
    )

    object SocietyMemberList : NavDes(
        "society-member-list",
        "社团用户列表",
        { SocietyMemberListPage(activity = it) }
    )

    object SocietyActivityList : NavDes(
        "society-activity-list",
        "社团活动列表",
        { SocietyActivityListPage(activity = it) }
    )

    companion object {

        @ExperimentalAnimationApi
        @ExperimentalMaterialApi
        fun a(): Array<NavDes> {
            return NavDes::class.nestedClasses
                .filter { !it.isCompanion }
                .map { it.objectInstance as NavDes }
                .toTypedArray()
        }

        @ExperimentalMaterialApi
        @OptIn(ExperimentalAnimationApi::class)
        val aa = a()
    }
}