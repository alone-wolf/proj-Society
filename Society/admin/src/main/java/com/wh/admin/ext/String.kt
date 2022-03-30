package com.wh.admin.ext

import com.wh.admin.util.TimeFormatter

fun String.zTimestampFmt(): String {
    return TimeFormatter.fmt(this)
}