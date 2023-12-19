package com.dicoding.cocokin.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(

	@field:SerializedName("DetailProductResponse")
	val detailProductResponse: List<DetailProductResponseItem>
)

data class DetailProductResponseItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("gambar")
	val gambar: String
)
