package com.dicoding.cocokin.data

sealed class LoginResult {
    object Success : LoginResult()
    data class Error(val errorMessage: String) : LoginResult()
}
