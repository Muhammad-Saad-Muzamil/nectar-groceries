package com.example.nectar.data

import com.example.nectar.data.api.ApiService

class  ProductRepository(private val api: ApiService) {
    suspend fun getProducts() = api.getProducts()
}
