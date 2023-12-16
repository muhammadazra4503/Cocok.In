package com.dicoding.cocokin.data

sealed class Result {
    object Success : Result()
    data class Error(val errorMessage: String) : Result()
}
