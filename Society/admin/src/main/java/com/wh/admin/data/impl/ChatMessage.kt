package com.wh.admin.data.impl

import com.wh.admin.componment.ServerApi

interface ChatMessage : IRealIconUrl,IZTimestamp {
    var userId: Int
    var username: String
    var userIconUrl: String
    var message:String
    override var createTimestamp: String

    override val realIconUrl: String
        get() = if (userIconUrl.startsWith("http://") || userIconUrl.startsWith("https://") || userIconUrl.isBlank())
            userIconUrl
        else
            ServerApi.userPicUrl(userIconUrl)
}