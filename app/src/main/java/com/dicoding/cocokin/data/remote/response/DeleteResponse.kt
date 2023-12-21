package com.dicoding.cocokin.data.remote.response

import com.google.gson.annotations.SerializedName

data class DeleteResponse(

	@field:SerializedName("Message")
	val message: String
)
