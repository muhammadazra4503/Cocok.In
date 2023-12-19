//package com.dicoding.cocokin.ui.viewmodel
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.dicoding.cocokin.data.UserRepository
//import com.dicoding.cocokin.data.remote.response.CartResponseItem
//import kotlinx.coroutines.launch
//import retrofit2.HttpException
//import java.io.IOException
//
//class CartViewModel(private val repository: UserRepository) : ViewModel() {
//
//    private val _cartList = MutableLiveData<List<CartResponseItem>>()
//    val cartList: LiveData<List<CartResponseItem>> get() = _cartList
//
//    private val _errorState = MutableLiveData<String>()
//    val errorState: LiveData<String> get() = _errorState
//
//    fun fetchCartData() {
//        viewModelScope.launch {
//            try {
//                val result = repository.getCartData()
//                _cartList.value = result
//            } catch (e: Exception) {
//                val errorMessage = handleHttpException(e)
//                _errorState.value = errorMessage
//            }
//        }
//    }
//
//    private fun handleHttpException(exception: Exception): String {
//        return when (exception) {
//            is HttpException -> {
//                Log.e("API_ERROR", "HTTP Error: ${exception.code()}", exception)
//                "HTTP Error: ${exception.code()}"
//            }
//            is IOException -> {
//                Log.e("API_ERROR", "Network Error: Periksa koneksi internet Anda", exception)
//                "Network Error: Periksa koneksi internet Anda"
//            }
//            else -> {
//                Log.e("API_ERROR", "Terjadi kesalahan yang tidak terduga", exception)
//                "Terjadi kesalahan yang tidak terduga"
//            }
//        }
//    }
//}