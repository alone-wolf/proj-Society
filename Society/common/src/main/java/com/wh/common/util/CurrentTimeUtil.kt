package com.wh.common.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

object CurrentTimeUtil {
    @SuppressLint("SimpleDateFormat")
    val ymdFmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private val currentTimeZone: TimeZone = TimeZone.getDefault()

    private val tz2utcMSOffset = currentTimeZone.getOffset(System.currentTimeMillis())

    fun utcTS2ms(fmt:SimpleDateFormat,utcTS: String): Long {
        val calendar = Calendar.getInstance(currentTimeZone)
        val date = fmt.parse(utcTS)!!
        calendar.time = date
        return calendar.time.time + tz2utcMSOffset
    }
}