package com.dicoding.cocokin.data

import com.dicoding.cocokin.data.pref.UserLoginRequest
import com.dicoding.cocokin.data.pref.UserModel
import com.dicoding.cocokin.data.pref.UserPreference
import com.dicoding.cocokin.data.pref.UserRegisterRequest
import com.dicoding.cocokin.data.remote.response.DetailProductResponseItem
import com.dicoding.cocokin.data.remote.response.LoginResponse
import com.dicoding.cocokin.data.remote.response.ProductResponse
import com.dicoding.cocokin.data.remote.response.ProductResponseItem
import com.dicoding.cocokin.data.remote.response.RegisterResponse
import com.dicoding.cocokin.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor
    (private val userPreference: UserPreference,
     private val authApiService: ApiService,
     private val productApiService: ApiService) {
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

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            authApiService: ApiService,
            productApiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, authApiService, productApiService)
            }.also { instance = it }
    }
}
