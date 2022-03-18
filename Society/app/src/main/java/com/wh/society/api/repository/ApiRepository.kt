package com.wh.society.api.repository

import android.util.Log
import com.wh.society.api.ServerApi
import com.wh.society.api.data.*
import com.wh.society.api.data.admin.UserRegisterAllow
import com.wh.society.api.data.society.*
import com.wh.society.api.data.society.bbs.BBSInfo
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.society.bbs.PostReply
import com.wh.society.api.data.user.UserChatPrivate
import com.wh.society.api.data.user.UserInfo
import com.wh.society.api.data.user.UserPicture
import com.wh.society.store.SettingStore
import okhttp3.MultipartBody

class ApiRepository(private val serverApi: ServerApi, private val settingStore: SettingStore) {

    val TAG = "WH_e"
    suspend fun societyList(
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<Society> {
        try {
            return serverApi.societyList(cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyList: ${e.localizedMessage}")
            onError("/society/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyUpdate(
        societyId: Int,
        name: String,
        openTimestamp: String,
        describe: String,
        college: String,
        bbsName: String,
        bbsDescribe: String,
        iconUrl: String,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.societyUpdate(
                societyId = societyId,
                name = name,
                openTimestamp = openTimestamp,
                describe = describe,
                college = college,
                bbsName = bbsName,
                bbsDescribe = bbsDescribe,
                iconUrl = iconUrl,
                cookieToken = cookieToken,
                authUserId = authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyUpdate: ${e.localizedMessage}")
            onError("/society/update ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyInfo(
        societyId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<Society> {
        try {
            return serverApi.societyInfo(societyId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyInfo: ${e.localizedMessage}")
            onError("/society/info ${e.localizedMessage}")
        }
        return ReturnObjectData.blank(Society())
    }

    suspend fun userInfo(
        userId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<UserInfo> {
        try {
            return serverApi.userInfo(userId = userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userInfo: ${e.localizedMessage}")
            onError("/user/info ${e.localizedMessage}")
        }
        return ReturnObjectData.blank()
    }

    suspend fun userInfoSimple(
        userId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<UserInfo> {
        try {
            return serverApi.userInfoSimple(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userInfo: ${e.localizedMessage}")
            onError("/user/info/simple ${e.localizedMessage}")
        }
        return ReturnObjectData.blank()
    }

    suspend fun userInfoUpdate(
        userId: Int,
        username: String,
        email: String,
        studentNumber: String,
        iconUrl: String,
        phone: String,
        name: String,
        password: String,
        college: String,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.userInfoUpdate(
                userId = userId,
                username = username,
                email = email,
                studentNumber = studentNumber,
                iconUrl = iconUrl,
                phone = phone,
                name = name,
                college = college,
                password = password,
                cookieToken = cookieToken,
                authUserId = authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "userInfoUpdate: ${e.localizedMessage}")
            onError("/user/info/update ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun userInfoUpdatePassword(
        name: String,
        username: String,
        studentNumber: String,
        phone: String,
        email: String,
        password: String,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.userInfoUpdatePassword(
                name = name,
                username = username,
                studentNumber = studentNumber,
                phone = phone,
                email = email,
                password = password
            )
        } catch (e: Exception) {
            Log.e(TAG, "userInfoUpdate: ${e.localizedMessage}")
            onError("/user/info/update/password ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun userLogin(
        phoneStudentIdEmail: String,
        password: String,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<LoginReturn> {
        try {
            return serverApi.userLogin(phoneStudentIdEmail, password)
        } catch (e: Exception) {
            Log.e(TAG, "userLogin: ${e.localizedMessage}")
            onError("/user/login ${e.localizedMessage}")
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

    suspend fun userLogout(
        userId: Int,
        cookieToken: String,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.userLogout(userId, cookieToken)
        } catch (e: Exception) {
            Log.e(TAG, "userLogout: ${e.localizedMessage}")
            onError("/user/logout ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyMemberRequestCreate(
        userId: Int,
        societyId: Int,
        request: String,
        isJoin: Boolean,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
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
            onError("/society/member/request/create ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyMemberRequestList(
        societyId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyMemberRequest> {
        try {
            return serverApi.societyMemberRequestList(societyId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyMemberRequestList: ${e.localizedMessage}")
            onError("/society/member/request/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyJoint(
        societyId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyMember> {
        try {
            return serverApi.societyJoint(societyId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyJoint: ${e.localizedMessage}")
            onError("/society/joint ")
        }
        return ReturnListData.blank()
    }

    suspend fun societyBBSInfo(
        societyId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<BBSInfo> {
        try {
            return serverApi.societyBBSInfo(societyId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyBBSInfo: ${e.localizedMessage}")
            onError("/society/bbs/info ${e.localizedMessage}")
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
        authUserId: Int,
        onError: (String) -> Unit = {}
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
            onError("/society/bbs/post/create ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyBBSPostById(
        postId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<Post> {
        try {
            return serverApi.societyBBSPostById(postId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyBBSPostById: ${e.localizedMessage}")
            onError("/society/bbs/post/by/id ${e.localizedMessage}")
        }
        return ReturnObjectData.blank()
    }

    suspend fun societyBBSPostDelete(
        userId: Int,
        postId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.societyBBSPostDelete(userId, postId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyBBSPostDelete: ${e.localizedMessage}")
            onError("/society/bbs/post/delete ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyBBSPostReplyList(
        postId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<PostReply> {
        try {
            return serverApi.societyBBSPostReplyList(postId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyBBSPostReplyList: ${e.localizedMessage}")
            onError("/society/bbs/post/reply/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyBBSPostReplyCreate(
        societyId: Int,
        postId: Int,
        userId: Int,
        reply: String,
        deviceName: String,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
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
            onError("/society/bbs/post/reply/create ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyChatInnerList(
        societyId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyChatMessage> {
        try {
            return serverApi.societyChatInnerList(societyId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyChatInnerList: ${e.localizedMessage}")
            onError("/society/chat/inner/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyChatInnerCreate(
        societyId: Int,
        userId: Int,
        message: String,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
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
            onError("/society/chat/inner/create ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyActivityList(
        societyId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyActivity> {
        try {
            return serverApi.societyActivityList(
                societyId = societyId,
                cookieToken = cookieToken,
                authUserId = authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyActivityList: ${e.localizedMessage}")
            onError("/society/activity/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyActivityCreate(
        societyId: Int,
        deviceName: String,
        title: String,
        level: Int,
        activity: String,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.societyActivityCreate(
                societyId = societyId,
                deviceName = deviceName,
                title = title,
                level = level,
                activity = activity,
                cookieToken = cookieToken,
                authUserId = authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyActivityCreate: ${e.localizedMessage}")
            onError("/society/activity/create ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyActivityRequestList(
        societyId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyActivityRequest> {
        try {
            return serverApi.societyActivityRequestList(societyId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyActivityRequestList: ${e.localizedMessage}")
            onError("/society/activity/request/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyActivityRequestCreate(
        societyId: Int,
        userId: Int,
        request: String,
        isJoin: Boolean,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.societyActivityRequestCreate(
                societyId = societyId,
                userId = userId,
                request = request,
                isJoin = isJoin,
                cookieToken,
                authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyActivityRequestCreate: ${e.localizedMessage}")
            onError("/society/activity/request/request ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyActivityMemberList(
        activityId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyActivityMember> {
        try {
            return serverApi.societyActivityMemberList(activityId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyActivityMemberList: ${e.localizedMessage}")
            onError("/society/activity/member/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyPictureList(
        societyId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyPicture> {
        try {
            return serverApi.societyPictureList(societyId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "societyPictureList: ${e.localizedMessage}")
            onError("/society/picture/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyPictureCreate(
        imageBodyPart: MultipartBody.Part,
        societyId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            val societyIdPart = MultipartBody.Part.createFormData("societyId", societyId.toString())
            return serverApi.societyPictureCreate(imageBodyPart, societyIdPart)
        } catch (e: Exception) {
            Log.e(TAG, "societyPictureCreate: ${e.localizedMessage}")
            onError("/society/picture/create ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun societyPictureDelete(
        societyId: Int,
        picToken: String,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.societyPictureDelete(
                societyId = societyId,
                picToken = picToken,
                cookieToken = cookieToken,
                authUserId = authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyPictureDelete: ${e.localizedMessage}")
            onError("/society/picture/delete")
        }
        return "failed"
    }

    suspend fun societyNoticeList(
        societyId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyNotice> {
        try {
            return serverApi.societyNoticeList(
                societyId = societyId,
                cookieToken = cookieToken,
                authUserId = authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyNoticeList: ${e.localizedMessage}")
            onError("/society/notify/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun societyNoticeCreate(
        societyId: Int,
        postUserId: Int,
        title: String,
        notice: String,
        permissionLevel: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.societyNoticeCreate(
                societyId = societyId,
                postUserId = postUserId,
                title = title,
                notice = notice,
                permissionLevel = permissionLevel,
                cookieToken = cookieToken,
                authUserId = authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "societyNoticeList: ${e.localizedMessage}")
            onError("/society/notice/create ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun userRegister(
        username: String,
        phone: String,
        email: String,
        name: String,
        password: String,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.userRegister(username, phone, email, name, password)
        } catch (e: Exception) {
            Log.e(TAG, "userRegister: ${e.localizedMessage}")
            onError("/user/register ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun userJoint(
        userId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyMember> {
        try {
            return serverApi.userJoint(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userJoint: ${e.localizedMessage}")
            onError("/user/joint ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun userJoinRequestList(
        userId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyMemberRequest> {
        try {
            return serverApi.userJoinRequestList(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userJoinRequestList: ${e.localizedMessage}")
            onError("/user/join/request/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun userPostList(
        userId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<Post> {
        try {
            return serverApi.userPostList(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userPost: ${e.localizedMessage}")
            onError("/user/post/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun userPostReplyList(
        userId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<PostReply> {
        try {
            return serverApi.userPostReplyList(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userPostReplyList: ${e.localizedMessage}")
            onError("/user/post/reply/list ${e.localizedMessage}")
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
        opUserId: Int,
        message: String,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): String {
        Log.d(TAG, "userChatPrivateCreate: userId:${userId}")
        Log.d(TAG, "userChatPrivateCreate: opUserId:${opUserId}")
        try {
            return serverApi.userChatPrivateCreate(
                userId,
                opUserId,
                message,
                cookieToken,
                authUserId
            )
        } catch (e: Exception) {
            Log.e(TAG, "userChatPrivateCreate: ${e.localizedMessage}")
            onError("/user/chat/private/create ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun userChatPrivateList(
        userId: Int,
        opUserId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<UserChatPrivate> {
        try {
            return serverApi.userChatPrivateList(userId, opUserId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "userChatPrivateList: ${e.localizedMessage}")
            onError("/user/chat/private/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun picList(
        userId: Int,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<UserPicture> {
        try {
            return serverApi.picList(userId, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "picList: ${e.localizedMessage}")
            onError("/pic/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun picCreate(
        imageBodyPart: MultipartBody.Part,
        userId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            val userIdPart = MultipartBody.Part.createFormData("userId", userId.toString())
            return serverApi.picCreate(imageBodyPart, userIdPart)
        } catch (e: Exception) {
            Log.e(TAG, "picCreate: ${e.localizedMessage}")
            onError("/pic/create ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun picDelete(
        userId: Int,
        picToken: String,
        cookieToken: String,
        authUserId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.picDelete(userId, picToken, cookieToken, authUserId)
        } catch (e: Exception) {
            Log.e(TAG, "picDelete: ${e.localizedMessage}")
            onError("/pic/delete ${e.localizedMessage}")
        }
        return "failed"
    }

    suspend fun collegeList(onError: (String) -> Unit = {}): ReturnListData<College> {
        try {
            return serverApi.collegeList()
        } catch (e: Exception) {
            Log.e(TAG, "collegeList: ${e.localizedMessage}")
            onError("/college/list ${e.localizedMessage}")
        }
        return ReturnListData.blank()
    }

    suspend fun adminUserRegisterAllow(onError: (String) -> Unit = {}): ReturnObjectData<UserRegisterAllow> {
        try {
            return serverApi.adminUserRegisterAllow()
        } catch (e: Exception) {
            Log.e(TAG, "adminUserRegisterAllow: ${e.localizedMessage}")
            onError("/admin/user/register/allow ${e.localizedMessage}")
        }
        return ReturnObjectData.blank(UserRegisterAllow())
    }
}