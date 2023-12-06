package com.dicoding.cocokin.data

import com.dicoding.cocokin.data.pref.UserLoginRequest
import com.dicoding.cocokin.data.pref.UserModel
import com.dicoding.cocokin.data.pref.UserPreference
import com.dicoding.cocokin.data.pref.UserRegisterRequest
import com.dicoding.cocokin.data.remote.response.LoginResponse
import com.dicoding.cocokin.data.remote.response.RegisterResponse
import com.dicoding.cocokin.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(private val userPreference: UserPreference, private val apiService: ApiService) {
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
        val response = apiService.login(loginRequest)

        if (response.registered) {
            saveSession(UserModel(email, response.idToken, true))
        }
        return response
    }

    suspend fun register(email: String, password: String): RegisterResponse {
        val registerRequest = UserRegisterRequest(email, password)
        return apiService.register(registerRequest)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}
