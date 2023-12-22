package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.data.ResultPayment
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.pref.UserModel
import com.dicoding.cocokin.data.remote.response.HistoryResponseItem
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository): ViewModel() {

    val userSession: LiveData<UserModel> = repository.getSession().asLiveData()

    private val _historyResult = MutableLiveData<ResultPayment<List<HistoryResponseItem>>>()
    val historyResult: LiveData<ResultPayment<List<HistoryResponseItem>>>
        get() = _historyResult

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun fetchHistory() {
        viewModelScope.launch {
            try {
                val session = repository.getSession().firstOrNull()
                if (session != null) {
                    val history = repository.getHistory(session.localId)
                    _historyResult.value = ResultPayment.SuccessWithData(history)
                } else {
                    // Handle the case where there is no valid session
                    _historyResult.value = ResultPayment.Error("User not logged in")
                }
            } catch (e: Exception) {
                _historyResult.value = ResultPayment.Error("Failed to fetch history: ${e.message}")
            }
        }
    }
}



