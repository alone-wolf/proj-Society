package com.wh.admin.componment

import com.wh.admin.MainActivity
import com.wh.admin.data.society.*
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

    fun allUser() {
        coroutineScope.launch {
            serverDataViewModel.getAllUser(snapBar)
        }
    }

    fun allSociety() {
        coroutineScope.launch {
            serverDataViewModel.getAllSociety(snapBar)
        }
    }

    fun allActivity() {
        coroutineScope.launch {
            serverDataViewModel.getAllActivity(snapBar)
        }
    }

    fun allSocietyPost(societyId: Int, onReturn: (List<Post>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.getAllSocietyPost(societyId, onReturn, snapBar)
        }
    }

    fun allSocietyReply(societyId: Int, onReturn: (List<PostReply>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.allSocietyReply(societyId, onReturn, snapBar)
        }
    }

    fun allSocietyMember(societyId: Int, onReturn: (List<SocietyMember>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.allSocietyMember(societyId, onReturn, snapBar)
        }
    }

    fun allSocietyMemberRequest(societyId: Int, onReturn: (List<SocietyMemberRequest>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.allSocietyMemberRequest(societyId, onReturn, snapBar)
        }
    }

    fun allSocietyActivity(societyId: Int, onReturn: (List<SocietyActivity>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.allSocietyActivity(societyId, onReturn, snapBar)
        }
    }

    fun allUserPost(userId: Int, onReturn: (List<Post>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.getAllUserPost(userId, onReturn, snapBar)
        }
    }

    fun allUserReply(userId: Int, onReturn: (List<PostReply>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.getAllUserReply(userId, onReturn, snapBar)
        }
    }

    fun allUserSocietyMember(userId: Int, onReturn: (List<SocietyMember>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.allUserSocietyMember(userId, onReturn, snapBar)
        }
    }

    fun allUserSocietyMemberRequest(userId: Int, onReturn: (List<SocietyMemberRequest>) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.allUserSocietyMemberRequest(userId, onReturn, snapBar)
        }
    }

    fun allUserSocietyActivityMember(
        userId: Int,
        onReturn: (List<SocietyActivityMember>) -> Unit
    ) {
        coroutineScope.launch {
            serverDataViewModel.allUserSocietyActivityMember(userId, onReturn, snapBar)
        }
    }

    fun allPostReply(postId: Int, onReturn: (List<PostReply>) -> Unit) {
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

    fun adminSocietyCreate(society: Society, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminSocietyCreate(society, onReturn, snapBar)
        }
    }

    fun adminSocietyUpdate(society: Society, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminSocietyUpdate(society, onReturn, snapBar)
        }
    }

    fun adminSocietyDelete(societyId: Int, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminSocietyDelete(societyId, onReturn, snapBar)
        }
    }


    fun adminUserCreate(userInfo: UserInfo, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminUserCreate(userInfo, onReturn, snapBar)
        }
    }

    fun adminUserUpdate(userInfo: UserInfo, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminUserUpdate(userInfo, onReturn, snapBar)
        }
    }

    fun adminUserDelete(userId: Int, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminUserDelete(userId, onReturn, snapBar)
        }
    }

    fun adminUserById(userId: Int, onReturn: (UserInfo) -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminUserById(userId, onReturn, snapBar)
        }
    }

    fun adminSocietyMemberCreate(
        userId: Int,
        societyId: Int,
        permissionLevel: Int,
        onReturn: () -> Unit
    ) {
        coroutineScope.launch {
            serverDataViewModel.adminSocietyMemberCreate(
                userId,
                societyId,
                permissionLevel,
                onReturn,
                snapBar
            )
        }
    }

    fun adminSocietyMemberUpdatePermission(
        userId: Int,
        societyId: Int,
        permissionLevel: Int,
        onReturn: () -> Unit
    ) {
        coroutineScope.launch {
            serverDataViewModel.adminSocietyMemberUpdatePermission(
                userId,
                societyId,
                permissionLevel,
                onReturn,
                snapBar
            )
        }
    }

    fun adminPostDelete(postId: Int, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminPostDelete(postId, onReturn, snapBar)
        }
    }

    fun adminPostReplyDelete(replyId: Int, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminPostReplyDelete(replyId, onReturn, snapBar)
        }
    }

    fun adminSocietyMemberDelete(userId: Int, societyId: Int, onReturn: () -> Unit) {
        coroutineScope.launch {
            serverDataViewModel.adminSocietyMemberDelete(userId, societyId, onReturn, snapBar)
        }
    }


}