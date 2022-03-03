package com.wh.society.typeExt

fun <T> List<T>.firstOrDefault(t: T): T {
    return if (this.isEmpty()) t
    else this.first()
}