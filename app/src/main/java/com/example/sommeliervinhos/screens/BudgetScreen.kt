package com.example.sommeliervinhos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BudgetScreen(
    navController: NavHostController,
    occasion: String,
    selectedFoods: List<String>,
    selectedWineType: String
) {
    var budget by remember { mutableStateOf(100f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4B0082))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Quanto você gostaria de gastar? 💰",
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 64.dp)
        )

        Text(
            text = "R$ %.2f".format(budget),
            fontSize = 48.sp,
            color = Color.Yellow,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Slider(
            value = budget,
            onValueChange = { budget = it },
            valueRange = 20f..500f,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFE55428),
                activeTrackColor = Color(0xFF71E791)
            )
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                val encodedFoods = URLEncoder.encode(
                    selectedFoods.joinToString(","), StandardCharsets.UTF_8.toString()
                )
                val encodedOccasion = URLEncoder.encode(occasion, StandardCharsets.UTF_8.toString())
                val encodedType = URLEncoder.encode(selectedWineType, StandardCharsets.UTF_8.toString())

                navController.navigate("resultScreen/$encodedOccasion/$encodedFoods/$encodedType/$budget")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5103DE))
        ) {
            Text("Buscar Vinhos", fontSize = 48.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE55428))
        ) {
            Text("Voltar", fontSize = 48.sp, color = Color.White)
        }
    }
}
