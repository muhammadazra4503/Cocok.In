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

    private val _addToCartSuccess = MutableLiveData<Boolean>()
    val addToCartSuccess: LiveData<Boolean> get() = _addToCartSuccess

    private val _addToCartError = MutableLiveData<String?>()
    val addToCartError: LiveData<String?> get() =_addToCartError

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

    fun addToCart(idbarang: String, nama: String, harga: String, sessionid: String, gambar: String) {
        viewModelScope.launch {
            try {
                val addToCartResponse = repository.addToCart(idbarang, nama, harga, sessionid, gambar)

                if (addToCartResponse.idbarang.isNotBlank()) {
                    // Cart item added successfully
                    _addToCartSuccess.value = true
                } else {
                    // Handle the case where the server returns an error in the response
                    _addToCartError.value = "Failed to add to cart: Unexpected response format"
                }
            } catch (e: HttpException) {
                // Handle HTTP errors
                _addToCartError.value = "HTTP Error: ${e.code()}"
                Log.e("API_ERROR", "HTTP Error: ${e.code()}", e)
            } catch (e: Exception) {
                // Handle other types of exceptions
                _addToCartError.value = "An unexpected error occurred"
                Log.e("API_ERROR", "An unexpected error occurred", e)
               }
        }
    }

}
