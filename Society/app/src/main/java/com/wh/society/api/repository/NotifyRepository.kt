package com.wh.society.api.repository

import com.wh.society.api.db.dao.NotifyDao
import com.wh.society.api.db.entity.Notify

class NotifyRepository(private val notifyDao: NotifyDao) {
    val all = notifyDao.notifyAllList

    suspend fun insertCo(notify: Notify) {
        notifyDao.insertCo(notify)
    }

    fun insert(notify: Notify) {
        Thread {
            notifyDao.insert(notify)
        }.start()
    }

    suspend fun updateCo(notify: Notify) {
        notifyDao.updateCo(notify)
    }

//    fun update(notify: Notify) {
//        Thread {
//            notifyDao.update(notify)
//        }.start()
//    }

    suspend fun deleteCo(notify: Notify) {
        notifyDao.deleteCo(notify)
    }

    fun delete(notify: Notify) {
        Thread {
            notifyDao.delete(notify)
        }.start()
    }

    suspend fun clear(){
        notifyDao.clear()
    }


}