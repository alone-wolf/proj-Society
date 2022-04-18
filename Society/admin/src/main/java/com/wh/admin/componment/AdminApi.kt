package com.wh.admin.componment

import com.wh.admin.data.ReturnListData
import com.wh.admin.data.ReturnObjectData
import com.wh.admin.data.society.*
import com.wh.admin.data.society.bbs.Post
import com.wh.admin.data.society.bbs.PostReply
import com.wh.admin.data.user.UserInfo
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface AdminApi {
    companion object {
        private const val host = "192.168.50.115"
        private const val ssl = false
        private const val port = 5100
        private val baseUrl: String
            get() = "${if (ssl) "https://" else "http://"}${host}:${port}"


        fun userPicUrl(picToken: String): String {
            return "$baseUrl/pic/$picToken"
        }

        fun societyPicUrl(picToken: String): String {
            return "$baseUrl/society/picture/$picToken"
        }

        fun create(): AdminApi {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AdminApi::class.java)
        }
    }

    @FormUrlEncoded
    @POST("/admin/user/delete")
    suspend fun adminUserDelete(
        @Field("userId") userId: Int
    ): String

    @FormUrlEncoded
    @POST("/admin/user/byid")
    suspend fun adminUserById(
        @Field("userId") userId: Int
    ): ReturnObjectData<UserInfo>


    @POST("/admin/user/create")
    suspend fun adminUserCreate(@Body userInfo: RequestBody): String

    @POST("/admin/user/update")
    suspend fun adminUserUpdate(@Body userInfo: RequestBody): String

    @POST("/admin/user/register/allow")
    suspend fun adminUserRegisterAllow(): String

    @POST("/admin/user/register/allow/switch")
    suspend fun adminUserRegisterAllowSwitch(): String

    @POST("/admin/society/create")
    suspend fun adminSocietyCreate(@Body society: RequestBody): String

    @POST("/admin/society/update")
    suspend fun adminSocietyUpdate(@Body society: RequestBody): String

    @FormUrlEncoded
    @POST("/admin/society/delete")
    suspend fun adminSocietyDelete(
        @Field("societyId") societyId: Int
    ): String

    @FormUrlEncoded
    @POST("/admin/society/member/delete")
    suspend fun adminSocietyMemberDelete(
        @Field("userId") userId: Int,
        @Field("societyId") societyId: Int
    ): String

    @FormUrlEncoded
    @POST("/admin/society/member/create")
    suspend fun adminSocietyMemberCreate(
        @Field("userId") userId: Int,
        @Field("societyId") societyId: Int,
        @Field("permissionLevel") permissionLevel: Int
    ): String


    @FormUrlEncoded
    @POST("/admin/society/member/update/permission")
    suspend fun adminSocietyMemberUpdatePermission(
        @Field("userId") userId: Int,
        @Field("societyId") societyId: Int,
        @Field("permissionLevel") permissionLevel: Int
    ): String

    @FormUrlEncoded
    @POST("/admin/society/activity/member/delete")
    suspend fun adminSocietyActivityMemberDelete(
        @Field("memberId") memberId: Int
    )


    @FormUrlEncoded
    @POST("/admin/post/delete")
    suspend fun adminPostDelete(
        @Field("postId") postId: Int
    ): String

    @FormUrlEncoded
    @POST("/admin/post/reply/delete")
    suspend fun adminPostReplyDelete(
        @Field("replyId") replyId: Int
    ): String




    @POST("/all/user")
    suspend fun allUser(): ReturnListData<UserInfo>

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

    @FormUrlEncoded
    @POST("/all/user/society/member/request")
    suspend fun allUserSocietyMemberRequest(
        @Field("userId") userId: Int
    ): ReturnListData<SocietyMemberRequest>

    @FormUrlEncoded
    @POST("/all/user/society/activity/member")
    suspend fun allUserSocietyActivityMember(
        @Field("userId") userId: Int,
    ): ReturnListData<SocietyActivityMember>

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
    @POST("/all/society/reply")
    suspend fun allSocietyReply(
        @Field("societyId") societyId: Int
    ): ReturnListData<PostReply>


    @FormUrlEncoded
    @POST("/all/post/reply")
    suspend fun allPostReply(
        @Field("postId") postId: Int
    ): ReturnListData<PostReply>

    @FormUrlEncoded
    @POST("/all/society/member")
    suspend fun allSocietyMember(
        @Field("societyId") societyId: Int
    ): ReturnListData<SocietyMember>

    @FormUrlEncoded
    @POST("/all/society/member/request")
    suspend fun allSocietyMemberRequest(
        @Field("societyId") societyId: Int
    ): ReturnListData<SocietyMemberRequest>

    @FormUrlEncoded
    @POST("/all/society/activity")
    suspend fun allSocietyActivity(
        @Field("societyId") societyId: Int
    ): ReturnListData<SocietyActivity>

    @POST("/all/activity")
    suspend fun allActivity(): ReturnListData<SocietyActivity>

}