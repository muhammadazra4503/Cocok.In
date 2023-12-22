package com.dicoding.cocokin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.remote.response.ProductResponseItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(private val repository: UserRepository) : ViewModel() {

    private val _productList = MutableLiveData<List<ProductResponseItem>>()
    val productList: LiveData<List<ProductResponseItem>> get() = _productList

    private val _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> get() = _errorState

    // Function to fetch product data
    fun fetchProductData() {
        viewModelScope.launch {
            try {
                val result = repository.getProductData()
                _productList.value = result
            } catch (e: Exception) {
                // Handle HTTP errors
                val errorMessage = handleHttpException(e)
                _errorState.value = errorMessage
            }
        }
    }

    private fun handleHttpException(exception: Exception): String {
        return when (exception) {
            is HttpException -> {
                // Handle HTTP errors (e.g., 404 Not Found, 500 Internal Server Error)
                Log.e("API_ERROR", "HTTP Error: ${exception.code()}", exception)
                "HTTP Error: ${exception.code()}"
            }
            is IOException -> {
                // Handle network errors (e.g., no internet connection)
                Log.e("API_ERROR", "Network Error: Check your internet connection", exception)
                "Network Error: Check your internet connection"
            }
            else -> {
                // Handle other types of exceptions
                Log.e("API_ERROR", "An unexpected error occurred", exception)
                "An unexpected error occurred"
            }
        }
    }
}