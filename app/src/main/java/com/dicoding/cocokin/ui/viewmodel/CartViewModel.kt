package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.remote.response.CartResponseItem
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CartViewModel(private val repository: UserRepository) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartResponseItem>>()
    val cartItems: LiveData<List<CartResponseItem>> get() = _cartItems

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    fun fetchCart() {
        viewModelScope.launch {
            try {
                val session = repository.getSession().firstOrNull()
                if (session != null) {
                    val cartItemsResult = repository.getCart(session.localId)
                    _cartItems.value = cartItemsResult
                } else {
                    _errorState.value = "User not logged in"
                }
            } catch (e: HttpException) {
                // Handle HTTP exceptions
                val errorMessage = when (val responseCode = e.code()) {
                    401 -> "Unauthorized. Please log in again."
                    403 -> "Access forbidden."

                    else -> "Failed to fetch cart items. Error code: $responseCode"
                }
                _errorState.value = errorMessage
            } catch (e: Exception) {
                _errorState.value = "Failed to fetch cart items: ${e.message}"
            }
        }
    }

    fun updateCartItemState(cartItem: CartResponseItem, isChecked: Boolean) {
        val currentCartItems = _cartItems.value.orEmpty().toMutableList()
        val index = currentCartItems.indexOfFirst { it.idkeranjang == cartItem.idkeranjang }

        if (index != -1) {
            currentCartItems[index].isChecked = isChecked
            _cartItems.value = currentCartItems
        }
    }
}