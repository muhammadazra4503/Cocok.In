package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class PredictViewModel(private val repository: UserRepository) : ViewModel() {
    private val _predictResult = MutableLiveData<String>()

    val predictResult: LiveData<String>
        get() = _predictResult

    suspend fun predictClothSize(file: File): String {
        return try {
            val filePart = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody())
            val predictedSize = repository.predictSize(filePart)
            predictedSize.sizePredicted
        } catch (e: Exception) {
            // Handle errors or exceptions
            throw RuntimeException("Error predicting size: ${e.message}")
        }
    }
}




