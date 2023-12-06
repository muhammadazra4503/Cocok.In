package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.LoginResult
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.remote.response.RegisterResponse
import com.dicoding.cocokin.data.pref.UserModel
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<LoginResult>()
    val registerResult: LiveData<LoginResult>
        get() = _registerResult

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                val registerResponse: RegisterResponse = repository.register(email, password)

                // Sesuaikan dengan properti yang sesuai dalam RegisterResponse
                if (registerResponse.idToken.isNotBlank()) {
                    saveSession(UserModel(email, registerResponse.idToken, true))
                    _registerResult.value = LoginResult.Success
                } else {
                    _registerResult.value = LoginResult.Error("Registration failed")
                }
            } catch (e: Exception) {
                _registerResult.value = LoginResult.Error("An error occurred: ${e.message}")
            }
        }
    }
}
