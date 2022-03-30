package com.wh.admin.componment

import com.wh.admin.MainActivity
import com.wh.admin.NavDes
import com.wh.admin.data.society.Society
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.data.user.UserInfo
import com.wh.admin.ext.goto

class NavRequest(private val activity: MainActivity){
    private val navController = activity.navController
    val navBack:()->Unit = {
        navController.popBackStack()
    }

    val navToUserDetail = { u: UserInfo ->
        activity.selectedUserInfo = u
        navController.goto(NavDes.UserDetail)
    }

    val navToUserPostList = {
        navController.goto(NavDes.UserPostList)
    }

    val navToUserReplyList = {
        navController.goto(NavDes.UserReplyList)
    }

    val navToUserSocietyMemberList = {
        navController.goto(NavDes.UserSocietyMemberList)
    }

    val navToSocietyDetail = { s: Society ->
        activity.selectedSociety = s
        navController.goto(NavDes.SocietyDetail)
    }


    val navToPostDetail = { p: Post ->
        activity.selectedPost = p
        navController.goto(NavDes.PostDetail)
    }

    val navToUserCreator = {
        navController.goto(NavDes.UserCreator)
    }

    val navToSocietyCreator = {
        navController.goto(NavDes.SocietyCreator)
    }
}