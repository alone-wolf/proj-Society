package com.wh.society.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ApiViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    var societyList by mutableStateOf(ReturnListData.blank<Society>())

    var bbsList by mutableStateOf(emptyList<BBS>())

    var userInfo by mutableStateOf(ReturnObjectData.blank<UserInfo>())

    var loginToken by mutableStateOf(ReturnObjectData.blank<LoginReturn>())

    var picDataList by mutableStateOf(ReturnListData.blank<UserPicture>())
    var collegeList by mutableStateOf(ReturnListData.blank<College>())

    val TAG = "WH_"

    fun societyList(onError: (String) -> Unit = {}) {
        viewModelScope.launch {
            societyList = apiRepository.societyList(
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = loginToken.data!!.userId,
                onError = onError
            )
            val b: MutableList<BBS> = mutableListOf()
            societyList.data.forEach {
                b.add(BBS.fromSociety(it))
            }
            bbsList = b
        }
    }

    fun societyUpdate(
        societyId: Int,
        name: String,
        openTimestamp: String,
        describe: String,
        college: String,
        bbsName: String,
        bbsDescribe: String,
        iconUrl: String,
        onReturn: CoroutineScope.() -> Unit,
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyUpdate(
                societyId = societyId,
                name = name,
                openTimestamp = openTimestamp,
                describe = describe,
                college = college,
                bbsName = bbsName,
                bbsDescribe = bbsDescribe,
                iconUrl = iconUrl,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = loginToken.data!!.userId,
                onError = onError
            )
            Log.d(TAG, "societyUpdate: $a")
            onReturn()
        }
    }

    fun societyInfo(
        societyId: Int,
        onError: (String) -> Unit = {},
        onReturn: CoroutineScope.(ReturnObjectData<Society>) -> Unit,
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyInfo(
                societyId = societyId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = loginToken.data!!.userId,
                onError = onError
            )
            onReturn(a)
        }
    }

    fun userInfo(onReturn: CoroutineScope.(UserInfo) -> Unit) {
        viewModelScope.launch {
            loginToken.data?.cookieToken?.let {
                userInfo = apiRepository.userInfo(
                    userId = loginToken.data!!.userId,
                    cookieToken = it,
                    authUserId = loginToken.data!!.userId
                )
                userInfo.data?.let { it ->
                    onReturn(it)
                }
                return@launch
            }
            Log.e(TAG, "userInfo: token is blank")
        }
    }

    fun userInfoSimple(userId: Int, onReturn: CoroutineScope.(UserInfo) -> Unit) {
        viewModelScope.launch {
            val userInfo = apiRepository.userInfoSimple(
                userId = userId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(userInfo.data!!)
        }
    }

    fun userInfoUpdate(u: UserInfo, onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.userInfoUpdate(
                userId = userInfo.data!!.id,
                username = u.username,
                email = u.email,
                studentNumber = u.studentNumber,
                iconUrl = u.iconUrl,
                phone = u.phone,
                name = u.name, college = u.college,
                password = u.password,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn()
        }
    }

    fun userInfoUpdate(
        username: String,
        email: String,
        studentNumber: String,
        iconUrl: String,
        phone: String,
        name: String,
        password: String,
        college: String,
        onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.userInfoUpdate(
                userId = userInfo.data!!.id,
                username = username,
                email = email,
                studentNumber = studentNumber,
                iconUrl = iconUrl,
                phone = phone,
                name = name, college = college,
                password = password,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
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
        onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.userInfoUpdatePassword(
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
        onReturn: CoroutineScope.(LoginReturn) -> Unit = {}
    ) {
        viewModelScope.launch {
            loginToken = apiRepository.userLogin(phoneStudentIdEmail, password)
            loginToken.data?.let {
                onReturn(it)
            }
        }
    }

    fun userLogout(onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            apiRepository.userLogout(
                userId = userInfo.data!!.id,
                cookieToken = loginToken.data!!.cookieToken
            )
            onReturn()
        }
    }

    fun societyMemberRequestCreate(
        userId: Int,
        societyId: Int,
        request: String,
        isJoin: Boolean,
        onReturn: CoroutineScope.() -> Unit = {}
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyMemberRequestCreate(
                userId = userId,
                societyId = societyId,
                request = request,
                isJoin = isJoin,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn()
        }
    }

    fun societyMemberRequestList(
        societyId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyMemberRequest>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyMemberRequestList(
                societyId = societyId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            Log.d(TAG, "societyMemberRequestList: ${a.data.size}")
            onReturn(a)
        }
    }

    fun societyJoint(
        societyId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyMember>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyJoint(
                societyId = societyId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(a)
        }
    }

    fun societyBBSInfo(
        societyId: Int,
        onReturn: CoroutineScope.(ReturnObjectData<BBSInfo>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyBBSInfo(
                societyId = societyId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id,
//                authUserId = 1
            )
            onReturn(a)
        }
    }

    fun societyBBSPostCreate(
        societyId: Int,
        userId: Int,
        title: String,
        post: String,
        level: Int,
        deviceName: String,
        onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyBBSPostCreate(
                societyId = societyId,
                userId = userId,
                title = title,
                post = post,
                level = level,
                deviceName = deviceName,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn()
        }
    }

    fun societyBBSPostById(postId: Int, onReturn: CoroutineScope.(ReturnObjectData<Post>) -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.societyBBSPostById(
                postId = postId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(a)
        }
    }

    fun societyBBSPostDelete(userId: Int, postId: Int, onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            val a =
                apiRepository.societyBBSPostDelete(
                    userId = userId,
                    postId = postId,
                    cookieToken = loginToken.data!!.cookieToken,
                    authUserId = userInfo.data!!.id
                )
            onReturn()
        }
    }

    fun societyBBSPostReplyList(
        postId: Int,
        onReturn: CoroutineScope.(ReturnListData<PostReply>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyBBSPostReplyList(
                postId = postId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            Log.d(TAG, "societyBBSPostReplyList: ${a.data.size}")
            onReturn(a)
        }
    }

    fun societyBBSPostReplyCreate(
        societyId: Int,
        postId: Int,
        userId: Int,
        reply: String,
        deviceName: String,
        onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyBBSPostReplyCreate(
                societyId = societyId,
                postId = postId,
                userId = userId,
                reply = reply,
                deviceName = deviceName,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn()
        }
    }

    fun societyChatInnerList(
        societyId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyChatMessage>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyChatInnerList(
                societyId = societyId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(a)
        }
    }

    fun societyChatInnerCreate(
        societyId: Int,
        userId: Int,
        message: String,
        onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyChatInnerCreate(
                societyId = societyId,
                userId = userId,
                message = message,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn()
        }
    }

    fun societyActivityList(
        societyId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyActivity>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyActivityList(
                societyId = societyId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(a)
        }
    }

    fun societyActivityCreate(
        societyId: Int,
        deviceName: String,
        title: String,
        activity: String,
        level: Int,
        onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyActivityCreate(
                societyId = societyId,
                deviceName = deviceName,
                title = title,
                activity = activity,
                level = level,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn()
        }
    }

    fun societyActivityRequestList(
        societyId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyActivityRequest>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyActivityRequestList(
                societyId = societyId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(a)
        }
    }

    fun societyActivityRequestCreate(
        societyId: Int,
        userId: Int,
        request: String,
        isJoin: Boolean,
        onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyActivityRequestCreate(
                societyId = societyId,
                userId = userId,
                request = request,
                isJoin = isJoin,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn()
        }
    }

    fun societyActivityMemberList(
        activityId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyActivityMember>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyActivityMemberList(
                activityId = activityId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
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
                societyId = societyId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
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
                picToken,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
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
                societyId = societyId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(a)
        }
    }

    fun societyNoticeCreate(
        societyId: Int,
        postUserId: Int,
        title: String,
        notice: String,
        permissionLevel: Int,
        onReturn: CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.societyNoticeCreate(
                societyId = societyId,
                postUserId = postUserId,
                title = title,
                notice = notice,
                permissionLevel = permissionLevel,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn()
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
            val a = apiRepository.userRegister(username, phone, email, name, password)
            onReturn()
        }
    }

    fun userJoint(userId: Int, onReturn: CoroutineScope.(ReturnListData<SocietyMember>) -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.userJoint(
                userId = userId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(a)
            Log.d(TAG, "userJoint: ${a.data.size}")
        }
    }

    fun userJoinRequestList(
        userId: Int,
        onReturn: CoroutineScope.(ReturnListData<SocietyMemberRequest>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.userJoinRequestList(
                userId = userId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(a)
            Log.d(TAG, "userJoinRequestList: ${a.data.size}")
        }
    }

    fun userPostList(userId: Int, onReturn: CoroutineScope.(ReturnListData<Post>) -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.userPostList(
                userId = userId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(a)
            Log.d(TAG, "userPostList: ${a.data.size}")
        }
    }

    fun userPostReplyList(
        userId: Int,
        onReturn: CoroutineScope.(ReturnListData<PostReply>) -> Unit
    ) {
        viewModelScope.launch {
            val a = apiRepository.userPostReplyList(
                userId = userId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(a)
            Log.d(TAG, "userPostReplyList: ${a.data.size}")
        }
    }

//    fun userPostReplyList(
//        userId: Int,
//        postId: Int,
//        onReturn: CoroutineScope.(ReturnListData<PostReply>) -> Unit
//    ) {
//        viewModelScope.launch {
//            val a = apiRepository.userPostReplyList(
//                userId = userId,
//                postId = postId,
//                cookieToken = loginToken.data!!.cookieToken,
//                authUserId = userInfo.data!!.id
//            )
//            onReturn(a)
//            Log.d(TAG, "userPostReplyList: ${a.data.size}")
//        }
//    }

    fun userChatPrivateCreate(opUserId: Int, message: String, onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.userChatPrivateCreate(
                userId = userInfo.data!!.id,
                opUserId = opUserId,
                message = message,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = loginToken.data!!.userId
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
                opUserId = opUserId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = loginToken.data!!.userId
            )
            onReturn(a)
        }
    }

    fun picList() {
        viewModelScope.launch {
            picDataList = apiRepository.picList(
                userId = loginToken.data!!.userId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = loginToken.data!!.userId
            )
            Log.d(TAG, "picList: ${picDataList.data.size}")
        }
    }

//    fun picList(userId: Int, onReturn: CoroutineScope.(ReturnListData<PicData>) -> Unit) {
//        viewModelScope.launch {
//            val a = apiRepository.picList(
//                userId = userId,
//                cookieToken = loginToken.data!!.cookieToken,
//                authUserId = userInfo.data!!.id
//            )
//            onReturn(a)
//        }
//    }

    fun picCreate(imageBodyPart: MultipartBody.Part, onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.picCreate(
                imageBodyPart = imageBodyPart,
                userId = userInfo.data!!.id
            )

            onReturn()
        }
    }

    fun picDelete(picToken: String, onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.picDelete(
                userId = loginToken.data!!.userId,
                picToken = picToken,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = loginToken.data!!.userId
            )
            onReturn()
        }
    }

    fun picDelete(userId: Int, picToken: String, onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.picDelete(
                userId = userId,
                picToken = picToken,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = loginToken.data!!.userId
            )
            onReturn()
        }
    }

    fun collegeList() {
        viewModelScope.launch {
            collegeList = apiRepository.collegeList()
        }
    }

    fun adminUserRegisterAllow(onReturn: CoroutineScope.(Boolean) -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.adminUserRegisterAllow()
            onReturn(a.notNullOrBlank(UserRegisterAllow()).userRegisterAllow)
        }
    }

}