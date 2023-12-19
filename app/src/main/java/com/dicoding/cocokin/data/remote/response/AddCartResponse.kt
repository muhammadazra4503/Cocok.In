package com.dicoding.cocokin.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddCartResponse(

	@field:SerializedName("idbarang")
	val idbarang: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: String,

	@field:SerializedName("sessionid")
	val sessionid: String,

	@field:SerializedName("gambar")
	val gambar: String
)
