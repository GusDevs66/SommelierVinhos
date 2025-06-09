package com.example.sommeliervinhos.viewmodel
import com.google.gson.annotations.SerializedName

//data class PriceResponse(
//    val success: Boolean,
//    val data: List<PriceData>
//)

data class PriceData(
    @SerializedName("vlr_valores") val vlrValores: String
)
