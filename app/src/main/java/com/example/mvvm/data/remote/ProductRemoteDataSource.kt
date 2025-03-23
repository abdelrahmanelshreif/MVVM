package com.example.mvvm.data.remote

import com.example.mvvm.data.local.ProductDAO
import com.example.mvvm.data.models.Product

class ProductRemoteDataSource(private val apiServices: ProductApiServices) {

    suspend fun getProducts(): ProductResponse {
        return apiServices.getProducts()
    }

}