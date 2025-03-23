package com.example.mvvm.favProducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.allproducts.AllProductsViewModel
import com.example.mvvm.repo.ProductRepository

@Suppress("UNCHECKED_CAST")
class FavProductsViewModelFactory(private val _repo: ProductRepository?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return _repo?.let { FavProductsViewModel(it) } as T
    }
}