package com.dicoding.cocokin.data.remote.response

import com.google.gson.annotations.SerializedName

data class CartResponse(
	@field:SerializedName("CartResponse")
	val cartResponse: List<CartResponseItem>
)

data class CartResponseItem(

	@field:SerializedName("idkeranjang")
	val idkeranjang: Int,

	@field:SerializedName("idbarang")
	val idbarang: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("catatan")
	val catatan: String,

	@field:SerializedName("sessionid")
	val sessionid: String,

	@field:SerializedName("gambar")
	val gambar: String,

	var isChecked: Boolean = false
)
