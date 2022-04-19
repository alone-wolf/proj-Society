package com.wh.common.typeExt

import com.wh.common.util.TimeUtils
import kotlin.math.absoluteValue

fun Long.toTimestamp(spf: String = "yyyy-MM-dd HH:mm:ss"): String {
    return TimeUtils.getFormattedTime(this, spf)
}