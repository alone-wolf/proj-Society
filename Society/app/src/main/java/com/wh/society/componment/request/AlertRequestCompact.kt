package com.wh.society.componment.request

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wh.society.componment.RequestHolder
import com.wh.society.ui.componment.RoundedTextFiled

class AlertRequestCompact(
    private val resources: Resources,
    private val requestHolder: RequestHolder
) : AlertRequest(resources) {
    //        fun alertNewPost(bbs: BBS) {
//            super.alert(
//                title = "New Post",
//                content = {
//
//                },
//                onOk = {},
//                onCancel = {}
//            )
//        }
    var reply by mutableStateOf("")
    var text1 by mutableStateOf("")

    fun alertFor1TextFiled(title: String, onOk: (String) -> Unit) {
        super.alert(
            title = title,
            content = {
                RoundedTextFiled(value = text1, onValueChange = { text1 = it })
            },
            onOk = { onOk(text1);text1 = "" },
            onCancel = { text1 = "" }
        )
    }


//        fun postPost(post: Post) {
//            super.alert("Confirm", "sure to post this?", onOk = {}, onCancel = {})
//        }
//
//        fun postPostReply(onOk: (String) -> Unit) {
//            super.alert("reply post", {
//                RoundedTextFiled(value = reply, onValueChange = { reply = it })
//            }, onOk = { onOk(reply);reply = "" }, { reply = "" })
//        }
}