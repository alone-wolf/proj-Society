package com.wh.admin.componment

import com.wh.admin.MainActivity
import com.wh.admin.NavDes
import com.wh.admin.data.society.Society
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.data.user.UserInfo
import com.wh.admin.ext.goto

class NavRequest(private val activity: MainActivity) {
    private val navController = activity.navController
    val back: () -> Unit = {
        navController.popBackStack()
    }

    val toUserDetail = { u: UserInfo ->
        activity.selectedUserInfo = u
        navController.goto(NavDes.UserDetail)
    }

    val toUserPostList = {
        navController.goto(NavDes.UserPostList)
    }

    val toUserReplyList = {
        navController.goto(NavDes.UserReplyList)
    }

    val toUserSocietyMemberList = {
        navController.goto(NavDes.UserSocietyMemberList)
    }

    val toSocietyDetail = { s: Society ->
        activity.selectedSociety = s
        navController.goto(NavDes.SocietyDetail)
    }


    val toPostDetail = { p: Post ->
        activity.selectedPost = p
        navController.goto(NavDes.PostDetail)
    }

    val toUserCreator = {
        navController.goto(NavDes.UserCreator)
    }

    val toSocietyCreator = {
        navController.goto(NavDes.SocietyCreator)
    }

    val toUserSocietyJoiner = {
        navController.goto(NavDes.UserSocietyJoiner)
    }

    val toUserEditor = {
        navController.goto(NavDes.UserEditor)
    }

    val toUserSocietyMemberRequestList = {
        navController.goto(NavDes.UserSocietyMemberRequestList)
    }

    val toUserSocietyActivityMemberList = {
        navController.goto(NavDes.UserSocietyActivityMemberList)
    }

    val toSocietyEditor = {
        navController.goto(NavDes.SocietyEditor)
    }

    val toSocietyPostList = {
        navController.goto(NavDes.SocietyPostList)
    }

    val toSocietyMemberList = {
        navController.goto(NavDes.SocietyMemberList)
    }

    val toSocietyMemberRequestList = {
        navController.goto(NavDes.SocietyMemberRequestList)
    }

    val toSocietyActivityList = {
        navController.goto(NavDes.SocietyActivityList)
    }
}