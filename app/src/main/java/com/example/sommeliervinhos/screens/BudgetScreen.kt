package com.example.sommeliervinhos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sommeliervinhos.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BudgetScreen(
    navController: NavHostController,
    selectedFoods: List<String>,
    selectedWineType: String
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val responsiveTitle = with(LocalDensity.current) { (screenWidth.value * 0.08f).sp }
    val responsiveSubtitle = with(LocalDensity.current) { (screenWidth.value * 0.07f).sp }
    val responsiveButtonHeight = with(LocalDensity.current) { (screenHeight.value * 0.12f).dp }
    val responsiveSliderHeight = with(LocalDensity.current) { (screenHeight.value * 0.08f).dp }

    var budget by remember { mutableStateOf(100f) }

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
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Quanto você gostaria de gastar?",
                fontSize = responsiveTitle,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = (responsiveTitle.value * 1.3f).sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "R$ %.2f".format(budget),
                fontSize = responsiveSubtitle,
                color = Color(0xFFE55428),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Slider(
                value = budget,
                onValueChange = { budget = it },
                valueRange = 20f..500f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(responsiveSliderHeight),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFE55428),
                    activeTrackColor = Color(0xFF71E791)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val encodedFoods = URLEncoder.encode(
                        selectedFoods.joinToString(","), StandardCharsets.UTF_8.toString()
                    )
                    val encodedType = URLEncoder.encode(selectedWineType, StandardCharsets.UTF_8.toString())

                    navController.navigate("result/$encodedFoods/$encodedType/$budget")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(responsiveButtonHeight),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6E0F1A))
            ) {
                Text("Buscar Vinhos", fontSize = responsiveSubtitle, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(responsiveButtonHeight),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6E0F1A))
            ) {
                Text("Voltar", fontSize = responsiveSubtitle, color = Color.White)
            }
        }
    }
}
