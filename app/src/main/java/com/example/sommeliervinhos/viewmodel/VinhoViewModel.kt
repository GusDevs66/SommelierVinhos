package com.example.sommeliervinhos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sommeliervinhos.data.VinhoRepository
import com.example.sommeliervinhos.model.Wine

// VinhoViewModel.kt - Ajustado

class VinhoViewModel(
    private val repository: VinhoRepository
) : ViewModel() {

    private val _vinhosFiltrados = MutableLiveData<List<Wine>>()
    val vinhosFiltrados: LiveData<List<Wine>> = _vinhosFiltrados

    fun buscarVinhos(
        selectedFoods: List<String>,
        selectedWineType: String,
        onError: (Throwable) -> Unit
    ) {
        repository.fetchVinhos(
            selectedFoods = selectedFoods,
            selectedWineType = selectedWineType,
            onSuccess = { vinhos ->
                _vinhosFiltrados.postValue(vinhos)
            },
            onError = onError
        )
    }
}
