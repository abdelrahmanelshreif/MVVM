package com.example.mvvm.allproducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.repo.ProductRepository

@Suppress("UNCHECKED_CAST")
class AllProductViewModelFactory(private val _repo: ProductRepository?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return _repo?.let { AllProductsViewModel(it) } as T
    }
}