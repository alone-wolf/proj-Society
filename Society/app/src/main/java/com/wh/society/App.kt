package com.wh.society

import android.app.Application
import com.wh.society.api.db.AppDatabase

class App : Application() {

    lateinit var appDatabase: AppDatabase

    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase.getDatabase(this)
    }

}