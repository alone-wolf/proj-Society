package com.wh.society

import android.app.Application
import android.util.Log
import com.wh.society.api.ServerApi
import com.wh.society.api.db.AppDatabase
import com.wh.society.componment.RepositoryKeeper
import com.wh.society.componment.StoreKeeper
import com.wh.society.componment.ViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class App : Application() {

//    val okHttpClient: OkHttpClient by lazy {
//        OkHttpClient.Builder().addInterceptor {
//            val request = it.request()
//            val response = it.proceed(it.request())
//            if (request.url.toString().contains("bbs/post/create")) {
//                response.body?.let {
//                    Log.d("WH_", "addInterceptor: ${it.string()}")
//                }
//            }
//
//            response
//        }.build()
//    }

    val appDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    val serverApi: ServerApi by lazy {
        ServerApi.create()
    }

    val storeKeeper: StoreKeeper by lazy {
        StoreKeeper(this)
    }

    val repositoryKeeper by lazy {
        RepositoryKeeper(serverApi, storeKeeper, appDatabase)
    }

    val viewModelFactory by lazy {
        ViewModelFactory(repositoryKeeper)
    }
}