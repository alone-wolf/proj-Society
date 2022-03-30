package com.wh.admin.util

import android.annotation.SuppressLint
import com.wh.common.util.CurrentTimeUtil
import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {

    @SuppressLint("SimpleDateFormat")
    val ymdthmssFmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    fun fmt(zTimestamp:String): String {
        val ms = CurrentTimeUtil.utcTS2ms(ymdthmssFmt,zTimestamp)
        return CurrentTimeUtil.ymdFmt.format(Date(ms))
    }
}