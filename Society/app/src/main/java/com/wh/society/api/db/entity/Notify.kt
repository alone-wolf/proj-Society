package com.wh.society.api.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Notify {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var title: String = ""
    var message: String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Notify

        if (id != other.id) return false
        if (title != other.title) return false
        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + message.hashCode()
        return result
    }

    override fun toString(): String {
        return "Notify(id=$id, title='$title', message='$message')"
    }


}