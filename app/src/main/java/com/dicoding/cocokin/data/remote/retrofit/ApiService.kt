package com.dicoding.cocokin.data.remote.retrofit

import com.dicoding.cocokin.data.pref.AddToCartRequest
import com.dicoding.cocokin.data.pref.PaymentHistoryRequest
import com.dicoding.cocokin.data.pref.UserLoginRequest
import com.dicoding.cocokin.data.pref.UserRegisterRequest
import com.dicoding.cocokin.data.remote.response.AddCartResponse
import com.dicoding.cocokin.data.remote.response.CartResponseItem
import com.dicoding.cocokin.data.remote.response.DeleteResponse
import com.dicoding.cocokin.data.remote.response.DetailProductResponseItem
import com.dicoding.cocokin.data.remote.response.HistoryResponseItem
import com.dicoding.cocokin.data.remote.response.LoginResponse
import com.dicoding.cocokin.data.remote.response.PaymentHistoryResponse
import com.dicoding.cocokin.data.remote.response.PredictSizeResponse
import com.dicoding.cocokin.data.remote.response.ProductResponseItem
import com.dicoding.cocokin.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @GET("api/keranjang/{sessionid}")
    suspend fun getCart(@Path("sessionid") sessionId: String): List<CartResponseItem>

    @Multipart
    @POST("predict_size")
    suspend fun predictSize(
        @Part file: MultipartBody.Part
    ): PredictSizeResponse

    @DELETE("api/hapusItemKeranjang/{idkeranjang}")
    suspend fun deleteCartItem(@Path("idkeranjang") idkeranjang: String): DeleteResponse

    @POST("api/masukriwayat")
    suspend fun paymentHistory(@Body request: PaymentHistoryRequest): PaymentHistoryResponse

    @GET("api/riwayat/{sessionid}")
    suspend fun getHistory(@Path("sessionid") sessionId: String): List<HistoryResponseItem>

}