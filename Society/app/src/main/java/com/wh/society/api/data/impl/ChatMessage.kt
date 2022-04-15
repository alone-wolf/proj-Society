package com.wh.society.api.data.impl

import com.wh.society.api.ServerApi

interface ChatMessage : RealIconUrl,IZTimestamp {
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