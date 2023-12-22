package com.dicoding.cocokin.data.remote.response

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
	@field:SerializedName("requestType")
	val requestType: String,

	@field:SerializedName("email")
	val email: String
)
