package com.dicoding.cocokin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cocokin.R
import com.dicoding.cocokin.data.UserRepository
import com.dicoding.cocokin.data.dummy.ProductDummy
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository): ViewModel() {
    private val data = MutableLiveData<List<ProductDummy>>()

    init {
        data.value = initData()
    }

    private fun initData(): List<ProductDummy> {
        return listOf(
            ProductDummy(R.drawable.black_logo,"Produk A"),
            ProductDummy(R.drawable.black_logo,"Produk B"),
            ProductDummy(R.drawable.black_logo,"Produk C"),
            ProductDummy(R.drawable.black_logo,"Produk D"),
            ProductDummy(R.drawable.black_logo,"Produk E"),
            ProductDummy(R.drawable.black_logo,"Produk F"),
            ProductDummy(R.drawable.black_logo,"Produk G"),
            ProductDummy(R.drawable.black_logo,"Produk H"),
            ProductDummy(R.drawable.black_logo,"Produk I"),
            ProductDummy(R.drawable.black_logo,"Produk J"),
        )
    }

    fun getData(): LiveData<List<ProductDummy>> = data
}