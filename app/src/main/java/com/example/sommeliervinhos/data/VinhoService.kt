package com.example.sommeliervinhos.data

import com.example.sommeliervinhos.model.Wine
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import com.google.gson.annotations.SerializedName

// resposta de aditivos (já existia)
data class ApiResponse(
    val data: List<Wine>
)

// request/response de preço (já existia)
data class PriceRequest(
    val vlr_produto: Int
)
data class PriceResponse(
    val data: PriceData?
)
data class PriceData(
    val vlr_valores: String?
)

// **NOVO**: resposta de cf_produto
data class CfProdutoResponse(
    val data: List<CfProduto>
)
data class CfProduto(
    @SerializedName("prod_id") val prodId: Int,
    @SerializedName("prod_sku") val prodSku: String
)

interface VinhoService {

    @GET("aditivos/adt_vinho")
    fun getVinhos(
        @Query("token") token: String
    ): Call<ApiResponse>

    // **NOVO**: busca o cliente-produto pelo SKU (usando LIKE no backend)
    @GET("cf_produto")
    fun getProdutos(
        @Query("token") token: String,
        @Query("prod_sku") prodSku: String
    ): Call<CfProdutoResponse>

    @POST("internal/cf_valor?token=396188e26f79af8b67be0dd5d0a6776f")
    fun getPreco(@Body body: Map<String, Int>): Call<PriceResponse>

}
