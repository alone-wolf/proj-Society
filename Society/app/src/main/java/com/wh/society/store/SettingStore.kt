package com.wh.society.store

import android.content.Context
import com.wh.common.store.Store

class SettingStore(context:Context) {
    val store = Store.create(context,"setting")


    var phoneStudentIdEmail by store.string("phone-studentId-email","")
    var password by store.string("password","")
    var autoLogin by store.boolean("auto-login",false)

    var showMineInfo by store.boolean("mine-show-more-info",false)
}