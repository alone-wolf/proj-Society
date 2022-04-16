package com.wh.society.api.data.shadow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wh.society.api.data.impl.IRequestBody
import com.wh.society.api.data.society.SocietyNotice
import org.json.JSONObject

class SocietyNoticeShadow: SocietyNotice() {
    override var title: String by mutableStateOf(super.title)
    override var notice: String by mutableStateOf(super.notice)
    override var permissionLevel: Int by mutableStateOf(super.permissionLevel)
}