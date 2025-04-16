package com.example.nectar.data.api

import com.example.nectar.model.Product
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>  // Wrap in Response

    @GET("cart")
    suspend fun getCartItems(): Response<List<Product>>

    @POST("cart")
    suspend fun addToCart(@Body product: Product): Response<Product>

    @DELETE("cart/{id}")
    suspend fun removeFromCart(@Path("id") id: Int): Response<Unit>
}