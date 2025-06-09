package com.example.sommeliervinhos.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.sommeliervinhos.model.ApiResponse
import com.example.sommeliervinhos.model.CfProdutoResponse
import com.example.sommeliervinhos.model.PriceResponse
import com.google.gson.annotations.SerializedName

interface VinhoService {
    @GET("aditivos/adt_vinho")
    fun getVinhos(@Query("token") token: String): Call<ApiResponse>

    @GET("cf_produto")
    suspend fun getProdutos(
        @Query("token") token: String,
        @Query("prod_sku") prodSku: String
    ): CfProdutoResponse

    @GET("cf_valor")
    suspend fun getPreco(
        @Query("token") token: String,
        @Query("vlr_produto") prodId: Int
    ): PriceResponse
}

data class ProdRequest(
    @SerializedName("prod_nome") val prodNome: String,
    @SerializedName("prod_cod") val prodCod: String,
    @SerializedName("prod_desc") val prodDesc: String,
    @SerializedName("prod_sku") val prodSku: String
)
