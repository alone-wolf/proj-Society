package com.wh.admin.componment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wh.admin.data.society.*
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.data.society.bbs.PostReply
import com.wh.admin.data.user.UserInfo

class ServerDataViewModel : ViewModel() {

    private val serverApi = AdminApi.create()

    var allUser by mutableStateOf(emptyList<UserInfo>())
    var allSociety by mutableStateOf(emptyList<Society>())
    var allActivity by mutableStateOf(emptyList<SocietyActivity>())

    val dealWithException: (Exception, (String) -> Unit) -> Unit = { e, su ->
        e.printStackTrace()
        su(e.localizedMessage!!)
    }

    suspend fun getAllUser(onError: (String) -> Unit) {
        try {
            allUser = serverApi.allUser().data
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun getAllSociety(onError: (String) -> Unit) {
        try {
            allSociety = serverApi.allSociety().data
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminUserCreate(
        userInfo: UserInfo,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminUserCreate(userInfo.toRequestBody())
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminUserUpdate(
        userInfo: UserInfo,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminUserUpdate(userInfo.toRequestBody())
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminUserDelete(
        userId: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminUserDelete(userId)
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminUserById(
        userId: Int,
        onReturn: (UserInfo) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminUserById(userId).data?.let {
                onReturn(it)
            }
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun getAllUserPost(
        userId: Int,
        onReturn: (List<Post>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allUserPost(userId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun getAllUserReply(
        userId: Int,
        onReturn: (List<PostReply>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allUserReply(userId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun allUserSocietyMember(
        userId: Int,
        onReturn: (List<SocietyMember>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allUserSocietyMember(userId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun allUserSocietyMemberRequest(
        userId: Int,
        onReturn: (List<SocietyMemberRequest>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allUserSocietyMemberRequest(userId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun allUserSocietyActivityMember(
        userId: Int,
        onReturn: (List<SocietyActivityMember>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allUserSocietyActivityMember(userId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }


    suspend fun getAllSocietyPost(
        societyId: Int,
        onReturn: (List<Post>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allSocietyPost(societyId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun allSocietyReply(
        societyId: Int,
        onReturn: (List<PostReply>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allSocietyReply(societyId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun allSocietyMember(
        societyId: Int,
        onReturn: (List<SocietyMember>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allSocietyMember(societyId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun allSocietyMemberRequest(
        societyId: Int,
        onReturn: (List<SocietyMemberRequest>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allSocietyMemberRequest(societyId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun allSocietyActivity(
        societyId: Int,
        onReturn: (List<SocietyActivity>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allSocietyActivity(societyId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun getAllActivity(onError: (String) -> Unit) {
        try {
            allActivity = serverApi.allActivity().data
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun getAllPostReply(
        postId: Int,
        onReturn: (List<PostReply>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allPostReply(postId).data)
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminUserRegisterAllow(
        onReturn: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.adminUserRegisterAllow())
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminUserRegisterAllowSwitch(
        onReturn: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.adminUserRegisterAllowSwitch())
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminSocietyCreate(
        society: Society,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminSocietyCreate(society.toRequestBody())
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminSocietyUpdate(
        society: Society,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminSocietyUpdate(society.toRequestBody())
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminSocietyDelete(
        societyId: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminSocietyDelete(societyId)
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminSocietyMemberCreate(
        userId: Int,
        societyId: Int,
        permissionLevel: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminSocietyMemberCreate(userId, societyId, permissionLevel)
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminSocietyMemberUpdatePermission(
        userId: Int,
        societyId: Int,
        permissionLevel: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminSocietyMemberUpdatePermission(userId, societyId, permissionLevel)
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminSocietyActivityMemberDelete(
        memberId: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminSocietyActivityMemberDelete(memberId)
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminPostDelete(postId: Int, onReturn: () -> Unit, onError: (String) -> Unit) {
        try {
            serverApi.adminPostDelete(postId)
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }

    suspend fun adminPostReplyDelete(
        replyId: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminPostReplyDelete(replyId)
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }


    suspend fun adminSocietyMemberDelete(
        userId: Int,
        societyId: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.adminSocietyMemberDelete(userId, societyId)
            onReturn()
        } catch (e: Exception) {
            dealWithException(e, onError)
        }
    }
}