package com.wh.society.api.data.shadow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wh.society.api.data.society.bbs.Post

class PostShadow: Post() {
    override var societyId:Int by mutableStateOf(super.societyId)
    override var userId:Int by mutableStateOf(super.userId)
    override var title:String by mutableStateOf(super.title)
    override var post:String by mutableStateOf(super.post)
    override var level:Int by mutableStateOf(super.level)
    override var deviceName by mutableStateOf(super.deviceName)
}