package com.wh.admin.data.user

import com.wh.admin.componment.ServerApi
import com.wh.admin.data.impl.IRealIconUrl
import com.wh.admin.data.impl.IZTimestamp

class UserPicture : IRealIconUrl,IZTimestamp {
    var id: Int = 0
    var userId: Int = 0
    var originalFilename: String = ""
    var newFilename: String = ""
    override var createTimestamp: String = ""
    override var updateTimestamp: String = ""

    override val realIconUrl
        get() = ServerApi.userPicUrl(newFilename)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPicture

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (originalFilename != other.originalFilename) return false
        if (newFilename != other.newFilename) return false
        if (createTimestamp != other.createTimestamp) return false
        if (updateTimestamp != other.updateTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId
        result = 31 * result + originalFilename.hashCode()
        result = 31 * result + newFilename.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "PicData(id=$id, userId=$userId, originalFilename='$originalFilename', newFilename='$newFilename', createTimestamp='$createTimestamp', updateTimestamp='$updateTimestamp')"
    }


}