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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun WineTypeScreen(navController: NavHostController, occasion: String, foods: String) {
    val wineTypes = listOf("Tinto", "Branco", "Rosé", "Espumante", "me_surpreenda")
    var selectedType by remember { mutableStateOf("me_surpreenda") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4B0082))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                append("🤓 Escolha o tipo de vinho\n")
                withStyle(style = SpanStyle(color = Color.Yellow)) {
                    append("que deseja❗")
                }
            },
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Spacer(modifier = Modifier.height(128.dp))

        wineTypes.forEach { type ->
            val selected = selectedType == type
            val backgroundColor = if (selected) Color(0xFF56C372) else Color(0xFF71E791)

            Button(
                onClick = { selectedType = type },
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .height(200.dp)
            ) {
                Text(type, fontSize = 60.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val encodedType = URLEncoder.encode(selectedType, StandardCharsets.UTF_8.toString())
                val encodedFoods = URLEncoder.encode(foods, StandardCharsets.UTF_8.toString())
                val encodedOccasion = URLEncoder.encode(occasion, StandardCharsets.UTF_8.toString())
                navController.navigate("budgetSelection/$encodedOccasion/$encodedFoods/$encodedType")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5103DE))
        ) {
            Text("Avançar", color = Color.White, fontSize = 60.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB00020))
        ) {
            Text("Voltar", color = Color.White, fontSize = 60.sp)
        }
    }
}
