package com.example.sommeliervinhos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "welcome") {
                composable("welcome") { WelcomeScreen(navController) }
                composable("foodSelection") { FoodSelectionScreen(navController) }
                composable("budgetSelection") { BudgetScreen(navController) }
                composable("wineSuggestions") { ResultScreen(navController) }
            }
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF3E7))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🍷 Sommelier Virtual", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Encontre o vinho ideal para sua refeição",
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { navController.navigate("foodSelection") }) {
            Text("Começar")
        }
    }
}

@Composable
fun FoodSelectionScreen(navController: NavHostController) {
    val options = listOf("Massa", "Carne Vermelha", "Peixe", "Frango")
    val selected = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Qual será a refeição?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selected.contains(option),
                    onCheckedChange = {
                        if (it) selected.add(option) else selected.remove(option)
                    }
                )
                Text(option)
            }
        }
        Button(onClick = { navController.navigate("budgetSelection") }) {
            Text("Avançar")
        }
    }
}

@Composable
fun BudgetScreen(navController: NavHostController) {
    var budget by remember { mutableStateOf(100.0f) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Quanto deseja gastar no vinho?", fontSize = 18.sp)
        Slider(
            value = budget,
            onValueChange = { budget = it },
            valueRange = 20f..500f
        )
        Text("R$ %.2f".format(budget))
        Button(onClick = { navController.navigate("wineSuggestions") }) {
            Text("Buscar Vinhos")
        }
    }
}

@Composable
fun ResultScreen(navController: NavHostController) {
    val mockWines = listOf(
        Wine("Cabernet Sauvignon", "Tinto", listOf("Carne Vermelha"), 89.90),
        Wine("Chardonnay", "Branco", listOf("Peixe"), 59.90),
        Wine("Malbec", "Tinto", listOf("Massa", "Carne Vermelha"), 75.00)
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(mockWines) { wine ->
            Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(wine.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(wine.type)
                    Text("Harmoniza com: ${wine.harmonization.joinToString()} ")
                    Text("Preço: R$ ${wine.price}")
                }
            }
        }
    }
}

data class Wine(
    val name: String,
    val type: String,
    val harmonization: List<String>,
    val price: Double
)
