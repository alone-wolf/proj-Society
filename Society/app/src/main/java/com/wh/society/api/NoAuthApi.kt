package com.wh.society.api

import com.wh.society.api.data.*
import com.wh.society.api.data.admin.UserRegisterAllow
import com.wh.society.api.data.user.UserInfo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface NoAuthApi {

    companion object{
        fun create(): NoAuthApi {
            return ServerApi.builder.build().create(NoAuthApi::class.java)
        }
    }

    // /user

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun userRegister(
        @Field("username") username: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String
    )

//    @FormUrlEncoded
//    @POST("/user/info")
//    suspend fun userInfo(
//        @Field("userId") userId: Int
//    ): ReturnObjectData<UserInfo>

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


    @POST("/admin/user/register/allow")
    suspend fun adminUserRegisterAllow(): ReturnObjectData<UserRegisterAllow>
}