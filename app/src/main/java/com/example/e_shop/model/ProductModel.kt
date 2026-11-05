package com.example.e_shop.model

data class ProductModel(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val actualprice: String = "",
    val category: String = "",
    val images: List<String> = emptyList(),
    val otherDetails : Map<String,String> = emptyMap(),


)
