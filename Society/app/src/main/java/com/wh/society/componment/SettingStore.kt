package com.wh.society.componment

import android.content.Context
import com.wh.common.store.Store
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.util.SystemUtil

class SettingStore(context: Context) {
    val store = Store.create(context, "setting")


    var phoneStudentIdEmail by store.string("phone-studentId-email", "")
    var password by store.string("password", "")
    var autoLogin by store.boolean("auto-login", false)

    var showMineInfo by store.boolean("mine-show-more-info", false)

    var deviceName by store.string("device-name", SystemUtil.getDeviceModel())

    var showBBS by store.boolean("show-bbs", true)
    var receivePush by store.boolean("receive-push", true)

    var token by store.string("auth-token","")

    companion object{
        private var settingStore: SettingStore? = null

        fun instance(context: Context): SettingStore {
            if (settingStore == null) {
                settingStore = SettingStore(context)
            }
            return settingStore!!
        }

        fun unsafeInstance(): SettingStore? {
            return settingStore
        }
    }
}