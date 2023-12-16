package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.Result
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.remote.response.RegisterResponse
import com.dicoding.cocokin.data.pref.UserModel
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<Result>()
    val registerResult: LiveData<Result>
        get() = _registerResult

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun register(name: String,email: String, password: String) {
        viewModelScope.launch {
            try {
                val registerResponse: RegisterResponse = repository.register(name, email, password)

                // Sesuaikan dengan properti yang sesuai dalam RegisterResponse
                if (registerResponse.idToken.isNotBlank()) {
                    saveSession(UserModel(email, registerResponse.idToken, true))
                    _registerResult.value = Result.Success
                } else {
                    _registerResult.value = Result.Error("Registration failed")
                }
            } catch (e: Exception) {
                _registerResult.value = Result.Error("An error occurred: ${e.message}")
            }
        }
    }
}
