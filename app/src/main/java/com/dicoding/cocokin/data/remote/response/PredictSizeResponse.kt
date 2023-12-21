package com.dicoding.cocokin.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictSizeResponse(

	@field:SerializedName("Size Predicted")
	val sizePredicted: String
)
