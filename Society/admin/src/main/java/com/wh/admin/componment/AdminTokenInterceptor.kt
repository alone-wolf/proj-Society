package com.wh.admin.componment

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class AdminTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Admin-Token", "asdfghjkjhgfdchvbjkuyfvghjb")
                .build()
        )
    }
}