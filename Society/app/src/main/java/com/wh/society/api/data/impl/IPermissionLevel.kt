package com.wh.society.api.data.impl

interface IPermissionLevel {
    var level:Int

    val permLevel:String
    get() {
        return when(level){
            0,1->"游客"
            10,11->"成员"
            100,111->"管理员"
            else -> "未知"
        }
    }
}