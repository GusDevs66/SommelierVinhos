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
fun ResultScreen(navController: NavHostController) {
    val mockWines: List<Wine> = listOf(
        Wine("Cabernet Sauvignon", "Tinto", listOf("Carne Vermelha"), 89.90),
        Wine("Chardonnay", "Branco", listOf("Peixe"), 59.90),
        Wine("Malbec", "Tinto", listOf("Massa", "Carne Vermelha"), 75.00)
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(mockWines) { wine ->
            Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(wine.name, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, fontSize = 20.sp)
                    Text(wine.type)
                    Text("Harmoniza com: ${wine.harmonization.joinToString()} ")
                    Text("Preço: R$ ${wine.price}")
                }
            }
        }
    }
}
