package com.wh.admin.data.shadow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wh.admin.data.society.Society

class SocietyShadow : Society() {
    override var name: String by mutableStateOf(super.name)
    override var openTimestamp: Long by mutableStateOf(super.openTimestamp)
    override var describe: String by mutableStateOf(super.describe)
    override var college: String by mutableStateOf(super.college)
    override var bbsName: String by mutableStateOf(super.bbsName)
    override var bbsDescribe: String by mutableStateOf(super.bbsDescribe)
    override var iconUrl: String by mutableStateOf(super.iconUrl)

    companion object {
        fun fromSociety(s: Society): SocietyShadow {
            val a = SocietyShadow()
            a.id = s.id
            a.name = s.name
            a.openTimestamp = s.openTimestamp
            a.describe = s.describe
            a.college = s.college
            a.bbsName = s.bbsName
            a.bbsDescribe = s.bbsDescribe
            a.iconUrl = s.iconUrl
            return a
        }
    }
}