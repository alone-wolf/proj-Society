package com.wh.society.api

import com.wh.society.api.data.*
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface ServerApi {
    companion object {
        private const val host = "192.168.50.115"
        private const val ssl = false
        private const val port = 5001
        private const val sioPort = 5002
        val baseUrl:String
        get() = "${if (ssl) "https://" else "http://"}${host}:${port}"
        val sioUrl:String
            get() = "${if (ssl) "https://" else "http://"}${host}:${sioPort}"


        fun picUrl(picToken: String): String {
            return "$baseUrl/pic/$picToken"
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
    @POST("/society/member/request/create")
    suspend fun societyMemberRequestCreate(
        @Field("userId") userId: Int,
        @Field("societyId") societyId: Int,
        @Field("request") request: String,
        @Field("isJoin") isJoin:Boolean,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String

    @FormUrlEncoded
    @POST("/society/member/request/list")
    suspend fun societyMemberRequestList(
        @Field("societyId")societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ):ReturnListData<MemberRequest>

    @FormUrlEncoded
    @POST("/society/joint")
    suspend fun societyJoint(
        @Field("id") societyId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<SocietyJoint>

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

    // /user

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
    ): ReturnListData<SocietyJoint>

    @FormUrlEncoded
    @POST("/user/join/request/list")
    suspend fun userJoinRequestList(
        @Field("userId") userId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<MemberRequest>

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
        @Field("message") message:String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ):String

    @FormUrlEncoded
    @POST("/user/chat/private/list")
    suspend fun userChatPrivateList(
        @Field("userId") userId: Int,
        @Field("opUserId") opUserId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ):ReturnListData<ChatPrivate>


    // /pic
    @FormUrlEncoded
    @POST("/pic/list")
    suspend fun picList(
        @Field("userId") userId: Int,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): ReturnListData<PicData>

    @Multipart
    @POST("/pic/create")
    suspend fun picCreate(
        @Part imageBodyPart: MultipartBody.Part,
        @Part userId: MultipartBody.Part
    ):String

    @FormUrlEncoded
    @POST("/pic/delete")
    suspend fun picDelete(
        @Field("userId") userId: Int,
        @Field("picToken") picToken: String,
        @Header("cookieToken") cookieToken: String,
        @Header("authUserId") authUserId: Int,
    ): String
}