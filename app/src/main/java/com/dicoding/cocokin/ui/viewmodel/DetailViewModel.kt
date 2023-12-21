package com.dicoding.cocokin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.remote.response.AddCartResponse
import com.dicoding.cocokin.data.remote.response.DetailProductResponseItem
import com.dicoding.cocokin.data.remote.response.ProductResponseItem
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(private val repository: UserRepository) : ViewModel() {

    private val _productDetail = MutableLiveData<DetailProductResponseItem>()
    val productDetail: LiveData<DetailProductResponseItem> get() = _productDetail

    private val _addToCartResult = MutableLiveData<AddCartResponse>()
    val addToCartResult: LiveData<AddCartResponse> get() = _addToCartResult

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
            }
        }
    }

    fun addToCart(idbarang: String, nama: String, harga: String, gambar: String) {
        viewModelScope.launch {
            try {
                val result = repository.addToCart(idbarang, nama, harga, gambar)
                _addToCartResult.value = result
            } catch (e: HttpException) {
                // Handle HTTP errors
                Log.e("API_ERROR", "HTTP Error: ${e.code()}", e)
                Log.e("API_ERROR", "Response Body: ${e.response()?.errorBody()?.string()}", e)
                _errorState.value = "Failed to add item to cart. HTTP Error: ${e.code()}"
            } catch (e: Exception) {
                Log.e("API_ERROR", "An unexpected error occurred", e)
                _errorState.value = "Failed to add item to cart. An unexpected error occurred."
            }
        }
    }
}
