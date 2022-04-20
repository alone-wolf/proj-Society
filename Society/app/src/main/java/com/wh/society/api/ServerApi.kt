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
import com.wh.society.navigation.GlobalNavPage
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response
import okio.FileNotFoundException
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.lang.reflect.Type

interface ServerApi {
    companion object {
        //private const val host = "192.168.1.7"
        private const val host = "192.168.50.115"
        private const val ssl = false
        private const val port = 5100
        private const val sioPort = 5200
        private val baseUrl: String
            get() = "${if (ssl) "https://" else "http://"}${host}:${port}"
        val sioUrl: String
            get() = "${if (ssl) "https://" else "http://"}${host}:${sioPort}"


        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())


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
                .client(OkHttpClient.Builder().addInterceptor(TokenInterceptor()).build())
                .build()
                .create(ServerApi::class.java)
        }
    }


    // /society
    @POST("/society/list")
    suspend fun societyList(): ReturnListData<Society>

    @POST("/society/update")
    suspend fun societyUpdate(@Body society: RequestBody)

    @FormUrlEncoded
    @POST("/society/info")
    suspend fun societyInfo(
        @Field("societyId") societyId: Int
    ): ReturnObjectData<Society>

    // // 获取发出请求用户的指定社团的成员信息
    @FormUrlEncoded
    @POST("/society/member/by/society/id")
    suspend fun societyMemberBySocietyId(
        @Field("societyId") societyId: Int
    ): ReturnObjectData<SocietyMember>

    @FormUrlEncoded
    @POST("/society/member/request/create")
    suspend fun societyMemberRequestCreate(
        @Field("userId") userId: Int,
        @Field("societyId") societyId: Int,
        @Field("request") request: String,
        @Field("isJoin") isJoin: Boolean
    ): String

    @FormUrlEncoded
    @POST("/society/member/request/list")
    suspend fun societyMemberRequestList(
        @Field("societyId") societyId: Int
    ): ReturnListData<SocietyMemberRequest>

    @FormUrlEncoded
    @POST("/society/member/request/deal")
    suspend fun societyMemberRequestDeal(
        @Field("requestId")requestId:Int,
        @Field("isAgreed") isAgreed:Boolean
    )

    @FormUrlEncoded
    @POST("/society/member/list/by/society")
    suspend fun societyMemberListBySociety(
        @Field("societyId")societyId:Int
    ):ReturnListData<SocietyMember>

    @FormUrlEncoded
    @POST("/society/member/update/permissionLevel")
    suspend fun societyMemberUpdatePermissionLevel(
        @Field("memberId")memberId:Int,
        @Field("permissionLevel")permissionLevel:Int
    )

    @FormUrlEncoded
    @POST("/society/joint")
    suspend fun societyJoint(
        @Field("id") societyId: Int
    ): ReturnListData<SocietyMember>

    @FormUrlEncoded
    @POST("/society/bbs/info")
    suspend fun societyBBSInfo(
        @Field("id") societyId: Int
    ): ReturnObjectData<BBSInfo>

    @POST("/society/bbs/post/create")
    suspend fun societyBBSPostCreate(
        @Body post: RequestBody
    ): String

    @FormUrlEncoded
    @POST("/society/bbs/post/by/id")
    suspend fun societyBBSPostById(
        @Field("postId") postId: Int
    ): ReturnObjectData<Post>

    @FormUrlEncoded
    @POST("/society/bbs/post/delete")
    suspend fun societyBBSPostDelete(
        @Field("userId") userId: Int,
        @Field("postId") postId: Int
    ): String

    @FormUrlEncoded
    @POST("/society/bbs/post/reply/list")
    suspend fun societyBBSPostReplyList(
        @Field("postId") postId: Int
    ): ReturnListData<PostReply>

    @POST("/society/bbs/post/reply/create")
    suspend fun societyBBSPostReplyCreate(
        @Body postReply: RequestBody
    ): String

    @FormUrlEncoded
    @POST("/society/bbs/post/reply/delete")
    suspend fun societyBBSPostReplyDelete(
        @Field("postReplyId") postReplyId: Int
    )

    // /society/chat/inner
    @FormUrlEncoded
    @POST("/society/chat/inner/list")
    suspend fun societyChatInnerList(
        @Field("societyId") societyId: Int
    ): ReturnListData<SocietyChatMessage>

    @FormUrlEncoded
    @POST("/society/chat/inner/create")
    suspend fun societyChatInnerCreate(
        @Field("societyId") societyId: Int,
        @Field("userId") userId: Int,
        @Field("message") message: String
    )

    @FormUrlEncoded
    @POST("/society/chat/inner/delete")
    suspend fun societyChatInnerDelete(
        @Field("chatId") chatId:Int
    )

    @FormUrlEncoded
    @POST("/society/chat/inner/clear")
    suspend fun societyChatInnerClear(
        @Field("societyId") societyId: Int,
    )

    @FormUrlEncoded
    @POST("/society/activity/list")
    suspend fun societyActivityList(
        @Field("societyId") societyId: Int
    ): ReturnListData<SocietyActivity>

    @POST("/society/activity/create")
    suspend fun societyActivityCreate(
        @Body activity: RequestBody
    )

    @FormUrlEncoded
    @POST("/society/activity/delete")
    suspend fun societyActivityDelete(
        @Field("activityId") activityId: Int
    )

    @FormUrlEncoded
    @POST("/society/activity/join")
    suspend fun societyActivityJoin(
        @Field("activityId") activityId: Int,
        @Field("userId")userId: Int
    )

    @FormUrlEncoded
    @POST("/society/activity/leave")
    suspend fun societyActivityLeave(
        @Field("activityId") activityId: Int,
        @Field("userId") userId: Int
    )

    @FormUrlEncoded
    @POST("/society/activity/member")
    suspend fun societyActivityMember(
        @Field("activityId") activityId: Int
    ): ReturnListData<SocietyActivityMember>


    @FormUrlEncoded
    @POST("/society/activity/member/delete")
    suspend fun societyActivityMemberDelete(
        @Field("memberId") memberId:Int
    )

    @FormUrlEncoded
    @POST("/society/picture/list")
    suspend fun societyPictureList(
        @Field("societyId") societyId: Int
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
        @Field("picToken") picToken: String
    )

    @FormUrlEncoded
    @POST("/society/notice/list")
    suspend fun societyNoticeList(
        @Field("societyId") societyId: Int
    ): ReturnListData<SocietyNotice>

    @FormUrlEncoded
    @POST("/society/notice/delete")
    suspend fun societyNoticeDelete(
        @Field("noticeId") noticeId:Int
    )

    @POST("/society/notice/create")
    suspend fun societyNoticeCreate(@Body notice: RequestBody)

    // /user

    @FormUrlEncoded
    @POST("/user/info")
    suspend fun userInfo(
        @Field("userId") userId: Int
    ): ReturnObjectData<UserInfo>

    @FormUrlEncoded
    @POST("/user/info/simple")
    suspend fun userInfoSimple(
        @Field("userId") userId: Int
    ): ReturnObjectData<UserInfo>

    @POST("/user/info/update")
    suspend fun userInfoUpdate(@Body userInfo: RequestBody)

