package com.example.mvvm.data.remote

import com.example.mvvm.data.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

data class ProductResponse(
    val products: List<Product>
)
