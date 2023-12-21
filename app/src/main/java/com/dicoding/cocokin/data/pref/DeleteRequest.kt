package com.dicoding.cocokin.data.pref

import com.google.gson.annotations.SerializedName

data class DeleteRequest(
    @SerializedName("idkeranjang")
    val idkeranjang: String
)

