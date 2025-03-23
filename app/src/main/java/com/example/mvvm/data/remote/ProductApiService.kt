package com.example.mvvm.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProductApiServices {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}

object RetrofitHelper {
    private const val BASE_URL = "https://dummyjson.com/"

    private val retrofitInstance =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val apiservice = retrofitInstance.create(ProductApiServices::class.java)
}