package com.example.sommeliervinhos.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.sommeliervinhos.model.Wine
import com.example.sommeliervinhos.viewmodel.VinhoViewModel
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ResultScreen(
    navController: NavHostController,
    selectedFoods: List<String>,
    maxPrice: Float,
    selectedOccasion: String,
    selectedWineType: String
) {
    val viewModel: VinhoViewModel = viewModel()
    val wines by viewModel.vinhos.observeAsState(emptyList())
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.carregarVinhos()
    }

    Log.d("RESULT_SCREEN", "Total vinhos carregados: ${wines.size}")

    val filteredWines = wines
        .filter { wine ->
            val harmoniza = selectedFoods.any { food ->
                val keyword = food.split("+").lastOrNull() ?: food
                wine.pairing?.contains(keyword, ignoreCase = true) == true
            }

            val tipoValido = selectedWineType == "me_surpreenda" ||
                    wine.type?.contains(selectedWineType, ignoreCase = true) == true

            harmoniza && tipoValido
        }
        .filter { wine ->
            wine.containsKeyword(searchText)
        }
        .sorted()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar vinho...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Resultados da Busca",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredWines) { wine ->
                WineItem(wine = wine)
            }
        }
    }
}

@Composable
fun WineItem(wine: Wine) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = wine.image,
            contentDescription = "Imagem do vinho",
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = wine.name, style = MaterialTheme.typography.bodyLarge)
        Text(text = "Tipo: ${wine.type}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "País: ${wine.country}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Preço: R$ ${wine.price}", style = MaterialTheme.typography.bodyMedium)
    }
}
