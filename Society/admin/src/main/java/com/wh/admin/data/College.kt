package com.wh.admin.data

class College {
    var id:Int = 0
    var name:String = ""
    var des :String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as College

        if (id != other.id) return false
        if (name != other.name) return false
        if (des != other.des) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + des.hashCode()
        return result
    }

    override fun toString(): String {
        return "College(id=$id, name='$name', des='$des')"
    }


}