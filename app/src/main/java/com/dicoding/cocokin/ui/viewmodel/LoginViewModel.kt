package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.LoginResult
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository):ViewModel() {
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult>
        get() = _loginResult

    fun saveSession(user:UserModel){
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginResponse = repository.login(email, password)
                if (loginResponse.registered) {
                    _loginResult.value = LoginResult.Success
                } else {
                    _loginResult.value = LoginResult.Error("Login failed")
                }
            } catch (e: Exception) {
                _loginResult.value = LoginResult.Error("An error occurred: ${e.message}")
            }
        }
    }
}