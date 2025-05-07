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
    val price: Double,
    val image: String,
    val occasions: List<String>? = null // ✅ adicionado para permitir filtro por ocasião
)
