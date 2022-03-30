package com.wh.admin.componment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wh.admin.data.society.Society
import com.wh.admin.data.society.SocietyMember
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.data.society.bbs.PostReply
import com.wh.admin.data.user.UserInfo

class ServerDataViewModel : ViewModel() {
    private val TAG = "WH_"

    private val serverApi = ServerApi.create()

    var allUser by mutableStateOf(emptyList<UserInfo>())
    var allSociety by mutableStateOf(emptyList<Society>())

    val dealWithException: (Exception, (String) -> Unit) -> Unit = {e,su->
        e.printStackTrace()
        su(e.localizedMessage!!)
    }

    suspend fun getAllUser(onError: (String) -> Unit) {
        try {
            allUser = serverApi.allUser().data
        } catch (e: Exception) {
            dealWithException(e,onError)
        }
    }

    suspend fun getAllSociety(onError: (String) -> Unit) {
        try {
            allSociety = serverApi.allSociety().data
        } catch (e: Exception) {
            dealWithException(e,onError)
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
            dealWithException(e,onError)
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
            dealWithException(e,onError)
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
            dealWithException(e,onError)
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
            dealWithException(e,onError)
        }
    }

    suspend fun getAllUserSocietyMember(
        userId: Int,
        onReturn: (List<SocietyMember>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.allUserSocietyMember(userId).data)
        } catch (e: Exception) {
            dealWithException(e,onError)
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
            dealWithException(e,onError)
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
            dealWithException(e,onError)
        }
    }

    suspend fun adminUserRegisterAllow(
        onReturn: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.adminUserRegisterAllow())
        } catch (e: Exception) {
            dealWithException(e,onError)
        }
    }

    suspend fun adminUserRegisterAllowSwitch(
        onReturn: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onReturn(serverApi.adminUserRegisterAllowSwitch())
        } catch (e: Exception) {
            dealWithException(e,onError)
        }
    }
}