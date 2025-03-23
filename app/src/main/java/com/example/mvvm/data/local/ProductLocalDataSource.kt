package com.example.mvvm.data.local

import com.example.mvvm.data.models.Product
import kotlinx.coroutines.flow.Flow

class ProductLocalDataSource(private val productDAO: ProductDAO) : IProductLocalDataSource {
    override suspend fun getFavProducts(): Flow<List<Product>> {
        return productDAO.getAll()
    }

    override suspend fun addProduct(product: Product):Long {
        return productDAO.insertProduct(product)
    }

    override suspend fun removeProduct(product: Product) :Int{
        return productDAO.deleteProduct(product)
    }
}