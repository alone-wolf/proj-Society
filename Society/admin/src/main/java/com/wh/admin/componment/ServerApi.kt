package com.wh.admin.componment

import com.wh.admin.data.society.Society
import com.wh.admin.data.society.SocietyChatMessage
import com.wh.admin.data.society.SocietyMember
import com.wh.admin.data.society.SocietyPicture
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.*
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.data.society.bbs.PostReply
import com.wh.society.api.data.user.UserChatPrivate
import com.wh.admin.data.user.UserInfo
import com.wh.admin.data.user.UserPicture
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ServerApi {
    companion object {
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

    @POST("/all/user")
    suspend fun allUser(): ReturnListData<UserInfo>

    @FormUrlEncoded
    @POST("/admin/user/delete")
    suspend fun adminUserDelete(
        @Field("userId") userId: Int
    ): String

    @FormUrlEncoded
    @POST("/all/user/post")
    suspend fun allUserPost(
        @Field("userId") userId: Int
    ): ReturnListData<Post>

    @FormUrlEncoded
    @POST("/all/user/reply")
    suspend fun allUserReply(
        @Field("userId") userId: Int
    ): ReturnListData<PostReply>

    @FormUrlEncoded
    @POST("/all/user/society/member")
    suspend fun allUserSocietyMember(
        @Field("userId") userId: Int
    ): ReturnListData<SocietyMember>

//    @POST("/all/user/pic")
//    suspend fun allUserPic(): ReturnListData<UserPicture>

    @POST("/all/society")
    suspend fun allSociety(): ReturnListData<Society>

    @FormUrlEncoded
    @POST("/all/society/post")
    suspend fun allSocietyPost(
        @Field("societyId") societyId: Int
    ): ReturnListData<Post>


    @FormUrlEncoded
    @POST("/all/post/reply")
    suspend fun allPostReply(
        @Field("postId") postId: Int
    ): ReturnListData<PostReply>


    @POST("/admin/user/create")
    suspend fun adminUserCreate(@Body userInfo: RequestBody): String

    @POST("/admin/user/register/allow")
    suspend fun adminUserRegisterAllow(): String

    @POST("/admin/user/register/allow/switch")
    suspend fun adminUserRegisterAllowSwitch(): String


//    @POST("/all/post/reply")
//    suspend fun allPostReply(): ReturnListData<PostReply>

//    @POST("/all/society/pic")
//    suspend fun allSocietyPic(): ReturnListData<SocietyPicture>

//    @POST("/all/society/member")
//    suspend fun allSocietyMember(): ReturnListData<SocietyMember>

//    @POST("/all/society/member/request")
//    suspend fun allSocietyMemberRequest(): ReturnListData<SocietyMemberRequest>
//
//    @POST("/all/society/activity")
//    suspend fun allSocietyActivity(): ReturnListData<SocietyActivity>

    @POST("/all/society/activity/member/request")
    suspend fun allSocietyActivityMemberRequest(): ReturnListData<SocietyActivityRequest>

    @POST("/all/society/activity/member")
    suspend fun allSocietyActivityMember(): ReturnListData<SocietyActivityMember>

    @POST("/all/society/chat/inner")
    suspend fun allSocietyChatInner(): ReturnListData<SocietyChatMessage>

    @POST("/all/society/notice")
    suspend fun allSocietyNotice(): ReturnListData<SocietyNotice>

    @POST("/all/user/chat/private")
    suspend fun allUserChatPrivate(): ReturnListData<UserChatPrivate>

}