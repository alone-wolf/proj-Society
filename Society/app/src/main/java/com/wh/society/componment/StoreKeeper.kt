package com.wh.society.componment

import android.content.Context
import com.wh.society.store.SettingStore

class StoreKeeper(context: Context) {
    val settingStore = SettingStore(context)
}