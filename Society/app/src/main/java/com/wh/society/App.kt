package com.wh.society

import android.app.Application
import com.wh.society.api.ServerApi
import com.wh.society.api.db.AppDatabase
import com.wh.society.componment.RepositoryKeeper
import com.wh.society.componment.StoreKeeper
import com.wh.society.componment.ViewModelFactory

class App : Application() {

    private val appDatabase by lazy {
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