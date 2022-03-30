package com.wh.admin.data.society.bbs

import com.wh.admin.data.society.bbs.Post

class BBSInfo {
    var postCount:Int = 0
    var posts:List<Post> = emptyList()
    var postReplyCount:Int = 0
    var bbsAllowGuestPost:Boolean = false
}