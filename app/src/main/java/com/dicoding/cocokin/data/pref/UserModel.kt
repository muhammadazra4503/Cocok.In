package com.dicoding.cocokin.data.pref

data class UserModel(
    val email: String,
    val localId: String,
    val isLoggedIn: Boolean,
    val displayName: String // Add this property
)
