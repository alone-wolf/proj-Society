package com.wh.society.api.data.shadow
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import com.wh.society.api.data.society.bbs.PostReply
//
//class PostReplyShadow: PostReply() {
//
//    override var reply: String by mutableStateOf(super.reply)
//
//    companion object{
//        fun new(societyId:Int,postId:Int,userId:Int): PostReplyShadow {
//            val a = PostReplyShadow()
//            a.societyId = societyId
//            a.postId = postId
//            a.userId = userId
//            return a
//        }
//    }
//}