package com.wh.society.api.data

class LoginReturn {
    var userId: Int = 0
    var cookieToken: String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginReturn

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
        return "LoginReturn(userId=$userId, cookieToken='$cookieToken')"
    }


}