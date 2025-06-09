package com.example.sommeliervinhos.model

import com.google.gson.annotations.SerializedName

data class VinhoRequest(
    @SerializedName("vinho_sku")
    val vinhoSku: String
)
