package com.wh.society.api.db.dao

import androidx.room.*
import com.wh.society.api.db.entity.Notify
import kotlinx.coroutines.flow.Flow

@Dao
interface NotifyDao {


    @get:Query("select * from Notify Order by id DESC")
    val notifyAllList:Flow<List<Notify>>

    @Insert
    suspend fun insertCo(notify: Notify)

    @Insert
    fun insert(notify: Notify) // need thread

    @Delete
    suspend fun deleteCo(notify: Notify)

    @Delete
    fun delete(notify: Notify)

    @Update
    suspend fun updateCo(notify: Notify)

    @Update
    fun update(notify: Notify)

    @Query("delete from notify")
    suspend fun clear()
}