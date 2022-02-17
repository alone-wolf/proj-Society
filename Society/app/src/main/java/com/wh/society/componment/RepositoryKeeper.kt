package com.wh.society.componment

import com.wh.society.api.ServerApi
import com.wh.society.api.db.AppDatabase
import com.wh.society.api.repository.ApiRepository
import com.wh.society.api.repository.NotifyRepository

class RepositoryKeeper(private val serverApi: ServerApi,private val storeKeeper: StoreKeeper,private val appDatabase: AppDatabase) {
    val apiRepository = ApiRepository(serverApi,storeKeeper.settingStore)
    val notifyRepository = NotifyRepository(appDatabase.notifyDao())
}