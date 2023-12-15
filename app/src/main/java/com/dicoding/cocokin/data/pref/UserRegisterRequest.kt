package com.dicoding.cocokin.data.pref

data class UserRegisterRequest(
    val name : String,
    val email: String,
    val password: String,
    // tambahkan properti lain sesuai kebutuhan
)