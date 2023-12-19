package com.dicoding.cocokin.data.remote.response

import com.google.gson.annotations.SerializedName

data class CartResponseItem(
    @SerializedName("idkeranjang")
    val idKeranjang: Int,

    @SerializedName("idbarang")
    val idBarang: Int,

    @SerializedName("sessionid")
    val sessionId: String,

    @SerializedName("nama")
    val nama: String,

    @SerializedName("harga")
    val harga: Int,

    @SerializedName("gambar")
    val gambar: String,

    @SerializedName("catatan")
    val catatan: String
)