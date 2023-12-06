package com.dicoding.cocokin.data.remote.retrofit

import com.dicoding.cocokin.data.pref.UserLoginRequest
import com.dicoding.cocokin.data.pref.UserRegisterRequest
import com.dicoding.cocokin.data.remote.response.LoginResponse
import com.dicoding.cocokin.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("v1/accounts:signInWithPassword?key=AIzaSyCqhBWxEJUpiNK7ZimOCaqFjttVQt5LhZQ")
    suspend fun login(
        @Body request: UserLoginRequest
    ): LoginResponse

    @POST("v1/accounts:signUp?key=AIzaSyCqhBWxEJUpiNK7ZimOCaqFjttVQt5LhZQ")
    suspend fun register(
        @Body request: UserRegisterRequest
    ): RegisterResponse
}