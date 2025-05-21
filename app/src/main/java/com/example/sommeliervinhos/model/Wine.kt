package com.example.sommeliervinhos.model

data class Wine(
    val name: String,
    val brand: String,
    val grape: String,
    val country: String,
    val region: String,
    val type: String,
    val alcohol: Double,
    val pairing: List<String>,
    var price: String, // 🔄 agora é var e do tipo String
    val image: String,
    val occasions: List<String>? = null
)

