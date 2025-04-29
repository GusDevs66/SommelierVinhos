package com.example.sommeliervinhos.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign

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
