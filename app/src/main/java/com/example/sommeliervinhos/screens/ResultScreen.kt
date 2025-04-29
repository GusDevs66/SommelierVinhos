package com.example.sommeliervinhos.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sommeliervinhos.model.Wine


@Composable
fun ResultScreen(navController: NavHostController, selectedFoods: List<String>, maxPrice: Float) {
    val context = LocalContext.current
    val wines = remember { loadWinesFromAssets(context) }

    val filteredWines = wines.filter { wine ->
        wine.pairing.any { dish -> selectedFoods.contains(dish) } && wine.price <= maxPrice
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(filteredWines) { wine ->
            Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(wine.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(wine.type)
                    Text("Harmoniza com: ${wine.pairing.joinToString()}")
                    Text("Preço: R$ %.2f".format(wine.price))
                }
            }
        }
    }
}

