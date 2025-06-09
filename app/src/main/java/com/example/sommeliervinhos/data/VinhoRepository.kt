package com.example.sommeliervinhos.data

import android.util.Log
import com.example.sommeliervinhos.model.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class VinhoRepository {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://apicartazfacil.com/public/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(VinhoService::class.java)
    private val token = "396188e26f79af8b67be0dd5d0a6776f"

    private val semaphore = Semaphore(10)

    fun fetchVinhos(
        selectedFoods: List<String>,
        selectedWineType: String,
        onSuccess: (List<Wine>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.getVinhos(token).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (!response.isSuccessful) {
                    onError(Exception("Erro ao buscar vinhos: ${response.code()}"))
                    return
                }

                val todos = response.body()?.data.orEmpty()

                CoroutineScope(Dispatchers.Main).launch {
                    val comidasSelecionadas = selectedFoods.map { it.lowercase() }
                    val tiposSelecionados = selectedWineType.split(",").map { it.trim().lowercase() }

                    val filtrados = todos.filter { vinho ->
                        val tipoMatch = tiposSelecionados.any { vinho.vinhoTipo.lowercase().contains(it) }
                        val harmonizacao = vinho.harmonizacao.lowercase()
                        val comidaMatch = comidasSelecionadas.any { harmonizacao.contains(it) }

                        Log.d("VINHO_REPO", "Filtro -> ${vinho.vinhoNome}: tipoMatch=$tipoMatch, comidaMatch=$comidaMatch")

                        tipoMatch && comidaMatch
                    }

                    filtrados.forEach { vinho ->
                        launch(Dispatchers.IO) {
                            fetchPrecoParaVinho(vinho) {
                                Log.d("VINHO_REPO", "Preço atualizado para ${it.vinhoSku}: ${it.price}")
                            }
                        }
                    }

                    onSuccess(filtrados)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onError(t)
            }
        })
    }

    suspend fun fetchPrecoParaVinho(wine: Wine, onVinhoUpdated: (Wine) -> Unit) {
        if (wine.vinhoSku.isBlank()) return
        try {
            Log.d("VINHO_REPO", "Buscando produto para SKU=${wine.vinhoSku}")
            val prodResp = withTimeout(5000L) {
                service.getProdutos(token, wine.vinhoSku)
            }
            Log.d("VINHO_REPO", "Resposta cf_produto para SKU=${wine.vinhoSku}: ${prodResp.data}")

            val prodId = prodResp.data.firstOrNull()?.prodId ?: run {
                Log.d("VINHO_REPO", "Produto não encontrado para SKU=${wine.vinhoSku}")
                return
            }

            Log.d("VINHO_REPO", "Buscando preço para prodId=$prodId")

            // ### Novo bloco para logar o corpo bruto antes da desserialização ###
            val rawResponse = service.getPreco(token, prodId) // esse objeto é convertido pelo Gson, mas vamos interceptar o body bruto
            // Obs: não dá para ler "bruto" aqui sem alterar o retrofit; mas vamos registrar imediatamente a lista desserializada e seu tamanho.

            val priceResp = withTimeout(5000L) {
                service.getPreco(token, prodId)
            }
            // Primeiro, registre o tamanho da lista que chegou:
            Log.d("VINHO_REPO", "cf_valor.data.size para prodId=$prodId: ${priceResp.data.size}")

            // Se a lista não estiver vazia, registre cada item:
            if (priceResp.data.isNotEmpty()) {
                priceResp.data.forEachIndexed { idx, priceItem ->
                    Log.d("VINHO_REPO", "cf_valor.data[$idx].vlrValores: ${priceItem.vlrValores}")
                }
            } else {
                Log.d("VINHO_REPO", "Aviso: priceResp.data veio vazio para prodId=$prodId")
            }
            // ### Fim do novo bloco ###

            val raw = priceResp.data.firstOrNull()?.vlrValores.orEmpty()
            Log.d("VINHO_REPO", "Raw vlrValores para prodId=$prodId: '$raw'")

            val clean = raw.replace(Regex("[^0-9,\\.]") , "").ifBlank { "Indisponível" }
            wine.price = clean
            onVinhoUpdated(wine)
        } catch (e: TimeoutCancellationException) {
            Log.e("VINHO_REPO", "Timeout ao buscar preço SKU=${wine.vinhoSku}", e)
        } catch (e: Exception) {
            Log.e("VINHO_REPO", "Erro ao buscar preço SKU=${wine.vinhoSku}", e)
        }
    }
}
