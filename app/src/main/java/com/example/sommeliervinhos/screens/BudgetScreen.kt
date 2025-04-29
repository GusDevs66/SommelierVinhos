package com.example.sommeliervinhos.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

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
