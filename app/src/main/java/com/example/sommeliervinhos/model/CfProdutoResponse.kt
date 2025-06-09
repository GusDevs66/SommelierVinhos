package com.example.sommeliervinhos.model

import com.google.gson.annotations.SerializedName

data class CfProdutoResponse(
    @SerializedName("data")
    val data: List<ProdutoData>
)

data class ProdutoData(
    @SerializedName("prod_id")
    val prodId: Int
)
