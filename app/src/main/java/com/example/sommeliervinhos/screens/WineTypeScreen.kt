package com.example.sommeliervinhos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun WineTypeScreen(navController: NavHostController, occasion: String, selectedFoods: List<String>)
{
    val wineTypes = listOf("Tinto", "Branco Seco", "Espumante", "Rosé", "me_surpreenda")
    var selectedType by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4B0082))
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Escolha o tipo de vinho",
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            wineTypes.forEach { type ->
                val isSelected = selectedType == type
                val backgroundColor = if (isSelected) Color(0xFF129994) else Color(0xFF71E791)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(backgroundColor)
                        .clickable { selectedType = type },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (type == "me_surpreenda") "Me Surpreenda" else type,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val encodedFoods = URLEncoder.encode(
                    selectedFoods.joinToString(","), StandardCharsets.UTF_8.toString()
                )
                val encodedOccasion = URLEncoder.encode(occasion, StandardCharsets.UTF_8.toString())
                val encodedType = URLEncoder.encode(selectedType, StandardCharsets.UTF_8.toString())

                navController.navigate("budget/$encodedOccasion/$encodedFoods/$encodedType")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5103DE))
        ) {
            Text("Avançar", fontSize = 48.sp, color = Color.White)
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