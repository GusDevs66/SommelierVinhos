package com.example.sommeliervinhos.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sommeliervinhos.data.loadWinesFromAssets
import com.example.sommeliervinhos.model.Wine

@Composable
fun ResultScreen(
    navController: NavHostController,
    selectedFoods: List<String>,
    maxPrice: Float,
    selectedOccasion: String,
    selectedWineType: String
) {
    val context = LocalContext.current
    var wines by remember { mutableStateOf(emptyList<Wine>()) }

    LaunchedEffect(Unit) {
        wines = loadWinesFromAssets(context)
        Log.d("WINE_DEBUG", "Carregado ${wines.size} vinhos do JSON")
    }

    val filteredWines = wines.filter { wine ->
        val matchesFood = selectedFoods.any { selected ->
            wine.pairing.any { it.contains(selected, ignoreCase = true) }
        }

        val matchesType = selectedWineType == "me_surpreenda" ||
                wine.type.equals(selectedWineType, ignoreCase = true)

        val matchesPrice = wine.price <= maxPrice

        matchesFood && matchesType && matchesPrice
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (filteredWines.isEmpty()) {
            item {
                Text(
                    text = "Nenhum vinho encontrado com esses critérios.",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            items(filteredWines) { wine ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(wine.image),
                            contentDescription = "Imagem do vinho",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )
                        Text(wine.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text(wine.type)
                        Text("Harmoniza com: ${wine.pairing.joinToString()}")
                        Text("Preço: R$ %.2f".format(wine.price))
                    }
                }
            }
        }
    }
}
