package com.dicoding.cocokin.data

import android.util.Log
import com.dicoding.cocokin.data.pref.AddToCartRequest
import com.dicoding.cocokin.data.pref.DeleteRequest
import com.dicoding.cocokin.data.pref.UserLoginRequest
import com.dicoding.cocokin.data.pref.UserModel
import com.dicoding.cocokin.data.pref.UserPreference
import com.dicoding.cocokin.data.pref.UserRegisterRequest
import com.dicoding.cocokin.data.remote.response.AddCartResponse
import com.dicoding.cocokin.data.remote.response.CartResponseItem
import com.dicoding.cocokin.data.remote.response.DeleteResponse
import com.dicoding.cocokin.data.remote.response.DetailProductResponseItem
import com.dicoding.cocokin.data.remote.response.LoginResponse
import com.dicoding.cocokin.data.remote.response.PredictSizeResponse
import com.dicoding.cocokin.data.remote.response.ProductResponseItem
import com.dicoding.cocokin.data.remote.response.RegisterResponse
import com.dicoding.cocokin.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MultipartBody
import retrofit2.Response

class UserRepository private constructor
    (private val userPreference: UserPreference,
     private val authApiService: ApiService,
     private val productApiService: ApiService,
     private val predictApiService: ApiService) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val loginRequest = UserLoginRequest(email, password, true)
        val response = authApiService.login(loginRequest)

        if (response.registered) {
            saveSession(UserModel(email, response.localId, true))
        }
        return response
    }

    suspend fun getProductData(): List<ProductResponseItem> {
        return productApiService.getProductData()
    }

    suspend fun getProductDetail(id : Int): List<DetailProductResponseItem>{
        return productApiService.getProductDetail(id)
    }

    suspend fun register(displayName: String, email: String, password: String): RegisterResponse {
        val registerRequest = UserRegisterRequest(displayName, email, password)
        return authApiService.register(registerRequest)
    }

    suspend fun addToCart(
        idbarang: String,
        nama: String,
        harga: String,
        gambar: String
    ): AddCartResponse {
        val session = getSession().firstOrNull()

        if (session != null) {
            Log.d("DetailViewModel", "Session ID used: ${session.localId}")
            val addToCartRequest = AddToCartRequest(
                idbarang,
                nama,
                harga,
                session.localId,  // Use localId as the session ID
                gambar
            )
            return productApiService.addToCart(addToCartRequest)
        } else {
            // Handle the case where there is no valid session
            throw IllegalStateException("User not logged in")
        }
    }

    suspend fun deleteCartItem(idkeranjang: String): DeleteResponse {
        return productApiService.deleteCartItem(idkeranjang)
    }

    suspend fun getCart(sessionId: String): List<CartResponseItem> {
        return productApiService.getCart(sessionId)
    }

    suspend fun predictSize(file: MultipartBody.Part): PredictSizeResponse {
        return predictApiService.predictSize(file)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            authApiService: ApiService,
            productApiService: ApiService,
            userPreference: UserPreference,
            predictApiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, authApiService, productApiService, predictApiService)
            }.also { instance = it }
    }
}
