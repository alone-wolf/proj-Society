package com.wh.society.typeExt

import com.wh.society.util.TimeFormatter

fun String.zTimestampFmt(): String {
    return TimeFormatter.fmtFull(this)
}