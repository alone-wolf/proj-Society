package com.wh.society.api

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
import okio.FileNotFoundException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface ServerApi {
    companion object {
        //        private const val host = "39.103.143.157"
        private const val host = "192.168.50.115"
        private const val ssl = false
        private const val port = 5100
        private const val sioPort = 5200
        val baseUrl: String
            get() = "${if (ssl) "https://" else "http://"}${host}:${port}"
        val sioUrl: String
            get() = "${if (ssl) "https://" else "http://"}${host}:${sioPort}"


        fun userPicUrl(picToken: String): String {
            return "$baseUrl/pic/$picToken"
        }

        fun societyPicUrl(picToken: String): String {
            return "$baseUrl/society/picture/$picToken"
        }

        fun create(): ServerApi {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServerApi::class.java)
        }
    }

    // /society
//    @FormUrlEncoded
    @POST("/society/list")
    suspend fun societyList(
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<Society>

    @FormUrlEncoded
    @POST("/society/update")
    suspend fun societyUpdate(
        @Field("societyId") societyId: Int,
        @Field("name") name: String,
        @Field("openTimestamp") openTimestamp: String,
        @Field("describe") describe: String,
        @Field("college") college: String,
        @Field("bbsName") bbsName: String,
        @Field("bbsDescribe") bbsDescribe: String,
        @Field("iconUrl") iconUrl: String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String

    @FormUrlEncoded
    @POST("/society/info")
    suspend fun societyInfo(
        @Field("societyId") societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnObjectData<Society>

    @FormUrlEncoded
    @POST("/society/member/request/create")
    suspend fun societyMemberRequestCreate(
        @Field("userId") userId: Int,
        @Field("societyId") societyId: Int,
        @Field("request") request: String,
        @Field("isJoin") isJoin: Boolean,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String

    @FormUrlEncoded
    @POST("/society/member/request/list")
    suspend fun societyMemberRequestList(
        @Field("societyId") societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<SocietyMemberRequest>

    @FormUrlEncoded
    @POST("/society/joint")
    suspend fun societyJoint(
        @Field("id") societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<SocietyMember>

    @FormUrlEncoded
    @POST("/society/bbs/info")
    suspend fun societyBBSInfo(
        @Field("id") societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnObjectData<BBSInfo>

    @FormUrlEncoded
    @POST("/society/bbs/post/create")
    suspend fun societyBBSPostCreate(
        @Field("societyId") societyId: Int,
        @Field("userId") userId: Int,
        @Field("title") title: String,
        @Field("post") post: String,
        @Field("level") level: Int,
        @Field("deviceName") deviceName: String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String

    @FormUrlEncoded
    @POST("/society/bbs/post/by/id")
    suspend fun societyBBSPostById(
        @Field("postId") postId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnObjectData<Post>

    @FormUrlEncoded
    @POST("/society/bbs/post/delete")
    suspend fun societyBBSPostDelete(
        @Field("userId") userId: Int,
        @Field("postId") postId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String

    @FormUrlEncoded
    @POST("/society/bbs/post/reply/list")
    suspend fun societyBBSPostReplyList(
        @Field("postId") postId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<PostReply>

    @FormUrlEncoded
    @POST("/society/bbs/post/reply/create")
    suspend fun societyBBSPostReplyCreate(
        @Field("societyId") societyId: Int,
        @Field("postId") postId: Int,
        @Field("userId") userId: Int,
        @Field("reply") reply: String,
        @Field("deviceName") deviceName: String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String

    // /society/chat/inner
    @FormUrlEncoded
    @POST("/society/chat/inner/list")
    suspend fun societyChatInnerList(
        @Field("societyId") societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<SocietyChatMessage>

    @FormUrlEncoded
    @POST("/society/chat/inner/create")
    suspend fun societyChatInnerCreate(
        @Field("societyId") societyId: Int,
        @Field("userId") userId: Int,
        @Field("message") message: String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String

    @FormUrlEncoded
    @POST("/society/activity/list")
    suspend fun societyActivityList(
        @Field("societyId") societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<SocietyActivity>

    @FormUrlEncoded
    @POST("/society/activity/create")
    suspend fun societyActivityCreate(
        @Field("societyId") societyId: Int,
        @Field("deviceName") deviceName: String,
        @Field("title") title: String,
        @Field("level") level: Int,
        @Field("activity") activity: String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int
    ): String

    @FormUrlEncoded
    @POST("/society/activity/request/list")
    suspend fun societyActivityRequestList(
        @Field("societyId") societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int
    ): ReturnListData<SocietyActivityRequest>

    @FormUrlEncoded
    @POST("/society/activity/request/create")
    suspend fun societyActivityRequestCreate(
        @Field("societyId") societyId: Int,
        @Field("userId") userId: Int,
        @Field("request") request: String,
        @Field("isJoin") isJoin: Boolean,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int
    ): String

    @FormUrlEncoded
    @POST("/society/activity/member/list")
    suspend fun societyActivityMemberList(
        @Field("activityId") activityId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int
    ): ReturnListData<SocietyActivityMember>

    @FormUrlEncoded
    @POST("/society/picture/list")
    suspend fun societyPictureList(
        @Field("societyId") societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int
    ): ReturnListData<SocietyPicture>

    @Multipart
    @POST("/society/picture/create")
    suspend fun societyPictureCreate(
        @Part imageBodyPart: MultipartBody.Part,
        @Part societyId: MultipartBody.Part
    ): String

    @FormUrlEncoded
    @POST("/society/picture/delete")
    suspend fun societyPictureDelete(
        @Field("societyId") societyId: Int,
        @Field("picToken") picToken: String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int
    ): String

    @FormUrlEncoded
    @POST("/society/notice/list")
    suspend fun societyNoticeList(
        @Field("societyId") societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int
    ): ReturnListData<SocietyNotice>

    @FormUrlEncoded
    @POST("/society/notice/create")
    suspend fun societyNoticeCreate(
        @Field("societyId") societyId: Int,
        @Field("postUserid") postUserId: Int,
        @Field("title") title: String,
        @Field("notice") notice: String,
        @Field("permissionLevel") permissionLevel: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int
    ): String

    // /user

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun userRegister(
        @Field("username") username: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String
    ): String

    @FormUrlEncoded
    @POST("/user/info")
    suspend fun userInfo(
        @Field("userId") userId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnObjectData<UserInfo>

    @FormUrlEncoded
    @POST("/user/info/simple")
    suspend fun userInfoSimple(
        @Field("userId") userId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnObjectData<UserInfo>

    @FormUrlEncoded
    @POST("/user/info/update")
    suspend fun userInfoUpdate(
        @Field("userId") userId: Int,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("studentNumber") studentNumber: String,
        @Field("iconUrl") iconUrl: String,
        @Field("phone") phone: String,
        @Field("name") name: String,
        @Field("college") college: String,
        @Field("password") password: String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String

    @FormUrlEncoded
    @POST("/user/info/update/password")
    suspend fun userInfoUpdatePassword(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("studentNumber") studentNumber: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): String

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun userLogin(
        @Field("phoneStudentIdEmail") phoneStudentIdEmail: String,
        @Field("password") password: String
    ): ReturnObjectData<LoginReturn>

    @FormUrlEncoded
    @POST("/user/logout")
    suspend fun userLogout(
        @Field("userId") userId: Int,
        @Field("cookieToken") cookieToken: String
    ): String

    @FormUrlEncoded
    @POST("/user/joint")
    suspend fun userJoint(
        @Field("userId") userId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<SocietyMember>

    @FormUrlEncoded
    @POST("/user/join/request/list")
    suspend fun userJoinRequestList(
        @Field("userId") userId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<SocietyMemberRequest>

    @FormUrlEncoded
    @POST("/user/post/list")
    suspend fun userPostList(
        @Field("userId") userId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<Post>

    @FormUrlEncoded
    @POST("/user/post/reply/list")
    suspend fun userPostReplyList(
        @Field("userId") userId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<PostReply>

    @FormUrlEncoded
    @POST("/user/post/reply/list")
    suspend fun userPostReplyList(
        @Field("userId") userId: Int,
        @Field("postId") postId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<PostReply>

    @FormUrlEncoded
    @POST("/user/chat/private/create")
    suspend fun userChatPrivateCreate(
        @Field("userId") userId: Int,
        @Field("opUserId") opUserId: Int,
        @Field("message") message: String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String

    @FormUrlEncoded
    @POST("/user/chat/private/list")
    suspend fun userChatPrivateList(
        @Field("userId") userId: Int,
        @Field("opUserId") opUserId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<UserChatPrivate>


    // /pic
    @FormUrlEncoded
    @POST("/pic/list")
    suspend fun picList(
        @Field("userId") userId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<UserPicture>

    @Multipart
    @POST("/pic/create")
    suspend fun picCreate(
        @Part imageBodyPart: MultipartBody.Part,
        @Part userId: MultipartBody.Part
    ): String

    @FormUrlEncoded
    @POST("/pic/delete")
    suspend fun picDelete(
        @Field("userId") userId: Int,
        @Field("picToken") picToken: String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String

    @POST("/college/list")
    suspend fun collegeList(): ReturnListData<College>


    @POST("/admin/user/register/allow")
    suspend fun adminUserRegisterAllow(): ReturnObjectData<UserRegisterAllow>
}