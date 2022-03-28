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

    suspend fun getAllUser(onError: (String) -> Unit) {
        try {
            allUser = serverApi.allUser().data
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.stackTraceToString())
        }
    }

    suspend fun getAllSociety(onError: (String) -> Unit) {
        try {
            allSociety = serverApi.allSociety().data
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.stackTraceToString())
        }
    }

    suspend fun getAllUserPost(userId: Int, onError: (String) -> Unit): List<Post> {
        try {
            return serverApi.allUserPost(userId).data
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.stackTraceToString())
        }
        return emptyList()
    }

    suspend fun getAllUserReply(userId: Int,onError: (String) -> Unit): List<PostReply> {
        try {
            return serverApi.allUserReply(userId).data
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.stackTraceToString())
        }
        return emptyList()
    }
    suspend fun getAllUserSocietyMember(userId: Int,onError: (String) -> Unit): List<SocietyMember> {
        try {
            return serverApi.allUserSocietyMember(userId).data
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.stackTraceToString())
        }
        return emptyList()
    }

    suspend fun getAllSocietyPost(societyId: Int, onError: (String) -> Unit): List<Post> {
        try {
            return serverApi.allSocietyPost(societyId).data
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.stackTraceToString())
        }
        return emptyList()
    }

    suspend fun getAllPostReply(postId: Int, onError: (String) -> Unit): List<PostReply> {
        try {
            return serverApi.allPostReply(postId).data
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.stackTraceToString())
        }
        return emptyList()
    }

    suspend fun adminUserRegisterAllow(onError: (String) -> Unit): String {
        try {
            return serverApi.adminUserRegisterAllow()
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.stackTraceToString())
        }
        return ""
    }

    suspend fun adminUserRegisterAllowSwitch(onError: (String) -> Unit): String {
        try {
            return serverApi.adminUserRegisterAllowSwitch()
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.stackTraceToString())
        }
        return ""
    }
}