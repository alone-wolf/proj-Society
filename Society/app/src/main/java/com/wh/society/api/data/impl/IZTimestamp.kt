package com.wh.society.api.data.impl

import com.wh.society.typeExt.zTimestampFmt

interface IZTimestamp {
    val updateTimestamp: String
    val createTimestamp: String

    fun updateTSFmt(): String {
        return updateTimestamp.zTimestampFmt()
    }

    fun createTSFmt(): String {
        return createTimestamp.zTimestampFmt()
    }
}