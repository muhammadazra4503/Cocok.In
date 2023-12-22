package com.dicoding.cocokin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.Result
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.pref.PaymentHistoryRequest
import com.dicoding.cocokin.data.remote.response.CartResponseItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CheckoutViewModel(private val repository: UserRepository) : ViewModel() {
    private val _paymentResult = MutableLiveData<Result>()
    val paymentResult: LiveData<Result>
        get() = _paymentResult

    private val _errorState = MutableLiveData<String>()

    fun processPayment(selectedItems: List<CartResponseItem>, address: String) {
        viewModelScope.launch {
            try {
                var totalHarga = 0

                // Calculate total price outside the loop
                for (item in selectedItems) {
                    totalHarga += item.harga
                }

                for (item in selectedItems) {
                    val paymentInfoRequest = PaymentHistoryRequest(
                        idkeranjang = item.idkeranjang.toString(),
                        idbarang = item.idbarang.toString(),
                        sessionid = item.sessionid ?: "",
                        nama = item.nama ?: "",
                        harga = item.harga.toString(),
                        gambar = item.gambar ?: "",
                        catatan = item.catatan ?: "",
                        totalharga = totalHarga.toString(),
                        alamat = address
                    )


                    val paymentInfoResponse = suspendCoroutine { continuation ->
                        viewModelScope.launch {
                            try {
                                val response = repository.processPaymentHistory(paymentInfoRequest)
                                continuation.resume(Result.Success)
                            } catch (e: Exception) {
                                continuation.resume(Result.Error("Failed to send payment information: ${e.message}"))
                            }
                        }
                    }

                    if (paymentInfoResponse is Result.Error) {
                        _paymentResult.value = paymentInfoResponse
                        return@launch
                    }
                }

                // Call deleteCartItems after processing all items
                deleteCartItems(selectedItems)

                _paymentResult.value = Result.Success
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized: Invalid credentials"
                    403 -> "Forbidden: Insufficient permissions"
                    404 -> "Not Found: Resource not available"
                    else -> "HTTP Error: ${e.code()}"
                }
                _paymentResult.value = Result.Error(errorMessage)
            } catch (e: Exception) {
                _paymentResult.value = Result.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    private suspend fun deleteCartItems(cartItems: List<CartResponseItem>) {
        try {
            coroutineScope {
                val cartItemIds = cartItems.map { it.idkeranjang.toString() }
                repository.deleteCartItems(cartItemIds)
            }
            if (!coroutineContext.isActive) {
                return
            }
        } catch (e: HttpException) {
            handleDeleteCartItemsHttpException(e)
        } catch (e: Exception) {
            handleDeleteCartItemsException(e)
        }
    }


    private fun handleDeleteCartItemsHttpException(e: HttpException) {
        val errorMessage = when (val responseCode = e.code()) {
            401 -> "Unauthorized. Please log in again."
            403 -> "Access forbidden."
            else -> "Failed to delete cart items. Error code: $responseCode"
        }
        Log.e("CartViewModel", "HTTP Exception while deleting items. Error: $errorMessage", e)
        _errorState.value = errorMessage
    }

    private fun handleDeleteCartItemsException(e: Exception) {
        Log.e("CartViewModel", "Exception while deleting items. Error: ${e.message}", e)
        _errorState.value = "Failed to delete cart items: ${e.message}"
    }

}

