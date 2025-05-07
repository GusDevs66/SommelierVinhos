package com.example.sommeliervinhos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.ui.draw.clip


@Composable
fun FoodSelectionScreen(
    navController: NavHostController,
    occasion: String
) {
    val foodOptions = listOf("Massa🍝", "Carne Vermelha🍖", "Peixe🎣", "Frango🐓", "Risoto🍽️", "Queijos🧀", "Petiscos🥜", "Sobremesa🍰")
    val selectedFoods = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4B0082))
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                append("🍽️ Escolha a refeição que\n")
                withStyle(style = SpanStyle(color = Color.Yellow)) {
                    append("o vinho irá acompanhar.")
                }
            },
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(foodOptions) { food ->
                val isSelected = selectedFoods.contains(food)
                val backgroundColor = if (isSelected) Color(0xFF129994) else Color(0xFF71E791)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(backgroundColor)
                        .clickable {
                            if (isSelected) selectedFoods.remove(food)
                            else selectedFoods.add(food)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = food,
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
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
                navController.navigate("wineTypeSelection/$encodedOccasion/$encodedFoods")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5103DE))
        ) {
            Text("Avançar", color = Color.White, fontSize = 60.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE55428))
        ) {
            Text("Voltar", color = Color.White, fontSize = 60.sp)
        }
    }
}
