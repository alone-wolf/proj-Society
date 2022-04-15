package com.wh.society.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.wh.society.App
import com.wh.society.R
import com.wh.society.api.ServerApi
import com.wh.society.api.data.SIORoomSubBlock
import com.wh.society.api.db.entity.Notify
import com.wh.society.api.repository.NotifyRepository
import com.wh.society.navigation.GlobalNavPage
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class SocketIOService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {

        const val START_SERVICE_REGISTER = "start-service-register"
        const val REFRESH_ROOM = "refresh-room"

        const val OPERATE_PLATFORM = "operate-platform"

        const val SOCIETY_CHAT_INNER = "society-chat-inner"
        const val BBS_NEW_POST = "bbs-new-post"
        const val BBS_NEW_POST_REPLY = "bbs-new-post-reply"
        const val SOCIETY_NEW_MEMBER_REQUEST = "society-new-member-request"
        const val SOCIETY_ACTIVITY_REQUEST = "society-new-activity-request"
        const val SOCIETY_MEMBER_CHANGED = "society-member-changed"
        const val USER_CHAT_PRIVATE = "user-chat-private"

        fun startSIOService(userId: Int, cookieToken: String, context: Context) {
            context.startService(Intent(context, SocketIOService::class.java).apply {
                putExtra("action", START_SERVICE_REGISTER)
                putExtra("userId", userId)
                putExtra("cookieToken", cookieToken)
            })
        }

        fun stopSIOService(context: Context) {
            context.stopService(Intent(context, SocketIOService::class.java))
        }
    }

    private val address: String = ServerApi.sioUrl
    private lateinit var sio: Socket
    private val TAG = "WH_SocketIOService"

    private var userId: Int = 0
    private var cookieToken: String = ""

    private lateinit var notificationManager: NotificationManager

    private val notifyRepository by lazy { NotifyRepository((application as App).appDatabase.notifyDao()) }

    private fun setupNotificationChannel(channels: Map<String, String>) {
        notificationManager.run {
            channels.entries.forEach {
                createNotificationChannel(
                    NotificationChannel(
                        it.key,
                        it.value,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        sio = IO.socket(address)

        notificationManager = getSystemService(NotificationManager::class.java)

        setupNotificationChannel(
            mapOf(
                SOCIETY_CHAT_INNER to "Society Inner Chat",
                BBS_NEW_POST to "BBS New Post",
                BBS_NEW_POST_REPLY to "BBS New Reply",
                SOCIETY_NEW_MEMBER_REQUEST to "Society New Member Request",
                SOCIETY_MEMBER_CHANGED to "Society Member Changed",
                USER_CHAT_PRIVATE to "User Private Chat",
                SOCIETY_ACTIVITY_REQUEST to "Society Activity Request",
                "service-notify" to "Service Notify",
            )
        )

        val foregroundNotification = Notification.Builder(this, "service-notify")
            .setContentTitle("Society Push Service Is Running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(0, foregroundNotification, ServiceInfo.FOREGROUND_SERVICE_TYPE_NONE)
        } else {
            startForeground(0, foregroundNotification)
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")

        Toast.makeText(this, "sio start", Toast.LENGTH_SHORT).show()
        if (sio.isActive) {
            Log.d(TAG, "onStartCommand: sio isActive == true")
            intent?.let {
                when (it.getStringExtra("action")!!) {
                    REFRESH_ROOM -> {
                        // want pusher to refresh-room
                        sio.emit(REFRESH_ROOM)
                    }
                    else -> {
                    }
                }
            }


        } else {
            intent?.let {
                val userId1 = it.getIntExtra("userId", -1)
                val cookieToken1 = it.getStringExtra("cookieToken")!!
                sio.on(Socket.EVENT_CONNECT) { out ->
                    Log.d(TAG, "onStartCommand: Socket.EVENT_CONNECT")
                    sio.emit("register", SIORoomSubBlock(userId1, cookieToken1).toJSON())
                    this.userId = userId1
                    this.cookieToken = cookieToken1
                }
                sio.on(Socket.EVENT_CONNECT_ERROR) { out ->
                    out.forEach {
                        Log.d(TAG, "onStartCommand: Socket.EVENT_CONNECT_ERROR $it")
                    }
                }
                sio.on(Socket.EVENT_DISCONNECT) {
                    Log.d(TAG, "Socket.EVENT_DISCONNECT")
                }
                sio.on("register-result") { out ->
                    out.forEach {
                        Log.d(TAG, "onStartCommand: Socket.register_result $it")
                    }
                }
                sio.on(SOCIETY_CHAT_INNER) { out ->
                    performOperatePlatform(
                        path = GlobalNavPage.SocietyChatInnerPage.route
                    )
                    (out[0] as JSONObject).let { it ->
                        val societyName = it.optString("societyName")
                        val message = it.optString("message")
                        val username = it.optString("username")
                        val userId = it.optInt("userId")
                        if (userId == this.userId) {
                            Log.d(
                                TAG,
                                "onStartCommand: $SOCIETY_CHAT_INNER $societyName $message $username isMe"
                            )
                        } else {
                            Log.d(TAG, "onStartCommand: $societyName $message $username isNotMe")
                            notifySocietyChatInner(societyName, username, message)
                        }

                    }
                }
                sio.on(BBS_NEW_POST) { out ->
                    //societyId, bbsName, postTitle, userId
                    (out[0] as JSONObject).let { it ->
//                        val societyId = it.optInt("societyId")
                        val bbsName = it.optString("bbsName")
                        val postTitle = it.optString("postTitle")
                        val userId = it.optInt("userId")
                        if (userId == this.userId) {
                            Log.d(
                                TAG,
                                "onStartCommand: $BBS_NEW_POST $bbsName $postTitle $userId isMe"
                            )
                        } else {
                            Log.d(
                                TAG,
                                "onStartCommand: $BBS_NEW_POST $bbsName $postTitle $userId isNotMe"
                            )
                            notifyBBSNewPost(bbsName, postTitle)
                        }
                    }
                }
                sio.on(BBS_NEW_POST_REPLY) { out ->
                    (out[0] as JSONObject).let { it ->
//                        { replyUserId, replyUsername, societyId, bbsName, postTitle, reply }
                        val replyUserId = it.optInt("replyUserId")
                        val replyUsername = it.optString("replyUsername")
                        val bbsName = it.optString("bbsName")
                        val postTitle = it.optString("postTitle")
                        val reply = it.optString("reply")
                        if (replyUserId == this.userId) {
                            Log.d(TAG, "onStartCommand: $BBS_NEW_POST_REPLY isMe")
                        } else {
                            Log.d(TAG, "onStartCommand: $BBS_NEW_POST_REPLY isNotMe")
                            notifyBBSNewPostReply(bbsName, postTitle, reply, replyUsername)
                        }
                    }
                }
                sio.on(SOCIETY_NEW_MEMBER_REQUEST) { out ->
                    (out[0] as JSONObject).let { it ->
//                        { societyId, societyName, userId, username }
//                        val societyId = it.optInt("societyId")
                        val societyName = it.optString("societyName")
                        val userId = it.optInt("userId")
                        val username = it.optString("username")
                        if (userId == this.userId) {
                            Log.d(TAG, "onStartCommand: $SOCIETY_NEW_MEMBER_REQUEST isMe")
                        } else {
                            Log.d(TAG, "onStartCommand: $SOCIETY_NEW_MEMBER_REQUEST isNotMe")
                            notifySocietyNewMemberRequest(username, societyName)
                        }
                    }
                }
                sio.on(SOCIETY_MEMBER_CHANGED) { out ->
                    (out[0] as JSONObject).let { it ->
//                        { societyId, societyName }
//                        val societyId = it.optInt("societyId")
                        val societyName = it.optString("societyName")
                        notifySocietyMemberChanged(societyName)
                    }

                }
                sio.on(USER_CHAT_PRIVATE) { out ->
                    performOperatePlatform(
                        path = GlobalNavPage.UserChatPrivate.route
                    )
                    (out[0] as JSONObject).let { it ->
                        val username = it.optString("username")
                        val message = it.optString("message")
                        notifyUserChatPrivate(username, message)
                    }
                }

                sio.on(SOCIETY_ACTIVITY_REQUEST) { out ->
//                    performOperatePlatform(GlobalNavPage.S)
                    (out[0] as JSONObject).let { it ->
                        val username = it.optString("username")
                        val userId = it.optInt("userId")
                        val request = it.optString("request")
                        val societyName = it.optString("societyName")
                        if (userId == this.userId) {
                            Log.d(TAG, "onStartCommand: $SOCIETY_ACTIVITY_REQUEST isMe")
                        } else {
                            Log.d(TAG, "onStartCommand: $SOCIETY_ACTIVITY_REQUEST isNotMe")
                            notifySocietyActivityRequest(username, request, societyName)
                        }
                    }

                }

                sio.on("notify") { out ->
                    Log.d(TAG, "onStartCommand: on-notify $out")
                }
                sio.connect()
            }
        }


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "sio stop", Toast.LENGTH_SHORT).show()

        if (sio.isActive) {
            sio.close()
        }
    }

    private fun performOperatePlatform(path: String) {
        sendBroadcast(Intent().apply {
            action = OPERATE_PLATFORM
            putExtra("path", path)
        })
    }

    private fun notifyBase(channel: String, title: String, text: String, id: Int) {
        val n = Notification
            .Builder(this, channel)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        notifyRepository.insert(Notify().apply {
            this.title = title
            this.message = text
        })

        notificationManager.notify(id, n)
    }

    private fun notifyBase(channel: String, title: String, id: Int) {
        val n = Notification
            .Builder(this, channel)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        notifyRepository.insert(Notify().apply {
            this.title = title
        })

        notificationManager.notify(id, n)
    }

    private fun notifySocietyChatInner(societyName: String, username: String, message: String) {
        notifyBase(
            channel = SOCIETY_CHAT_INNER,
            title = "$societyName · 聊天室",
            text = "${username}:${message}",
            id = 1
        )
    }

    private fun notifyBBSNewPost(bbsName: String, postTitle: String) {
        notifyBase(
            channel = BBS_NEW_POST,
            title = "$bbsName 有新的帖子",
            text = postTitle,
            id = 2
        )
    }

    private fun notifyBBSNewPostReply(
        bbsName: String,
        postTitle: String,
        reply: String,
        username: String
    ) {
        notifyBase(
            channel = BBS_NEW_POST_REPLY,
            title = "$username 回复了你的帖子 · $postTitle · $bbsName",
            text = reply,
            id = 3
        )
    }

    private fun notifySocietyNewMemberRequest(
        username: String,
        societyName: String
    ) {
        notifyBase(
            channel = SOCIETY_NEW_MEMBER_REQUEST,
            title = "$username 发出成员申请 · $societyName",
            id = 4
        )
    }

    private fun notifySocietyMemberChanged(
        societyName: String
    ) {
        notifyBase(
            channel = SOCIETY_MEMBER_CHANGED,
            title = "$societyName 成员发生了变动",
            id = 5
        )
    }

    private fun notifyUserChatPrivate(
        username: String, message: String
    ) {
        notifyBase(
            channel = USER_CHAT_PRIVATE,
            title = "$username 发来了私信",
            text = message,
            id = 6
        )
    }

    private fun notifySocietyActivityRequest(
        username: String, request: String, societyName: String
    ) {
        notifyBase(
            channel = SOCIETY_ACTIVITY_REQUEST,
            title = "$username 发来了活动申请 · $societyName",
            text = request,
            id = 7
        )
    }
}
