package com.example.sommeliervinhos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sommeliervinhos.data.VinhoRepository

class VinhoViewModelFactory(
    private val repository: VinhoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VinhoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VinhoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
