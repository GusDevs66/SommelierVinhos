package com.example.sommeliervinhos.model

data class Wine(
    val name: String,
    val type: String,
    val harmonization: List<String>,
    val price: Double
)
