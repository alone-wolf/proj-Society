package com.wh.society.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wh.society.api.db.AppDatabase
import com.wh.society.api.db.entity.Notify
import com.wh.society.api.repository.NotifyRepository
import kotlinx.coroutines.launch

class NotifyViewModel(appDatabase: AppDatabase) : ViewModel() {

    private val notifyRepository: NotifyRepository = NotifyRepository(appDatabase.notifyDao())

    val all = notifyRepository.all

    fun insert(notify: Notify) {
        viewModelScope.launch {
            notifyRepository.insertCo(notify)
        }
    }

    fun update(notify: Notify) {
        viewModelScope.launch {
            notifyRepository.updateCo(notify)
        }
    }

    fun delete(notify: Notify) {
        viewModelScope.launch {
            notifyRepository.deleteCo(notify)
        }
    }

    fun clear() {
        viewModelScope.launch {
            notifyRepository.clear()
        }
    }
}