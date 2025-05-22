package com.example.sommeliervinhos.model

import com.google.gson.annotations.SerializedName

data class Wine(
    @SerializedName("vinho_nome")
    val name: String,
    @SerializedName("vinho_marca")
    val brand: String,
    @SerializedName("vinho_proporcao")
    val grape: String,
    @SerializedName("vinho_pais")
    val country: String,
    @SerializedName("vinho_regiao")
    val region: String,
    @SerializedName("vinho_tipo")
    val type: String,
    @SerializedName("vinho_teor")
    val alcohol: String,
    @SerializedName("vinho_harmonizacao")
    val pairing: String,
    var price: String,
    @SerializedName("vinho_imagem")
    val image: String,
    val occasions: List<String>? = null,
    @SerializedName("prod_cod")
    val prod_cod: String
)
