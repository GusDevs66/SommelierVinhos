package com.example.sommeliervinhos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun OccasionScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val responsiveTitle = with(LocalDensity.current) { (screenWidth.value * 0.08f).sp }
    val responsiveText = with(LocalDensity.current) { (screenWidth.value * 0.06f).sp }
    val responsiveButtonHeight = with(LocalDensity.current) { (screenHeight.value * 0.09f).dp }
    val responsivePadding = screenWidth * 0.04f

    val occasions = listOf(
        "Para presentear",
        "Para celebrar",
        "Para um encontro",
        "Para uma refeição",
        "Qualquer hora"
    )

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
                    append("Para qual finalidade\n")
                    withStyle(style = SpanStyle(color = Color(0xFFE55428))) {
                        append("está buscando?")
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.wrapContentHeight()
                ) {
                    occasions.forEach { occasion ->
                        Button(
                            onClick = {
                                val encodedOccasion = occasion.replace(" ", "_")
                                navController.navigate("foodSelection/$encodedOccasion")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(responsiveButtonHeight),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6E0F1A)),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Text(occasion, color = Color.White, fontSize = responsiveText)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(responsiveButtonHeight),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6E0F1A)),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Voltar", color = Color.White, fontSize = responsiveText)
            }
        }
    }
}
