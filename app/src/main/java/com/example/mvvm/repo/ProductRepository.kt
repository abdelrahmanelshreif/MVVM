package com.example.mvvm.repo

import com.example.mvvm.data.local.ProductLocalDataSource
import com.example.mvvm.data.models.Product
import com.example.mvvm.data.remote.ProductRemoteDataSource
import com.example.mvvm.data.remote.ProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository private constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource
) : IProductRepository {
    companion object {
        private var productRepository: ProductRepository? = null
        fun getInstance(
            remoteDataSource: ProductRemoteDataSource,
            localDataSource: ProductLocalDataSource
        ): ProductRepository? {
            if (productRepository == null) {
                productRepository = ProductRepository(remoteDataSource, localDataSource)
            }
            return productRepository
        }
    }

    override suspend fun getProducts(): Flow<ProductResponse> = flow {
        val response = remoteDataSource.getProducts()
        emit(response)
    }


    override suspend fun addProductToFav(product: Product): Long {
        return localDataSource.addProduct(product)
    }

    override suspend fun deleteProductFromFav(product: Product): Int {
        return localDataSource.removeProduct(product)
    }

    override suspend fun getFavProducts(): Flow<List<Product>> {
        return localDataSource.getFavProducts()
    }

}