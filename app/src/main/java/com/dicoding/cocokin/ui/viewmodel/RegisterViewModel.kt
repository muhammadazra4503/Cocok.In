package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.Result
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.remote.response.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<Result>()
    val registerResult: LiveData<Result>
        get() = _registerResult

    fun register(displayName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val registerResponse: RegisterResponse = repository.register(displayName, email, password)
                if (registerResponse.localId.isNotBlank()) {
                    _registerResult.value = Result.Success
                } else {
                    _registerResult.value = Result.Error("Registration failed")
                }
            } catch (e: HttpException) {
                // Handle HTTP-related exceptions (e.g., 4xx, 5xx status codes)
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized: Invalid credentials"
                    403 -> "Forbidden: Insufficient permissions"
                    404 -> "Not Found: Resource not available"
                    // Add more cases for specific HTTP status codes if needed
                    else -> "HTTP Error: ${e.message()}"
                }
                _registerResult.value = Result.Error(errorMessage)
            } catch (e: IOException) {
                // Handle network-related exceptions
                _registerResult.value = Result.Error("Network error: ${e.message}")
            } catch (e: Exception) {
                // Handle other types of exceptions
                _registerResult.value = Result.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }
}
