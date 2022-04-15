package com.wh.society.api.data.society

import com.wh.society.api.ServerApi
import com.wh.society.api.data.impl.IRealIconUrl

class SocietyPicture : IRealIconUrl {
    var id: Int = 0
    var societyId: Int = 0
    var newFilename: String = ""
    override val realIconUrl: String
        get() = ServerApi.societyPicUrl(newFilename)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocietyPicture

        if (id != other.id) return false
        if (societyId != other.societyId) return false
        if (newFilename != other.newFilename) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + societyId
        result = 31 * result + newFilename.hashCode()
        return result
    }

    override fun toString(): String {
        return "SocietyPicture(id=$id, societyId=$societyId, newFilename='$newFilename')"
    }


}