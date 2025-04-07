package com.example.nectar.ui.cart

data class CartItem(
    val name: String,
    val imageRes: Int,
    val description: String,
    val price: Double ,
    var quantity : Int = 1,
)
