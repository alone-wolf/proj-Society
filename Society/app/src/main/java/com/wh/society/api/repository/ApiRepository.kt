package com.wh.society.api.repository

import android.util.Log
import com.wh.society.api.NoAuthApi
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
import okhttp3.MultipartBody

class ApiRepository(private val serverApi: ServerApi, private val noAuthApi: NoAuthApi) {

    val TAG = "WH_e"

    val handleError: (path: String, ex: Exception, onError: (String) -> Unit) -> Unit =
        { path, ex, onError ->
            val m = "$path ${ex.localizedMessage}"
            Log.e(TAG, m)
            onError(m)
        }

    suspend fun societyList(
        onError: (String) -> Unit = {}
    ): ReturnListData<Society> {
        try {
            return serverApi.societyList()
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun societyUpdate(
        society: Society,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.societyUpdate(society.toRequestBody())
            onReturn()
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyInfo(
        societyId: Int,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<Society> {
        try {
            return serverApi.societyInfo(societyId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnObjectData.blank(Society())
    }

    suspend fun societyMemberBySocietyId(
        societyId: Int,
        onError: (String) -> Unit
    ): ReturnObjectData<SocietyMember> {
        try {
            return serverApi.societyMemberBySocietyId(societyId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnObjectData.blank(SocietyMember())
    }

    suspend fun userInfo(
        userId: Int,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<UserInfo> {
        try {
            return serverApi.userInfo(userId = userId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnObjectData.blank()
    }

    suspend fun userInfoSimple(
        userId: Int,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<UserInfo> {
        try {
            return serverApi.userInfoSimple(userId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnObjectData.blank()
    }

    suspend fun userInfoUpdate(
        userInfo: UserInfo,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.userInfoUpdate(userInfo.toRequestBody())
            onReturn()
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun userInfoUpdatePassword(
        name: String,
        username: String,
        studentNumber: String,
        phone: String,
        email: String,
        password: String,
        onError: (String) -> Unit = {}
    ) {
        try {
            noAuthApi.userInfoUpdatePassword(
                name = name,
                username = username,
                studentNumber = studentNumber,
                phone = phone,
                email = email,
                password = password
            )
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun userLogin(
        phoneStudentIdEmail: String,
        password: String,
        onError: (String) -> Unit
    ): ReturnObjectData<LoginReturn> {
        try {
            return noAuthApi.userLogin(phoneStudentIdEmail, password)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnObjectData.blank()
    }

    suspend fun userLogout(
        userId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.userLogout(userId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return "failed"
    }

    suspend fun societyMemberRequestCreate(
        userId: Int,
        societyId: Int,
        request: String,
        isJoin: Boolean,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.societyMemberRequestCreate(
                userId = userId,
                societyId = societyId,
                request = request,
                isJoin = isJoin
            )
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return "failed"
    }

    suspend fun societyMemberRequestList(
        societyId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyMemberRequest> {
        try {
            return serverApi.societyMemberRequestList(societyId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun societyMemberRequestDeal(
        requestId: Int,
        isAgreed: Boolean,
        onError: (String) -> Unit
    ){
        try {
            serverApi.societyMemberRequestDeal(requestId, isAgreed)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyJoint(
        societyId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyMember> {
        try {
            return serverApi.societyJoint(societyId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun societyBBSInfo(
        societyId: Int,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<BBSInfo> {
        try {
            return serverApi.societyBBSInfo(societyId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnObjectData.blank()
    }

    suspend fun societyBBSPostCreate(
        post: Post,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.societyBBSPostCreate(
                post.toRequestBody()
            )
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return "failed"
    }

    suspend fun societyBBSPostById(
        postId: Int,
        onError: (String) -> Unit = {}
    ): ReturnObjectData<Post> {
        try {
            return serverApi.societyBBSPostById(postId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnObjectData.blank()
    }

    suspend fun societyBBSPostDelete(
        userId: Int,
        postId: Int,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.societyBBSPostDelete(userId, postId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return "failed"
    }

    suspend fun societyBBSPostReplyList(
        postId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<PostReply> {
        try {
            return serverApi.societyBBSPostReplyList(postId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun societyBBSPostReplyCreate(
        postReply: PostReply,
        onError: (String) -> Unit = {}
    ): String {
        try {
            return serverApi.societyBBSPostReplyCreate(postReply.toRequestBody())
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return "failed"
    }

    suspend fun societyBBSPostReplyDelete(
        postReplyId: Int,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.societyBBSPostReplyDelete(postReplyId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyChatInnerList(
        societyId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyChatMessage> {
        try {
            return serverApi.societyChatInnerList(societyId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun societyChatInnerCreate(
        societyId: Int,
        userId: Int,
        message: String,
        onError: (String) -> Unit = {}
    ) {
        try {
            serverApi.societyChatInnerCreate(
                societyId = societyId,
                userId = userId,
                message = message,
            )
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyChatInnerDelete(chatId:Int,onError: (String) -> Unit){
        try {
            serverApi.societyChatInnerDelete(chatId)
        }catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyChatInnerClear(
        societyId: Int,
        onError: (String) -> Unit = {}
    ) {
        try {
            serverApi.societyChatInnerClear(societyId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }


    suspend fun societyActivityList(
        societyId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyActivity> {
        try {
            return serverApi.societyActivityList(
                societyId = societyId
            )
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun societyActivityCreate(
        activity: SocietyActivity,
        onError: (String) -> Unit = {}
    ) {
        try {
            serverApi.societyActivityCreate(
                activity.toRequestBody()
            )
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyActivityDelete(
        activityId: Int,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.societyActivityDelete(activityId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyActivityJoin(
        activityId: Int, userId: Int,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.societyActivityJoin(activityId, userId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyActivityLeave(
        activityId: Int,
        userId: Int,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.societyActivityLeave(activityId, userId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyActivityMember(
        activityId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyActivityMember> {
        try {
            return serverApi.societyActivityMember(activityId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun societyActivityMemberDelete(memberId:Int,onError: (String) -> Unit){
        try {
            serverApi.societyActivityMemberDelete(memberId)
        }catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyPictureList(
        societyId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyPicture> {
        try {
            return serverApi.societyPictureList(societyId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
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
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return "failed"
    }

    suspend fun societyPictureDelete(
        societyId: Int,
        picToken: String,
        onError: (String) -> Unit = {}
    ) {
        try {
            serverApi.societyPictureDelete(
                societyId = societyId,
                picToken = picToken
            )
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyNoticeList(
        societyId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyNotice> {
        try {
            return serverApi.societyNoticeList(
                societyId = societyId
            )
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun societyNoticeDelete(
        noticeId:Int,
        onError: (String) -> Unit
    ){
        try {
            serverApi.societyNoticeDelete(noticeId)
        }catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun societyNoticeCreate(
        societyNotice: SocietyNotice,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            serverApi.societyNoticeCreate(societyNotice.toRequestBody())
            onReturn.invoke()
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }


    suspend fun userRegister(
        username: String,
        phone: String,
        email: String,
        name: String,
        password: String,
        onError: (String) -> Unit = {}
    ) {
        try {
            noAuthApi.userRegister(username, phone, email, name, password)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun userJoint(
        userId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyMember> {
        try {
            return serverApi.userJoint(userId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun userJoinRequestList(
        userId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<SocietyMemberRequest> {
        try {
            return serverApi.userJoinRequestList(userId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun userPostList(
        userId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<Post> {
        try {
            return serverApi.userPostList(userId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun userPostReplyList(
        userId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<PostReply> {
        try {
            return serverApi.userPostReplyList(userId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun userChatPrivateCreate(
        userId: Int,
        opUserId: Int,
        message: String,
        onError: (String) -> Unit = {}
    ) {
        try {
            serverApi.userChatPrivateCreate(
                userId = userId,
                opUserId = opUserId,
                message = message
            )
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun userChatPrivateList(
        userId: Int,
        opUserId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<UserChatPrivate> {
        try {
            return serverApi.userChatPrivateList(userId, opUserId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun picList(
        userId: Int,
        onError: (String) -> Unit = {}
    ): ReturnListData<UserPicture> {
        try {
            return serverApi.picList(userId)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun picCreate(
        imageBodyPart: MultipartBody.Part,
        userId: Int,
        onError: (String) -> Unit = {}
    ) {
        try {
            val userIdPart = MultipartBody.Part.createFormData("userId", userId.toString())
            serverApi.picCreate(imageBodyPart, userIdPart)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun picDelete(
        userId: Int,
        picToken: String,
        onError: (String) -> Unit = {}
    ) {
        try {
            serverApi.picDelete(userId, picToken)
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
    }

    suspend fun collegeList(
        onError: (String) -> Unit
    ): ReturnListData<College> {
        try {
            return serverApi.collegeList()
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnListData.blank()
    }

    suspend fun adminUserRegisterAllow(
        onError: (String) -> Unit
    ): ReturnObjectData<UserRegisterAllow> {
        try {
            return noAuthApi.adminUserRegisterAllow()
        } catch (e: Exception) {
            val funName = object {}.javaClass.enclosingMethod.name ?: "funName"
            handleError(funName, e, onError)
        }
        return ReturnObjectData.blank(UserRegisterAllow())
    }
}