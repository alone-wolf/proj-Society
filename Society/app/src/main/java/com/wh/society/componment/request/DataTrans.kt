package com.wh.society.componment.request

import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.*
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.society.bbs.PostReply
import com.wh.society.api.data.user.UserInfo

class DataTrans {
    var society: Society = Society()
    var bbs: BBS = BBS()
    var userInfo = UserInfo()
    var postList = ReturnListData.blank<Post>()
    var postReplyList = ReturnListData.blank<PostReply>()
    var societyMemberRequestList = ReturnListData.blank<SocietyMemberRequest>()
    var societyMember = SocietyMember()
    var societyMemberList = ReturnListData.blank<SocietyMember>()
    var societyActivity = SocietyActivity()
    var isJoint = false
    var isAdmin = false
    var postId = 0
    var societyPictureList = ReturnListData.blank<SocietyPicture>()

    fun clear() {
        society = Society()
        bbs = BBS()
        userInfo = UserInfo()
        postList = ReturnListData.blank()
        postReplyList = ReturnListData.blank()
        societyMemberRequestList = ReturnListData.blank()
        societyMember = SocietyMember()
        societyMemberList = ReturnListData.blank()
        societyActivity = SocietyActivity()
        isJoint = false
        isAdmin = false
        postId = 0
        societyPictureList = ReturnListData.blank()
    }
}