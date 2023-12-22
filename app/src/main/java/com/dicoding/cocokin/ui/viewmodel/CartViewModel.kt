package com.dicoding.cocokin.ui.viewmodel

import android.util.Log
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

    private val _selectedItems = MutableLiveData<Set<CartResponseItem>>()
    val selectedItems: LiveData<Set<CartResponseItem>> get() = _selectedItems

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    fun getSelectedItems(): Set<CartResponseItem> {
        return _selectedItems.value.orEmpty()
    }

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

            val selectedItems = currentCartItems.filter { it.isChecked }.toSet()
            _selectedItems.value = selectedItems
        }
    }

    fun deleteCartItem(idkeranjang: String) {
        viewModelScope.launch {
            try {
                // Log before attempting to delete
                Log.d("CartViewModel", "Attempting to delete item with idkeranjang: $idkeranjang")

                repository.deleteCartItem(idkeranjang)

                // Log after successful delete
                Log.d("CartViewModel", "Successfully deleted item with idkeranjang: $idkeranjang")
                fetchCart()
            } catch (e: HttpException) {
                // Handle HTTP exceptions for delete operation
                val errorMessage = when (val responseCode = e.code()) {
                    401 -> "Unauthorized. Please log in again."
                    403 -> "Access forbidden."

                    else -> "Failed to delete cart item. Error code: $responseCode"
                }
                // Log HTTP exception
                Log.e("CartViewModel", "HTTP Exception while deleting item. Error: $errorMessage", e)
                _errorState.value = errorMessage
            } catch (e: Exception) {
                // Handle other exceptions for delete operation
                // Log other exceptions
                Log.e("CartViewModel", "Exception while deleting item. Error: ${e.message}", e)
                _errorState.value = "Failed to delete cart item: ${e.message}"
            }
        }
    }
}