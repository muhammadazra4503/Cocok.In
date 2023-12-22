package com.dicoding.cocokin.data.pref

data class UserRegisterRequest(
    val displayName : String,
    val email: String,
    val password: String,
)