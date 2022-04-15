package com.wh.society.util

import android.annotation.SuppressLint
import com.wh.common.util.CurrentTimeUtil
import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {
    @SuppressLint("SimpleDateFormat")
    val ymdFmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @SuppressLint("SimpleDateFormat")
    val fullFmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    fun fmtFull(zTimestamp:String): String {
        val ms = CurrentTimeUtil.utcTS2ms(fullFmt,zTimestamp)
        return ymdFmt.format(Date(ms))
    }
}