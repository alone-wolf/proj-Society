package com.wh.society.api.data.society

import com.wh.society.api.ServerApi
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.api.data.impl.RealIconUrl
import com.wh.society.api.data.shadow.SocietyShadow
import com.wh.society.api.data.impl.IContain

open class Society: IContain,RealIconUrl {
    var id: Int = 0
    open var name: String = ""
    open var openTimestamp: String = ""
    open var describe: String = ""
    open var college: String = ""
    open var bbsName: String = ""
    open var bbsDescribe: String = ""
    var createTimestamp: String = ""
    var updateTimestamp: String = ""
    open var iconUrl:String =""

    fun shadow(): SocietyShadow {
        return SocietyShadow.fromSociety(this)
    }

    override val checkArray: Array<String>
        get() = arrayOf(name,describe,college)

    fun toBBS(): BBS {
        return BBS().apply{
            this.id = this@Society.id
            this.name = this@Society.bbsName
            this.describe = this@Society.bbsDescribe
            this.openTimestamp = this@Society.openTimestamp
        }
    }

    override val realIconUrl: String
        get() = if (iconUrl.startsWith("http://") || iconUrl.startsWith("https://") || iconUrl.isBlank())
            iconUrl
        else
            ServerApi.societyPicUrl(iconUrl)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Society

        if (id != other.id) return false
        if (name != other.name) return false
        if (openTimestamp != other.openTimestamp) return false
        if (describe != other.describe) return false
        if (college != other.college) return false
        if (bbsName != other.bbsName) return false
        if (bbsDescribe != other.bbsDescribe) return false
        if (createTimestamp != other.createTimestamp) return false
        if (updateTimestamp != other.updateTimestamp) return false
        if (iconUrl != other.iconUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + openTimestamp.hashCode()
        result = 31 * result + describe.hashCode()
        result = 31 * result + college.hashCode()
        result = 31 * result + bbsName.hashCode()
        result = 31 * result + bbsDescribe.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        result = 31 * result + iconUrl.hashCode()
        return result
    }

    override fun toString(): String {
        return "Society(id=$id, name='$name', openTimestamp='$openTimestamp', describe='$describe', college='$college', bbsName='$bbsName', bbsDescribe='$bbsDescribe', createTimestamp='$createTimestamp', updateTimestamp='$updateTimestamp', iconUrl='$iconUrl')"
    }


}