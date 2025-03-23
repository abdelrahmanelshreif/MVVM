package com.example.mvvm.allproducts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.models.Product
import com.example.mvvm.repo.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllProductsViewModel(private val repo: ProductRepository) : ViewModel() {

    private val _mutableProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _mutableProducts

    private val mutableMessage: MutableLiveData<String> = MutableLiveData("")
    val message: LiveData<String> = mutableMessage


    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val productList = repo.getProducts().products
                _mutableProducts.postValue(productList)
            } catch (e: Exception) {
                mutableMessage.postValue("Failed to fetch products")
            }
        }
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addProductToFav(product)
        }
    }


}