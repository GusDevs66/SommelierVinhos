package com.example.sommeliervinhos.model

import android.util.Log
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
    var price: String = "",
    @SerializedName("vinho_imagem")
    val image: String,
    val occasions: List<String>? = null,
    @SerializedName("vinho_sku")
    val sku: String
) : Comparable<Wine> {
    override fun compareTo(other: Wine): Int {
        return name.compareTo(other.name, ignoreCase = true)
    }

    fun containsKeyword(keyword: String): Boolean {
        return name.contains(keyword, ignoreCase = true) ||
                brand.contains(keyword, ignoreCase = true) ||
                grape.contains(keyword, ignoreCase = true) ||
                region.contains(keyword, ignoreCase = true)
    }

    fun logDetails(tag: String = "WINE_DEBUG") {
        Log.d(tag, "[DEBUG] $name | SKU: $sku | Preço: $price")
    }
}
