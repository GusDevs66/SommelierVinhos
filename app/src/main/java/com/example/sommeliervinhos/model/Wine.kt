package com.example.sommeliervinhos.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Define estrutura base da resposta da API
class ApiResponse(
    @SerializedName("data") val data: List<Wine>
)

// Classe Wine para representar os vinhos
data class Wine(
    @SerializedName("vinho_id") val vinhoId: String,
    @SerializedName("vinho_nome") val vinhoNome: String,
    @SerializedName("vinho_pais") val vinhoPais: String?,
    @SerializedName("vinho_regiao") val vinhoRegiao: String,
    @SerializedName("vinho_teor") val vinhoTeor: String,
    @SerializedName("vinho_tipo") val vinhoTipo: String,
    @SerializedName("vinho_sku") val vinhoSku: String,
    @SerializedName("vinho_imagem") val vinhoImg: String,
    @SerializedName("vinho_harmonizacao") val harmonizacao: String,
) : Serializable {
    @Transient
    var price: String? = null
}
