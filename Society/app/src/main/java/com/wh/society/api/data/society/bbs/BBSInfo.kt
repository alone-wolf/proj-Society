package com.wh.society.api.data.society.bbs

class BBSInfo {
    var postCount:Int = 0
    var posts:List<Post> = emptyList()
    var postReplyCount:Int = 0
    var bbsAllowGuestPost:Boolean = false
}