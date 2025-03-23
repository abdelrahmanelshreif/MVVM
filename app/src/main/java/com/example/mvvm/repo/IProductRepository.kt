package com.example.mvvm.repo

import com.example.mvvm.data.models.Product
import com.example.mvvm.data.remote.ProductResponse
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    suspend fun getProducts(): Flow<ProductResponse>

    suspend fun addProductToFav(product: Product): Long

    suspend fun deleteProductFromFav(product: Product): Int

    suspend fun getFavProducts(): Flow<List<Product>>
}
