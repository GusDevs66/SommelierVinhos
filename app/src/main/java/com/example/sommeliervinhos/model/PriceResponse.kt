package com.example.sommeliervinhos.model

import com.google.gson.annotations.SerializedName

data class PriceResponse(
    @SerializedName("data")
    val data: List<PriceData>
)

data class PriceData(
    @SerializedName("vlr_valores")
    val vlrValores: String
)
