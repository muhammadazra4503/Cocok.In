package com.dicoding.cocokin.data.remote.response

import com.google.gson.annotations.SerializedName

data class PaymentHistoryResponse(

	@field:SerializedName("Message")
	val message: String
)
