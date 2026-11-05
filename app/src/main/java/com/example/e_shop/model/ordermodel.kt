package com.example.e_shop.model

import com.google.firebase.Timestamp

data class ordermodel(
    val id : String = "",
    val date : Timestamp = Timestamp.now(),
    val userId : String = "",
    val items : Map<String, Long> = mapOf(),
    val status : String = "",
    val address : String = "",
)
