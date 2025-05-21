package com.example.sommeliervinhos.data

import com.example.sommeliervinhos.model.Wine
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.Call

data class ApiResponse(
    val data: List<Wine>
)

data class PriceRequest(
    val vlr_produto: Int
)

data class PriceResponse(
    val data: PriceData?
)

data class PriceData(
    val vlr_valores: String?
)

interface VinhoService {

    @GET("aditivos/adt_vinho")
    fun getVinhos(
        @Query("token") token: String
    ): Call<ApiResponse>

    @POST("internal/cf_valor")
    fun getPreco(
        @Query("token") token: String,
        @Body payload: PriceRequest
    ): Call<PriceResponse>
}
