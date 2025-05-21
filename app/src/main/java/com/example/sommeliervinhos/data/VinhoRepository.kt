package com.example.sommeliervinhos.data

import android.util.Log
import com.example.sommeliervinhos.model.Wine
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VinhoRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://apicartazfacil.com/public/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val vinhoService = retrofit.create(VinhoService::class.java)
    private val token = "396188e26f79af8b67be0dd5d0a6776f"

    fun fetchVinhos(
        onSuccess: (List<Wine>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val call = vinhoService.getVinhos(token)
        call.enqueue(object : retrofit2.Callback<ApiResponse> {
            override fun onResponse(
                call: retrofit2.Call<ApiResponse>,
                response: retrofit2.Response<ApiResponse>
            ) {
                if (response.isSuccessful) {
                    val vinhos = response.body()?.data ?: emptyList()
                    onSuccess(vinhos)
                } else {
                    onError(Exception("Erro ao buscar vinhos: ${response.code()}"))
                }
            }

            override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun fetchPreco(
        prodCod: Int,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val body = PriceRequest(prodCod)
        val call = vinhoService.getPreco(token, body)
        call.enqueue(object : retrofit2.Callback<PriceResponse> {
            override fun onResponse(
                call: retrofit2.Call<PriceResponse>,
                response: retrofit2.Response<PriceResponse>
            ) {
                if (response.isSuccessful) {
                    val preco = response.body()?.data?.vlr_valores ?: ""
                    onSuccess(preco)
                } else {
                    onError(Exception("Erro ao buscar preço: ${response.code()}"))
                }
            }

            override fun onFailure(call: retrofit2.Call<PriceResponse>, t: Throwable) {
                onError(t)
            }
        })
    }
}
