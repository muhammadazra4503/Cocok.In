package com.dicoding.cocokin.di

import android.content.Context
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.pref.UserPreference
import com.dicoding.cocokin.data.pref.datastore
import com.dicoding.cocokin.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.datastore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService, pref)
    }
}