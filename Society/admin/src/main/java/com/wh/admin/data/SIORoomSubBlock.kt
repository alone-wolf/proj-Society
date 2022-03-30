package com.wh.admin.data

import org.json.JSONObject

class SIORoomSubBlock(var userId: Int, var cookieToken: String) {


    fun toJSON(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("userId",userId)
        jsonObject.put("cookieToken",cookieToken)
        return jsonObject
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SIORoomSubBlock

        if (userId != other.userId) return false
        if (cookieToken != other.cookieToken) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId
        result = 31 * result + cookieToken.hashCode()
        return result
    }

    override fun toString(): String {
        return "SIORoomSubBlock(userId=$userId, cookieToken='$cookieToken')"
    }

}