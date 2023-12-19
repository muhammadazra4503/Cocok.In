package com.dicoding.cocokin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.remote.response.DetailProductResponseItem
import com.dicoding.cocokin.data.remote.response.ProductResponseItem
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(private val repository: UserRepository) : ViewModel() {
    private val _productDetail = MutableLiveData<DetailProductResponseItem>()
    val productDetail: LiveData<DetailProductResponseItem> get() = _productDetail

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    fun fetchProductDetail(productId: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getProductDetail(productId)
                _productDetail.value = result.firstOrNull()
            } catch (e: HttpException) {
                // Handle HTTP errors
                Log.e("API_ERROR", "HTTP Error: ${e.code()}", e)
            } catch (e: Exception) {
                Log.e("API_ERROR", "An unexpected error occurred", e)
                // Optionally, handle other types of exceptions here
            }
        }
    }
}
