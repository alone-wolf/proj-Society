package com.wh.admin.data.impl

import com.wh.admin.ext.zTimestampFmt

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