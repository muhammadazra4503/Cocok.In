package com.dicoding.cocokin.data.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("HistoryResponse")
	val historyResponse: List<HistoryResponseItem>
)

data class HistoryResponseItem(

	@field:SerializedName("idkeranjang")
	val idkeranjang: Int,

	@field:SerializedName("idbarang")
	val idbarang: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("idriwayat")
	val idriwayat: Int,

	@field:SerializedName("totalharga")
	val totalharga: Int,

	@field:SerializedName("catatan")
	val catatan: String,

	@field:SerializedName("sessionid")
	val sessionid: String,

	@field:SerializedName("gambar")
	val gambar: String,

	@field:SerializedName("alamat")
	val alamat: String
)
