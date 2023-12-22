package com.dicoding.cocokin.data

sealed class ResultPayment<out T> {
    object Success : ResultPayment<Nothing>()
    data class SuccessWithData<T>(val data: T) : ResultPayment<T>()
    data class Error(val errorMessage: String) : ResultPayment<Nothing>()
}
