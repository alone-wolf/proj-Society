package com.wh.society.componment.request

import androidx.appcompat.app.AppCompatActivity
import com.wh.society.service.SocketIOService

class SioServiceRequest(val activity: AppCompatActivity) {


    fun start(userId:Int, cookieToken:String){
        SocketIOService.startSIOService(userId, cookieToken, activity)
    }
    val stop: () -> Unit = {
        SocketIOService.stopSIOService(activity)
    }
}