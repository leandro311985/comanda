package com.example.mylogin.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mylogin.data.UserRepository


@Suppress("UNCHECKED_CAST")
class ManagerViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ManagerViewModel(repository) as T
    }

}