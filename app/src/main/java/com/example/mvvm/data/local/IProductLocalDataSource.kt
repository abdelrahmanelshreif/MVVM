package com.example.mvvm.data.local

import com.example.mvvm.data.models.Product
import kotlinx.coroutines.flow.Flow


interface IProductLocalDataSource {
    suspend fun getFavProducts(): Flow<List<Product>>

    suspend fun addProduct(product: Product): Long

    suspend fun removeProduct(product: Product): Int
}
