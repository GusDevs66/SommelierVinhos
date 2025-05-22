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
                    Log.d("VINHO_REPO", "Vinhos recebidos da API: ${vinhos.size}")
                    vinhos.forEach { Log.d("VINHO_REPO", "Vinho: ${it.name}, Tipo: ${it.type}, Harmonizacao: ${it.pairing}") }
                    onSuccess(vinhos)
                } else {
                    Log.e("VINHO_REPO", "Erro na resposta da API: ${response.code()}")
                    onError(Exception("Erro ao buscar vinhos: ${response.code()}"))
                }
            }

            override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable) {
                Log.e("VINHO_REPO", "Falha na chamada da API", t)
                onError(t)
            }
        })
    }
}
