package com.wh.society.api

import android.util.Log
import com.wh.society.componment.SettingStore
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class TokenInterceptor : Interceptor {

    private val settingStore: SettingStore?
        get() = SettingStore.unsafeInstance()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

//        val request = settingStore?.let {
//            chain.request().newBuilder()
//                    .addHeader("Cookie-Token", it.token)
//                    .build()
//        }?:chain.request()

        val ss = settingStore
        val request = if (ss == null) {
            val r = chain.request()
            Log.d("WH_", "intercept: error ${r.url}")
            r
        } else {
            chain.request().newBuilder()
                .addHeader("Cookie-Token", ss.token)
                .build()
        }


        val response: Response = chain.proceed(request)

        /*这里可以获取响应体 */
//        val responseBody: ResponseBody = response.body()
        return response
    }
}