package com.wh.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static final String fullDateTimeFormat = "yyyy-MM-dd HH:mm:ss";


    /**
     * @return 返回格式化后的字符串
     * @describe 将长整型的时间戳转换成自定义的格式
     * @args MS long 长整型的毫秒时间戳
     * format String 格式
     */
    public static String getFormattedTime(long MS, String format) {
        return genDateFormat(format).format(new Date(MS));
    }

    /**
     * @return 返回格式数据结构
     * @describe 输入格式字符串, 返回格式的数据结构
     * @args stringFormat String 格式字符串
     */
    public static SimpleDateFormat genDateFormat(String stringFormat) {
        return new SimpleDateFormat(stringFormat, Locale.getDefault());
    }

}