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
                    vinhos.forEach { vinho ->
                        Log.d("VINHO_TRACE", "Chamando fetchProdCod com SKU: ${vinho.sku}")
                        fetchProdCod(vinho)
                    }
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

    fun fetchProdCod(vinho: Wine) {
        val call = vinhoService.getProdutos(token, vinho.sku)
        call.enqueue(object : retrofit2.Callback<CfProdutoResponse> {
            override fun onResponse(
                call: retrofit2.Call<CfProdutoResponse>,
                response: retrofit2.Response<CfProdutoResponse>
            ) {
                if (response.isSuccessful) {
                    val prodCod = response.body()?.data?.firstOrNull()?.prodId?.toString()
                    Log.d("VINHO_CHECK", "ProdCod para SKU ${vinho.sku}: $prodCod")
                    if (prodCod != null) {
                        Log.d("VINHO_TRACE", "Chamando fetchPreco com prodCod: $prodCod")
                        fetchPreco(prodCod, vinho)
                    }
                } else {
                    Log.e("VINHO_CHECK", "Erro na resposta de prod_cod: ${response.code()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<CfProdutoResponse>, t: Throwable) {
                Log.e("VINHO_CHECK", "Falha ao buscar prod_cod", t)
            }
        })
    }

    fun fetchPreco(prodCod: String, vinho: Wine) {
        val body = mapOf("vlr_produto" to prodCod.toInt())
        val call = vinhoService.getPreco(body)
        call.enqueue(object : retrofit2.Callback<PriceResponse> {
            override fun onResponse(
                call: retrofit2.Call<PriceResponse>,
                response: retrofit2.Response<PriceResponse>
            ) {
                if (response.isSuccessful) {
                    val raw = response.body()
                    Log.d("VINHO_RAW_JSON", "Response completa: ${raw.toString()}")
                    val preco = raw?.data?.vlr_valores ?: ""
                    vinho.price = preco
                    Log.d("VINHO_CHECK", "Preço atribuído para ${vinho.name} (SKU ${vinho.sku}): R$ $preco")
                    vinho.logDetails()
                } else {
                    Log.e("VINHO_CHECK", "Erro ao buscar preco: ${response.code()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<PriceResponse>, t: Throwable) {
                Log.e("VINHO_CHECK", "Falha ao buscar preco", t)
            }
        })
    }
}
