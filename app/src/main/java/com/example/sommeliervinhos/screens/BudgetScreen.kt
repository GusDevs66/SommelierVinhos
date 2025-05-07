package com.example.sommeliervinhos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun BudgetScreen(
    navController: NavHostController,
    occasion: String,
    selectedFoods: String,
    wineType: String
) {
    var budget by remember { mutableStateOf(100f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4B0082))
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                append("💸 Quanto deseja gastar\n")
                withStyle(style = SpanStyle(color = Color.Yellow)) {
                    append("no vinho?")
                }
            },
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Slider(
            modifier = Modifier.height(200.dp),
            value = budget,
            onValueChange = { budget = it },
            valueRange = 20f..500f,
            steps = 48,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF71E791),
                activeTrackColor = Color(0xFF56C372),
                inactiveTrackColor = Color.LightGray
            )
        )

        Text(
            "R$ %.2f".format(budget),
            color = Color.Green,
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = {
                navController.navigate("wineSuggestions/$selectedFoods/$budget/$occasion/$wineType/$")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            Text("Buscar Vinhos 🍷", fontSize = 60.sp)
        }

        Spacer(modifier = Modifier.height(256.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE55428))
        ) {
            Text("Voltar", color = Color.White, fontSize = 60.sp)
        }
    }
}
