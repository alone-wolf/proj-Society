package com.wh.society.api.data.shadow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wh.society.api.data.society.SocietyActivity

class SocietyActivityShadow: SocietyActivity() {

    override var title:String by mutableStateOf(super.title)
    override var activity:String by mutableStateOf(super.activity)
    override var level: Int by mutableStateOf(super.level)

}