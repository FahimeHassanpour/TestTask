package com.example.myproject.domain

data class Product(
    val id: Long,
    val title: String,
    val vendor: String,
    val productType: String,
    val price: Double,
    val variantsJson: String = "[]"
)
