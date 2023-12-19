package com.dicoding.cocokin.data.pref

import com.google.gson.annotations.SerializedName

data class AddToCartRequest (
    val idbarang: String,
    val nama: String,
    val harga: String,
    val sessionid: String,
    val gambar: String,
)
