package com.wh.society.componment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wh.society.viewModel.ApiViewModel
import com.wh.society.viewModel.NotifyViewModel


class ViewModelFactory(private val repositoryKeeper: RepositoryKeeper): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ApiViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ApiViewModel(repositoryKeeper.apiRepository) as T
            }
            modelClass.isAssignableFrom(NotifyViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return NotifyViewModel(repositoryKeeper.notifyRepository) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}