package com.dicoding.cocokin.data.pref

data class UserModel(
    val email: String,
    val localId: String,
    val isLogin: Boolean = false
)
