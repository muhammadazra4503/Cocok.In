package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository): ViewModel() {
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}