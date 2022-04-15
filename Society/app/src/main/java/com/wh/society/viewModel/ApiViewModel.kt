package com.wh.society.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wh.society.api.NoAuthApi
import com.wh.society.api.ServerApi
import com.wh.society.api.data.*
import com.wh.society.api.data.admin.UserRegisterAllow
import com.wh.society.api.data.society.*
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.api.data.society.bbs.BBSInfo
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.society.bbs.PostReply
import com.wh.society.api.data.user.UserChatPrivate
import com.wh.society.api.data.user.UserInfo
import com.wh.society.api.data.user.UserPicture
import com.wh.society.api.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ApiViewModel : ViewModel() {

    private var noAuthApi = NoAuthApi.create()

    private var serverApi = ServerApi.create()

    private val apiRepository = ApiRepository(serverApi, noAuthApi)

    var societyList by mutableStateOf(emptyList<Society>())

    var bbsList by mutableStateOf(emptyList<BBS>())

    var userInfo by mutableStateOf(ReturnObjectData.blank<UserInfo>())

    var loginToken by mutableStateOf(ReturnObjectData.blank<LoginReturn>())

    var picDataList by mutableStateOf(ReturnListData.blank<UserPicture>())
    var collegeList by mutableStateOf(ReturnListData.blank<College>())

    val TAG = "WH_"

    fun societyList(onError: (String) -> Unit = {}) {
        viewModelScope.launch {
            societyList = apiRepository.societyList(onError).data
            val b: MutableList<BBS> = mutableListOf()
            societyList.forEach {
                b.add(BBS.fromSociety(it))
            }
            bbsList = b
        }
    }

    fun societyUpdate(
        society: Society,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyUpdate(society, onReturn, onError)
        }
    }

    fun societyInfo(
        societyId: Int,
        onError: (String) -> Unit = {},
        onReturn: (ReturnObjectData<Society>) -> Unit,
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyInfo(
                societyId = societyId,
                onError = onError
            )
            onReturn(a)
        }
    }

    fun societyMemberBySocietyId(
        societyId: Int,
        onError: (String) -> Unit,
        onReturn: (SocietyMember) -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyMemberBySocietyId(societyId, onError).data?.let(onReturn)
        }
    }

    fun userInfo(onReturn: CoroutineScope.(UserInfo) -> Unit) {
        viewModelScope.launch {
            loginToken.data?.cookieToken?.let {
                userInfo = apiRepository.userInfo(
                    userId = loginToken.data!!.userId
                )
                userInfo.data?.let { it ->
                    onReturn(it)
                }
            } ?: Log.e(TAG, "userInfo: token is blank")
        }
    }

    fun userInfoSimple(userId: Int, onReturn: CoroutineScope.(UserInfo) -> Unit) {
        viewModelScope.launch {
            val userInfo = apiRepository.userInfoSimple(
                userId = userId
            )
            onReturn(userInfo.data!!)
        }
    }

    fun userInfoUpdate(u: UserInfo, onReturn: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.userInfoUpdate(
                userInfo = u,
                onReturn = onReturn,
                onError = onError
            )
            onReturn()
        }
    }

    fun userInfoUpdatePassword(
        name: String,
        username: String,
        studentNumber: String,
        phone: String,
        email: String,
        password: String,
        onReturn: () -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.userInfoUpdatePassword(
                name = name,
                username = username,
                studentNumber = studentNumber,
                phone = phone,
                email = email,
                password = password
            )
            onReturn()
        }
    }

    fun userLogin(
        phoneStudentIdEmail: String,
        password: String,
        onError: (String) -> Unit,
        onReturn: (LoginReturn) -> Unit,
    ) {
        viewModelScope.launch {
            loginToken = apiRepository.userLogin(phoneStudentIdEmail, password, onError)
            loginToken.data?.let(onReturn)
        }
    }

    fun userLogout(onReturn: () -> Unit) {
        viewModelScope.launch {
            apiRepository.userLogout(
                userId = userInfo.data!!.id
            )
            onReturn()
        }
    }

    fun societyMemberRequestCreate(
        userId: Int,
        societyId: Int,
        request: String,
        isJoin: Boolean,
        onReturn: () -> Unit = {}
    ) {
        viewModelScope.launch {
            apiRepository.societyMemberRequestCreate(
                userId = userId,
                societyId = societyId,
                request = request,
                isJoin = isJoin
            )
            onReturn()
        }
    }

    fun societyMemberRequestList(
        societyId: Int,
        onReturn: (ReturnListData<SocietyMemberRequest>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyMemberRequestList(
                societyId = societyId
            )
//            Log.d(TAG, "societyMemberRequestList: ${a.data.size}")
            onReturn(a)
        }
    }

    fun societyJoint(
        societyId: Int,
        onReturn: (ReturnListData<SocietyMember>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyJoint(
                societyId = societyId
            )
            onReturn(a)
        }
    }

    fun societyBBSInfo(
        societyId: Int,
        onReturn: (ReturnObjectData<BBSInfo>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyBBSInfo(
                societyId = societyId
            )
            onReturn(a)
        }
    }

    fun societyBBSPostCreate(
        post: Post,
        onReturn: () -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyBBSPostCreate(post)
            onReturn()
        }
    }

    fun societyBBSPostById(postId: Int, onReturn: (ReturnObjectData<Post>) -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.societyBBSPostById(
                postId = postId
            )
            onReturn(a)
        }
    }

    fun societyBBSPostDelete(userId: Int, postId: Int, onReturn: () -> Unit) {
        viewModelScope.launch {
            apiRepository.societyBBSPostDelete(
                userId = userId,
                postId = postId
            )
            onReturn()
        }
    }

    fun societyBBSPostReplyList(
        postId: Int,
        onReturn: (ReturnListData<PostReply>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyBBSPostReplyList(
                postId = postId
            )
//            Log.d(TAG, "societyBBSPostReplyList: ${a.data.size}")
            onReturn(a)
        }
    }

    fun societyBBSPostReplyCreate(
        postReply: PostReply,
        onReturn: () -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyBBSPostReplyCreate(postReply)
            onReturn()
        }
    }

    fun societyBBSPostReplyDelete(
        postReplyId: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyBBSPostReplyDelete(
                postReplyId = postReplyId,
                onError = onError
            )
            onReturn()
        }
    }

    fun societyChatInnerList(
        societyId: Int,
        onReturn: (ReturnListData<SocietyChatMessage>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyChatInnerList(
                societyId = societyId
            )
            onReturn(a)
        }
    }

    fun societyChatInnerCreate(
        societyId: Int,
        userId: Int,
        message: String,
        onReturn: () -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyChatInnerCreate(
                societyId = societyId,
                userId = userId,
                message = message
            )
            onReturn()
        }
    }

    fun societyChatInnerClear(
        societyId: Int,
        onError: (String) -> Unit,
        onReturn: () -> Unit,
    ) {
        viewModelScope.launch {
            apiRepository.societyChatInnerClear(societyId, onError)
            onReturn.invoke()
        }
    }

    fun societyActivityList(
        societyId: Int,
        onReturn: (ReturnListData<SocietyActivity>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyActivityList(
                societyId = societyId
            )
            onReturn(a)
        }
    }

    fun societyActivityCreate(
        activity: SocietyActivity,
        onReturn: () -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyActivityCreate(activity)
            onReturn()
        }
    }

    fun societyActivityDelete(
        activityId: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyActivityDelete(activityId, onError)
            onReturn.invoke()
        }
    }

    fun societyActivityJoin(
        activityId: Int,
        userId: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyActivityJoin(activityId, userId, onError)
            onReturn.invoke()
        }
    }

    fun societyActivityLeave(
        activityMemberId: Int,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyActivityLeave(activityMemberId, onError)
            onReturn.invoke()
        }
    }

    fun societyActivityMember(
        activityId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyActivityMember>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyActivityMember(
                activityId = activityId
            )
            onReturn(a)
        }
    }

    fun societyPictureList(
        societyId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyPicture>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyPictureList(
                societyId = societyId
            )
            onReturn(a)
        }
    }

    fun societyPictureCreate(
        imageBodyPart: MultipartBody.Part, societyId: Int, onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyPictureCreate(
                imageBodyPart = imageBodyPart,
                societyId = societyId,
            )
            onReturn()
        }
    }

    fun societyPictureDelete(
        societyId: Int, picToken: String, onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyPictureDelete(
                societyId,
                picToken
            )
            onReturn()
        }
    }

    fun societyNoticeList(
        societyId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyNotice>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyNoticeList(
                societyId = societyId
            )
            onReturn(a)
        }
    }

    fun societyNoticeCreate(
        societyNotice: SocietyNotice,
        onReturn: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.societyNoticeCreate(societyNotice, onReturn, onError)
        }
    }

    fun userRegister(
        username: String,
        phone: String,
        email: String,
        name: String,
        password: String,
        onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            apiRepository.userRegister(username, phone, email, name, password)
            onReturn()
        }
    }

    fun userJoint(userId: Int, onReturn: CoroutineScope.(ReturnListData<SocietyMember>) -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.userJoint(
                userId = userId
            )
            onReturn(a)
//            Log.d(TAG, "userJoint: ${a.data.size}")
        }
    }

    fun userJoinRequestList(
        userId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyMemberRequest>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.userJoinRequestList(
                userId = userId
            )
            onReturn(a)
//            Log.d(TAG, "userJoinRequestList: ${a.data.size}")
        }
    }

    fun userPostList(userId: Int, onReturn: CoroutineScope.(ReturnListData<Post>) -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.userPostList(
                userId = userId
            )
            onReturn(a)
//            Log.d(TAG, "userPostList: ${a.data.size}")
        }
    }

    fun userPostReplyList(
        userId: Int,
        onReturn: CoroutineScope.(ReturnListData<PostReply>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.userPostReplyList(
                userId = userId
            )
            onReturn(a)
//            Log.d(TAG, "userPostReplyList: ${a.data.size}")
        }
    }

    fun userChatPrivateCreate(opUserId: Int, message: String, onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.userChatPrivateCreate(
                userId = userInfo.data!!.id,
                opUserId = opUserId,
                message = message
            )
            onReturn()
        }
    }

    fun userChatPrivateList(
        opUserId: Int,
        onReturn: CoroutineScope.(ReturnListData<UserChatPrivate>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.userChatPrivateList(
                userId = userInfo.data!!.id,
                opUserId = opUserId
            )
            onReturn(a)
        }
    }

    fun picList() {
        viewModelScope.launch {
            picDataList = apiRepository.picList(
                userId = loginToken.data!!.userId
            )
//            Log.d(TAG, "picList: ${picDataList.data.size}")
        }
    }

    fun picCreate(imageBodyPart: MultipartBody.Part, onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            apiRepository.picCreate(
                imageBodyPart = imageBodyPart,
                userId = userInfo.data!!.id
            )

            onReturn()
        }
    }

    fun picDelete(picToken: String, onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            apiRepository.picDelete(
                userId = loginToken.data!!.userId,
                picToken = picToken
            )
            onReturn()
        }
    }

    fun collegeList(onError: (String) -> Unit) {
        viewModelScope.launch {
            collegeList = apiRepository.collegeList(onError)
        }
    }

    fun adminUserRegisterAllow(onReturn: (Boolean) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.adminUserRegisterAllow(onError)
            onReturn(a.notNullOrBlank(UserRegisterAllow()).userRegisterAllow)
        }
    }

}