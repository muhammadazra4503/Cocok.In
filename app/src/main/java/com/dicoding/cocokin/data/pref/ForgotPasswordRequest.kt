package com.dicoding.cocokin.data.pref

data class ForgotPasswordRequest(
    val email: String,
    val requestType: String = "PASSWORD_RESET"
)
