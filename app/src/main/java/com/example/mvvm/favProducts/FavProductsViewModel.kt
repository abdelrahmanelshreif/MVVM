package com.example.mvvm.favProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.models.Product
import com.example.mvvm.repo.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FavProductsViewModel(private val repo: ProductRepository) : ViewModel() {

    private var _mutableProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _mutableProducts

    private val mutableMessage: MutableLiveData<String> = MutableLiveData("")
    val message: LiveData<String> = mutableMessage


    suspend fun getFavProducts() {
//        viewModelScope.launch(Dispatchers.IO) {
        try {
            val productList = repo.getFavProducts()
//                _mutableProducts.postValue(productList)
            productList.flowOn(Dispatchers.IO)
                .collect {
                    _mutableProducts.value = it
                }
        } catch (e: Exception) {
            mutableMessage.postValue("Failed to fetch Fav products")
        }
//        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteProductFromFav(product)
        }
    }


}