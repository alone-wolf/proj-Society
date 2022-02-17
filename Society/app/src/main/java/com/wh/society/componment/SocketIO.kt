//package com.wh.society.componment
//
//import android.util.Log
//import com.wh.society.api.data.SIORoomSubBlock
//import com.wh.society.store.SettingStore
//import io.socket.client.IO
//import io.socket.client.Socket
//import io.socket.emitter.Emitter
//
//// 广播 - society-chat-inner 每个 society 创建一个 room society-chat-inner-${societyId}
//// 广播 - bbs-post 每个 society 创建一个 room 用于显示关注的 bbs 的新post bbs-post-${societyId}
//// 广播 -
//// 定向推送 - bbs-post 订阅单个事件 bbs-post-reply-${id}
//
//class SocketIO(private val settingStore: SettingStore) {
//
//    private val address = "http://192.168.50.183:5002/"
//    val TAG = "WH_SocketIO"
//
//    private fun registerThisDevice(socket: Socket, sioRoomSubBlock: SIORoomSubBlock) {
//        // userId cookieToken
//        socket.emit("register", sioRoomSubBlock)
//    }
//
//    private var socket: Socket = IO.socket(address).apply {
//        on(Socket.EVENT_CONNECT) {
//            registerThisDevice(this)
//            Log.d(TAG, "Socket.EVENT_CONNECT")
//        }
//        on(Socket.EVENT_CONNECT_ERROR) {
//            it.forEach { i ->
//                Log.d(TAG, "Socket.EVENT_CONNECT_ERROR $i ")
//            }
//        }
//        on(Socket.EVENT_DISCONNECT) {
//            Log.d(TAG, "Socket.EVENT_DISCONNECT")
//        }
//    }
//
//    fun removeEventListener(event: String) {
//        socket.off(event)
//    }
//
//    fun connect(onConnect: () -> Unit) {
//        socket.connect()
//        onConnect()
//    }
//
//    fun disconnect() {
//        socket.close()
//    }
//
//    fun installListener(event: String, fn: Emitter.Listener) {
//        socket.on(event, fn)
//    }
//
//    fun emit(event: String, data: String) {
//        if (!socket.isActive) {
//            return
//        }
//        try {
//            socket.emit(event, data)
//        } catch (e: Exception) {
//            Log.d("TAG", "emit: ${e.localizedMessage}")
//        }
//    }
//}