//    @FormUrlEncoded
//    @POST("/user/login")
//    suspend fun userLogin(
//        @Field("phoneStudentIdEmail") phoneStudentIdEmail: String,
//        @Field("password") password: String
//    ): ReturnObjectData<LoginReturn>

    @FormUrlEncoded
    @POST("/user/logout")
    suspend fun userLogout(
        @Field("userId") userId: Int
    ): String

    @FormUrlEncoded
    @POST("/user/joint")
    suspend fun userJoint(
        @Field("userId") userId: Int
    ): ReturnListData<SocietyMember>

    @FormUrlEncoded
    @POST("/user/join/request/list")
    suspend fun userJoinRequestList(
        @Field("userId") userId: Int
    ): ReturnListData<SocietyMemberRequest>

    @FormUrlEncoded
    @POST("/user/post/list")
    suspend fun userPostList(
        @Field("userId") userId: Int
    ): ReturnListData<Post>

    @FormUrlEncoded
    @POST("/user/post/reply/list")
    suspend fun userPostReplyList(
        @Field("userId") userId: Int
    ): ReturnListData<PostReply>

//    @FormUrlEncoded
//    @POST("/user/post/reply/list")
//    suspend fun userPostReplyList(
//        @Field("userId") userId: Int,
//        @Field("postId") postId: Int
//    ): ReturnListData<PostReply>

    @FormUrlEncoded
    @POST("/user/chat/private/create")
    suspend fun userChatPrivateCreate(
        @Field("userId") userId: Int,
        @Field("opUserId") opUserId: Int,
        @Field("message") message: String
    )

    @FormUrlEncoded
    @POST("/user/chat/private/list")
    suspend fun userChatPrivateList(
        @Field("userId") userId: Int,
        @Field("opUserId") opUserId: Int
    ): ReturnListData<UserChatPrivate>


    // /pic
    @FormUrlEncoded
    @POST("/pic/list")
    suspend fun picList(
        @Field("userId") userId: Int
    ): ReturnListData<UserPicture>

    @Multipart
    @POST("/pic/create")
    suspend fun picCreate(
        @Part imageBodyPart: MultipartBody.Part,
        @Part userId: MultipartBody.Part
    )

    @FormUrlEncoded
    @POST("/pic/delete")
    suspend fun picDelete(
        @Field("userId") userId: Int,
        @Field("picToken") picToken: String
    )

    @POST("/college/list")
    suspend fun collegeList(): ReturnListData<College>
}