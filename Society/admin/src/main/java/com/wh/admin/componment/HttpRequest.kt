package com.wh.admin.componment

import com.wh.admin.MainActivity
import com.wh.admin.data.society.SocietyMember
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.data.society.bbs.PostReply
import com.wh.admin.data.user.UserInfo
import kotlinx.coroutines.launch

class HttpRequest(activity: MainActivity) {
    private val coroutineScope = activity.coroutineScope
    private val scaffoldState = activity.scaffoldState
    private val serverDataViewModel = activity.serverDataViewModel

    private val snapBar: (String) -> Unit = { s: String ->
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(s, "Close")
        }
    }

    fun getAllUser() {
        coroutineScope.launch {
            serverDataViewModel.getAllUser(snapBar)
        }
    }

    fun getAllSociety() {
        coroutineScope.launch {
            serverDataViewModel.getAllSociety(snapBar)
        }
    }

    fun getAllSocietyPost(societyId: Int, onReturn: (List<Post>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.getAllSocietyPost(societyId, onReturn, snapBar)
        }
    }

    fun getAllUserPost(userId: Int, onReturn: (List<Post>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.getAllUserPost(userId, onReturn, snapBar)
        }
    }

    fun getAllUserReply(userId: Int, onReturn: (List<PostReply>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.getAllUserReply(userId, onReturn, snapBar)
        }
    }

    fun getAllUserSocietyMember(userId: Int, onReturn: (List<SocietyMember>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.getAllUserSocietyMember(userId, onReturn, snapBar)
        }
    }

    fun getAllPostReply(postId: Int, onReturn: (List<PostReply>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.getAllPostReply(postId, onReturn, snapBar)
        }
    }

    fun adminUserRegisterAllow(onReturn: (Boolean) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminUserRegisterAllow(
                { onReturn(it.contentEquals("{\"code\":200,\"message\":\"OK\",\"data\":{\"userRegisterAllow\":true}}")) },
                snapBar
            )
        }
    }

    fun adminUserRegisterAllowSwitch(onReturn: (Boolean) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminUserRegisterAllowSwitch(
                { onReturn(it.contentEquals("{\"code\":200,\"message\":\"OK\",\"data\":{\"userRegisterAllow\":true}}")) },
                snapBar
            )
        }
    }


    fun adminUserCreate(userInfo: UserInfo, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminUserCreate(userInfo, onReturn, snapBar)
        }
    }

    fun adminUserDelete(userId: Int, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminUserDelete(userId, onReturn, snapBar)
        }
    }


}