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

    fun predictClothSize(file: File) {
        viewModelScope.launch {
            try {
                val filePart = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody())
                val predictedSize = repository.predictSize(filePart)
                _predictResult.value = predictedSize.sizePredicted
            } catch (e: Exception) {
                // Handle errors or exceptions
                _predictResult.value = "Error predicting size: ${e.message}"
            }
        }
    }
}



