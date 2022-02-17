package com.wh.society.api.repository

import android.util.Log
import com.wh.society.api.ServerApi
import com.wh.society.api.data.*
import com.wh.society.store.SettingStore
import okhttp3.MultipartBody

class ApiRepository(private val serverApi: ServerApi, private val settingStore: SettingStore) {

    val TAG = "WH_e"
    suspend fun societyList(cookieToken: String, authUserId: Int): ReturnListData<Society> {
        try {
            return serverApi.societyList(cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyList: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun userInfo(
        userId: Int,
        cookieToken: String,
        authUserId: Int
    ): ReturnObjectData<UserInfo> {
        try {
            return serverApi.userInfo(userId = userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userInfo: ${e.localizedMessage}")
        }
        return ReturnObjectData.blank()
    }

    suspend fun userInfoSimple(
        userId: Int,
        cookieToken: String,
        authUserId: Int
    ): ReturnObjectData<UserInfo> {
        try {
            return serverApi.userInfoSimple(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userInfo: ${e.localizedMessage}")
        }
        return ReturnObjectData.blank()
    }

    suspend fun userLogin(
        phoneStudentIdEmail: String,
        password: String
    ): ReturnObjectData<LoginReturn> {
        try {
            return serverApi.userLogin(phoneStudentIdEmail, password)
        } catch (e: Exception) {
            Log.e(TAG, "userLogin: ${e.localizedMessage}")
        }
        return ReturnObjectData.blank()
    }

//    suspend fun userSIOConnected(
//        userId: Int,
//        cookieToken: String,
//        authUserId: Int
//    ): String {
//        try {
//            return serverApi.userSIOConnected(userId, cookieToken, authUserId)
//        } catch (e: Exception) {
//            Log.e(TAG, "userSIOConnected: ${e.localizedMessage}")
//        }
//        return "failed"
//    }

    suspend fun userLogout(userId: Int, cookieToken: String): String {
        try {
            return serverApi.userLogout(userId, cookieToken)
        } catch (e: Exception) {
            Log.e(TAG, "userLogout: ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyMemberRequestCreate(
        userId: Int,
        societyId: Int,
        request: String,
        isJoin: Boolean,
        cookieToken: String,
        authUserId: Int
    ): String {
        try {
            return serverApi.societyMemberRequestCreate(
                userId,
                societyId,
                request,
                isJoin,
                cookieToken,
                authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyMemberRequest: ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyMemberRequestList(societyId: Int,cookieToken: String,authUserId: Int): ReturnListData<MemberRequest> {
        try {
            return serverApi.societyMemberRequestList(societyId,cookieToken, authUserId)
        }catch (e:Exception){
            Log.e(TAG, "societyMemberRequestList: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyJoint(
        societyId: Int,
        cookieToken: String,
        authUserId: Int
    ): ReturnListData<SocietyJoint> {
        try {
            return serverApi.societyJoint(societyId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyJoint: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyBBSInfo(
        societyId: Int,
        cookieToken: String,
        authUserId: Int
    ): ReturnObjectData<BBSInfo> {
        try {
            return serverApi.societyBBSInfo(societyId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyBBSInfo: ${e.localizedMessage}")
        }
        return ReturnObjectData.blank()
    }

    suspend fun societyBBSPostCreate(
        societyId: Int,
        userId: Int,
        title: String,
        post: String,
        level: Int, deviceName: String,
        cookieToken: String,
        authUserId: Int
    ): String {
        try {
            return serverApi.societyBBSPostCreate(
                societyId,
                userId,
                title,
                post,
                level,
                deviceName,
                cookieToken,
                authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyBBSPostCreate: ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyBBSPostDelete(
        userId: Int,
        postId: Int,
        cookieToken: String,
        authUserId: Int
    ): String {
        try {
            return serverApi.societyBBSPostDelete(userId, postId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyBBSPostDelete: ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyBBSPostReplyList(
        postId: Int,
        cookieToken: String, authUserId: Int
    ): ReturnListData<PostReply> {
        try {
            return serverApi.societyBBSPostReplyList(postId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyBBSPostReplyList: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyBBSPostReplyCreate(
        societyId: Int,
        postId: Int,
        userId: Int,
        reply: String,
        deviceName: String,
        cookieToken: String, authUserId: Int
    ): String {
        try {
            return serverApi.societyBBSPostReplyCreate(
                societyId,
                postId,
                userId,
                reply,
                deviceName,
                cookieToken,
                authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyBBSPostReplyCreate: ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyChatInnerList(
        societyId: Int,
        cookieToken: String, authUserId: Int
    ): ReturnListData<SocietyChatMessage> {
        try {
            return serverApi.societyChatInnerList(societyId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyChatInnerList: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyChatInnerCreate(
        societyId: Int,
        userId: Int,
        message: String,
        cookieToken: String, authUserId: Int
    ): String {
        try {
            return serverApi.societyChatInnerCreate(
                societyId,
                userId,
                message,
                cookieToken,
                authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyChatInnerCreate: ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun userJoint(
        userId: Int,
        cookieToken: String,
        authUserId: Int
    ): ReturnListData<SocietyJoint> {
        try {
            return serverApi.userJoint(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userJoint: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun userJoinRequestList(
        userId: Int,
        cookieToken: String,
        authUserId: Int
    ): ReturnListData<MemberRequest> {
        try {
            return serverApi.userJoinRequestList(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userJoinRequestList: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun userPostList(
        userId: Int,
        cookieToken: String,
        authUserId: Int
    ): ReturnListData<Post> {
        try {
            return serverApi.userPostList(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userPost: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun userPostReplyList(
        userId: Int,
        cookieToken: String,
        authUserId: Int
    ): ReturnListData<PostReply> {
        try {
            return serverApi.userPostReplyList(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userPostReplyList: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

//    suspend fun userPostReplyList(
//        userId: Int,
//        postId: Int,
//        cookieToken: String,
//        authUserId: Int
//    ): ReturnListData<PostReply> {
//        try {
//            return serverApi.userPostReplyList(userId, postId, cookieToken, authUserId)
//        } catch (e: Exception) {
//            Log.e(TAG, "userPostReplyList: ${e.localizedMessage}")
//        }
//        return ReturnListData.blank()
//    }

    suspend fun userChatPrivateCreate(
        userId: Int,
        opUserId:Int,
        message:String,
        cookieToken: String,
        authUserId: Int
    ): String {
        Log.d(TAG, "userChatPrivateCreate: userId:${userId}")
        Log.d(TAG, "userChatPrivateCreate: opUserId:${opUserId}")
        try{
            return serverApi.userChatPrivateCreate(userId, opUserId, message, cookieToken, authUserId)
        }catch (e:Exception){
            Log.e(TAG, "userChatPrivateCreate: ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun userChatPrivateList(
        userId: Int,opUserId: Int,cookieToken: String,authUserId: Int
    ): ReturnListData<ChatPrivate> {
        try {
            return serverApi.userChatPrivateList(userId, opUserId, cookieToken, authUserId)
        }catch (e:Exception){
            Log.e(TAG, "userChatPrivateList: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun picList(
        userId: Int,
        cookieToken: String,
        authUserId: Int
    ): ReturnListData<PicData> {
        try {
            return serverApi.picList(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "picList: ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun picCreate(imageBodyPart: MultipartBody.Part, userId: Int): String {
        try {
            val userIdPart = MultipartBody.Part.createFormData("userId", userId.toString())
            return serverApi.picCreate(imageBodyPart, userIdPart)
        } catch (e: Exception) {
            Log.e(TAG, "picCreate: ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun picDelete(
        userId: Int,
        picToken: String,
        cookieToken: String,
        authUserId: Int
    ): String {
        try {
            return serverApi.picDelete(userId, picToken, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "picDelete: ${e.localizedMessage}")
        }
        return "failed"
    }
}