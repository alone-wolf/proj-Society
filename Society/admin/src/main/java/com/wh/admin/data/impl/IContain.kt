package com.wh.admin.data.impl

interface IContain {

    val checkArray:Array<String>

    fun contain(str:String): Boolean {
        return checkArray.any { it.contains(str,false) }
    }
}