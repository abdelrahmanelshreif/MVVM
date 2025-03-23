package com.example.mvvm.allproducts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.models.Product
import com.example.mvvm.data.models.Response
import com.example.mvvm.repo.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AllProductsViewModel(private val repo: ProductRepository) : ViewModel() {

    private val _productsList = MutableStateFlow<Response>(Response.Loading)
    val productsList = _productsList.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                val products = repo.getProducts()
                products
                    .catch { ex ->
                        _productsList.value = Response.Failure(ex)
                        _toastEvent.emit("Can't Fetch Products Right now Please try again later")
                    }
                    .collect {
                        _productsList.value = Response.Success(it.products)

                    }
            } catch (   ex: Exception) {
                _productsList.value = Response.Failure(ex)
                _toastEvent.emit("Error ${ex.message}")

            }
        }
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addProductToFav(product)
        }
    }


}