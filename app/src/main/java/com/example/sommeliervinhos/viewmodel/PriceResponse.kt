package com.example.sommeliervinhos.model
import com.google.gson.annotations.SerializedName

data class PriceData(
    @SerializedName("vlr_valores")
    val vlr_valores: String
)

data class PriceResponse(
    @SerializedName("data")
    val data: PriceData
)
