package com.dicoding.cocokin.data.remote.retrofit

import com.dicoding.cocokin.data.pref.AddToCartRequest
import com.dicoding.cocokin.data.pref.UserLoginRequest
import com.dicoding.cocokin.data.pref.UserRegisterRequest
import com.dicoding.cocokin.data.remote.response.AddCartResponse
import com.dicoding.cocokin.data.remote.response.DetailProductResponseItem
import com.dicoding.cocokin.data.remote.response.LoginResponse
import com.dicoding.cocokin.data.remote.response.ProductResponseItem
import com.dicoding.cocokin.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("v1/accounts:signInWithPassword?key=AIzaSyCraSu3TwUfF0VAgVPiVXUZOJsPgxP33A8")
    suspend fun login(
        @Body request: UserLoginRequest
    ): LoginResponse

    @POST("v1/accounts:signUp?key=AIzaSyCraSu3TwUfF0VAgVPiVXUZOJsPgxP33A8")
    suspend fun register(
        @Body request: UserRegisterRequest
    ): RegisterResponse

    @GET("api/data")
    suspend fun getProductData(): List<ProductResponseItem>

    @GET("api/dataById/{id}")
    suspend fun getProductDetail(
        @Path("id") id: Int
    ): List<DetailProductResponseItem>

    @POST("api/masukkeranjang")
    suspend fun addToCart(@Body request: AddToCartRequest): AddCartResponse
}