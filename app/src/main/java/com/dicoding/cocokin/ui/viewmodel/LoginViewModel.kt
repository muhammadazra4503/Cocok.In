package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.Result
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.pref.UserModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(private val repository: UserRepository):ViewModel() {
    private val _loginResult = MutableLiveData<Result>()
    val loginResult: LiveData<Result>
        get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val (loginResponse, displayName) = repository.login(email, password)

                if (loginResponse.registered) {
                    repository.saveSession(UserModel(email, loginResponse.localId, true, displayName))
                    _loginResult.value = Result.Success
                } else {
                    _loginResult.value = Result.Error("Login failed")
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized: Invalid credentials"
                    403 -> "Forbidden: Insufficient permissions"
                    404 -> "Not Found: Resource not available"
                    // Add more cases for specific HTTP status codes if needed
                    else -> "HTTP Error: ${e.message()}"
                }
                _loginResult.value = Result.Error(errorMessage)
            } catch (e: IOException) {
                // Handle network-related exceptions
                _loginResult.value = Result.Error("Network error: ${e.message}")
            } catch (e: Exception) {
                // Handle other types of exceptions
                _loginResult.value = Result.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

}