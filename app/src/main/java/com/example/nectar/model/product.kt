package com.example.nectar.model

data class Product(
    val id: String,
    val name: String,
    val description: String,

    val quantity: String,
    val price: String,
    val imageResId: Int,

    val category: String
)