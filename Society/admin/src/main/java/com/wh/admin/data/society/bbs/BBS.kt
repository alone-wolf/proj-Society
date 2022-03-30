package com.wh.admin.data.society.bbs

import com.wh.admin.data.impl.IZTimestamp
import com.wh.admin.data.society.Society
import com.wh.society.api.data.impl.IContain


class BBS: IContain,IZTimestamp {
    var id: Int = 0
    var name: String = ""
    var openTimestamp: String = ""
    var describe: String = ""
    override var createTimestamp: String = ""
    override var updateTimestamp: String = ""

    override val checkArray: Array<String>
        get() = arrayOf(name,describe)

    companion object{
        fun fromSociety(s: Society): BBS {
            return BBS().apply {
                this.id = s.id
                this.name = s.bbsName
                this.describe = s.bbsDescribe
                this.openTimestamp = s.openTimestamp
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BBS

        if (id != other.id) return false
        if (name != other.name) return false
        if (openTimestamp != other.openTimestamp) return false
        if (describe != other.describe) return false
        if (createTimestamp != other.createTimestamp) return false
        if (updateTimestamp != other.updateTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + openTimestamp.hashCode()
        result = 31 * result + describe.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "BBS(id=$id, name='$name', openTimestamp='$openTimestamp', describe='$describe', createTimestamp='$createTimestamp', updateTimestamp='$updateTimestamp')"
    }


}