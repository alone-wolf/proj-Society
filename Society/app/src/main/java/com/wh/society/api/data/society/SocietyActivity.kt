package com.wh.society.api.data

class SocietyActivity {
    var id:Int = 0
    var societyId:Int = 0
    var societyName:String = ""
    var deviceName:String = ""
    var level:Int = 0
    var title:String = ""
    var activity:String = ""
    var createTimestamp:String = ""
    var updateTimestamp:String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocietyActivity

        if (id != other.id) return false
        if (societyId != other.societyId) return false
        if (societyName != other.societyName) return false
        if (deviceName != other.deviceName) return false
        if (level != other.level) return false
        if (title != other.title) return false
        if (activity != other.activity) return false
        if (createTimestamp != other.createTimestamp) return false
        if (updateTimestamp != other.updateTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + societyId
        result = 31 * result + societyName.hashCode()
        result = 31 * result + deviceName.hashCode()
        result = 31 * result + level
        result = 31 * result + title.hashCode()
        result = 31 * result + activity.hashCode()
        result = 31 * result + createTimestamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "SocietyActivity(id=$id, societyId=$societyId, societyName='$societyName', deviceName='$deviceName', level=$level, title='$title', activity='$activity', createTimestamp='$createTimestamp', updateTimestamp='$updateTimestamp')"
    }


}