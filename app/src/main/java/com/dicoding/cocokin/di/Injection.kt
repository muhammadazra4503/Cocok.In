package com.dicoding.cocokin.di

import android.content.Context
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.pref.UserPreference
import com.dicoding.cocokin.data.pref.datastore
import com.dicoding.cocokin.data.remote.retrofit.ApiConfig
import com.dicoding.cocokin.data.remote.retrofit.ApiService

object Injection {
    // For login and registration
    private fun provideAuthService(): ApiService {
        return ApiConfig.getApiService("https://identitytoolkit.googleapis.com/")
    }

    // For product data
    private fun provideProductService(): ApiService {
        return ApiConfig.getApiService("https://capstone-408207.et.r.appspot.com/")
    }

    private fun providePredictService(): ApiService {
        return ApiConfig.getApiService("https://mlcapstone-lsfmfisyzq-ts.a.run.app/")
    }

    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.datastore)
        val authApiService = provideAuthService()
        val productApiService = provideProductService()
        val predictApiService = providePredictService()
        return UserRepository.getInstance(authApiService, productApiService, pref, predictApiService)
    }

}