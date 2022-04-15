package com.wh.society.componment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wh.society.App
import com.wh.society.viewModel.ApiViewModel
import com.wh.society.viewModel.NotifyViewModel


class ViewModelFactory(
    private val settingStore: SettingStore,
    private val application: App
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ApiViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ApiViewModel() as T
            }
            modelClass.isAssignableFrom(NotifyViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return NotifyViewModel(application.appDatabase) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}