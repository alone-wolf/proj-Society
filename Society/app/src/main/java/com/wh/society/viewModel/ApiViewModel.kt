package com.wh.society.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wh.society.api.data.*
import com.wh.society.api.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ApiViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    var societyList by mutableStateOf(ReturnListData.blank<Society>())

    var bbsList by mutableStateOf(emptyList<BBS>())

    var userInfo by mutableStateOf(ReturnObjectData.blank<UserInfo>())

    var loginToken by mutableStateOf(ReturnObjectData.blank<LoginReturn>())

    var picDataList by mutableStateOf(ReturnListData.blank<PicData>())
    var collegeList by mutableStateOf(ReturnListData.blank<College>())

    val TAG = "WH_"

    fun login() {}

    fun societyList() {
        viewModelScope.launch {
            societyList = apiRepository.societyList(
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = loginToken.data!!.userId
            )
            val c: MutableList<String> = mutableListOf()
            val b: MutableList<BBS> = mutableListOf()
            societyList.data.forEach {
                c.add(it.college)
                b.add(BBS.fromSociety(it))
            }
//            collegeList = c.distinct()
            bbsList = b
            Log.d(TAG, "societyList: ${societyList.data.size}")
//            Log.d(TAG, "collegeList: ${collegeList.size}")
            Log.d(TAG, "bbsList: ${bbsList.size}")
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
            userInfo = apiRepository.userInfoSimple(
                userId = userId,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn(userInfo.data!!)
        }
    }

    fun userInfoUpdate(
        username: String,
        email: String,
        studentNumber: String,
        iconUrl: String,
        phone: String,
        name: String,
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
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
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

//    fun userSIOConnected() {
//        viewModelScope.launch {
//            val a = apiRepository.userSIOConnected(
//                userId = userInfo.data!!.id,
//                cookieToken = loginToken.data!!.cookieToken,
//                authUserId = userInfo.data!!.id
//            )
//            Log.d(TAG, "userSIOConnected: $a")
//        }
//    }

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
//            Log.d(TAG, "societyJoinRequest: $a")
            onReturn()
        }
    }

    fun societyMemberRequestList(
        societyId: Int,
        onReturn: CoroutineScope.(ReturnListData<MemberRequest>) -> Unit
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
        onReturn: CoroutineScope.(ReturnListData<SocietyJoint>) -> Unit
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
                authUserId = userInfo.data!!.id
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

    fun userJoint(userId: Int, onReturn: CoroutineScope.(ReturnListData<SocietyJoint>) -> Unit) {
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
        onReturn: CoroutineScope.(ReturnListData<MemberRequest>) -> Unit
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
        onReturn: CoroutineScope.(ReturnListData<ChatPrivate>) -> Unit
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

    fun picDelete(userId: Int, picToken: String, onReturn: CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            val a = apiRepository.picDelete(
                userId = userId,
                picToken = picToken,
                cookieToken = loginToken.data!!.cookieToken,
                authUserId = userInfo.data!!.id
            )
            onReturn()
        }
    }

    fun collegeList() {
        viewModelScope.launch {
            collegeList = apiRepository.collegeList()
        }
    }

}