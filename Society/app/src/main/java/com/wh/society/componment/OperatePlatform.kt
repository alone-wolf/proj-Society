package com.wh.society.componment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.wh.society.service.SocketIOService

class OperatePlatform {

    var currentRoute: String = ""

    var currentOperate: () -> Unit = {}

    private val onOperationRequestReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            p1?.let {
                val path = it.getStringExtra("path")
//                    val event = it.getStringExtra("opt-event")

                if (currentRoute == path) {
                    currentOperate()
                }
            }
        }
    }
    private val intentFilter = IntentFilter(SocketIOService.OPERATE_PLATFORM)

    fun reg(context: Context){
        context.registerReceiver(onOperationRequestReceiver,intentFilter)
    }

    fun unreg(context: Context){
        context.unregisterReceiver(onOperationRequestReceiver)
    }
}