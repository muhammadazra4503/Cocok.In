package com.dicoding.cocokin.data.pref

data class UserLoginRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)