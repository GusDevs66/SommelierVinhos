package com.example.sommeliervinhos.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sommeliervinhos.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FoodSelectionScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val responsiveTitle = with(LocalDensity.current) { (screenWidth.value * 0.08f).sp }
    val responsiveText = with(LocalDensity.current) { (screenWidth.value * 0.06f).sp }
    val responsiveGridItemHeight = with(LocalDensity.current) { (screenHeight.value * 0.10f).dp }
    val responsiveButtonHeight = with(LocalDensity.current) { (screenHeight.value * 0.09f).dp }

    val foodOptions = listOf("Massa", "Carne", "Peixe", "Frango", "Risoto", "Queijos", "Petiscos", "Sobremesa")
    val selectedFoods = remember { mutableStateListOf<String>() }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.wine_grapes_3by2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Escolha a refeição que\n")
                    withStyle(style = SpanStyle(color = Color(0xFFE55428))) {
                        append("o vinho irá acompanhar.")
                    }
                },
                fontSize = responsiveTitle,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = (responsiveTitle.value * 1.3f).sp,
                fontFamily = PlayfairFont,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(foodOptions) { food ->
                        val isSelected = selectedFoods.contains(food)
                        val backgroundColor = if (isSelected) Color(0xFF129994) else Color(0xFF6E0F1A)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(responsiveGridItemHeight)
                                .clip(RoundedCornerShape(10.dp))
                                .background(backgroundColor)
                                .clickable {
                                    if (isSelected) selectedFoods.remove(food)
                                    else selectedFoods.add(food)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = food,
                                fontSize = responsiveText,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val cleanedFoods = selectedFoods.map { it.replace(Regex("[^\\p{L}\\p{Zs}]"), "").trim() }
                    val encodedFoods = URLEncoder.encode(
                        if (cleanedFoods.isEmpty()) "none" else cleanedFoods.joinToString(","),
                        StandardCharsets.UTF_8.toString()
                    )
                    navController.navigate("wineTypeSelection/$encodedFoods")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(responsiveButtonHeight),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6E0F1A))
            ) {
                Text("Avançar", color = Color.White, fontSize = responsiveText)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(responsiveButtonHeight),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6E0F1A))
            ) {
                Text("Voltar", color = Color.White, fontSize = responsiveText)
            }
        }
    }
}
