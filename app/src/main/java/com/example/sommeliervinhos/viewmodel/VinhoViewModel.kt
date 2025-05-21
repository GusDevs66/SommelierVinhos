package com.example.sommeliervinhos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sommeliervinhos.data.VinhoRepository
import com.example.sommeliervinhos.model.Wine

class VinhoViewModel : ViewModel() {

    private val repository = VinhoRepository()

    private val _vinhos = MutableLiveData<List<Wine>>()
    val vinhos: LiveData<List<Wine>> = _vinhos

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun carregarVinhos() {
        _loading.value = true

        repository.fetchVinhos(
            onSuccess = { lista ->
                val vinhosComPreco = mutableListOf<Wine>()

                lista.forEach { vinho ->
                    try {
                        val prodCodInt = vinho.prod_cod.toInt()

                        repository.fetchPreco(
                            prodCod = prodCodInt,
                            onSuccess = { preco ->
                                vinho.price = preco
                                vinhosComPreco.add(vinho)

                                if (vinhosComPreco.size == lista.size) {
                                    _vinhos.postValue(vinhosComPreco)
                                    _loading.postValue(false)
                                }
                            },
                            onError = {
                                vinho.price = ""
                                vinhosComPreco.add(vinho)

                                if (vinhosComPreco.size == lista.size) {
                                    _vinhos.postValue(vinhosComPreco)
                                    _loading.postValue(false)
                                }
                            }
                        )
                    } catch (e: Exception) {
                        vinho.price = ""
                        vinhosComPreco.add(vinho)

                        if (vinhosComPreco.size == lista.size) {
                            _vinhos.postValue(vinhosComPreco)
                            _loading.postValue(false)
                        }
                    }
                }
            },
            onError = {
                _error.postValue(it.message)
                _loading.postValue(false)
            }
        )
    }
}
