package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.pref.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository): ViewModel() {

    val userSession: LiveData<UserModel> = repository.getSession().asLiveData()
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

}