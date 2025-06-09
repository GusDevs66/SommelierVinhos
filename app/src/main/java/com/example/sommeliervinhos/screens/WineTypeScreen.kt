package com.example.sommeliervinhos.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun WineTypeScreen(
    navController: NavHostController,
    selectedFoods: List<String>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val responsiveTitle = with(LocalDensity.current) { (screenWidth.value * 0.07f).sp }
    val responsiveText = with(LocalDensity.current) { (screenWidth.value * 0.045f).sp }
    val responsiveBoxHeight = with(LocalDensity.current) { (screenHeight.value * 0.12f).dp }
    val responsiveButtonHeight = with(LocalDensity.current) { (screenHeight.value * 0.1f).dp }
    val responsiveLineHeight = with(LocalDensity.current) { (screenWidth.value * 0.09f).sp }

    val visible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible.value = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.wine_grapes_3by2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        AnimatedVisibility(
            visible = visible.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Escolha o tipo de\n")
                        withStyle(style = SpanStyle(color = Color(0xFFE55428))) {
                            append("vinho:")
                        }
                    },
                    fontSize = responsiveTitle,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF),
                    fontFamily = PlayfairFont,
                    modifier = Modifier.padding(bottom = 32.dp),
                    lineHeight = responsiveLineHeight,
                    textAlign = TextAlign.Center
                    )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("Tinto", "Branco", "Rosé", "Espumante", "me_surpreenda").forEach { type ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(responsiveBoxHeight)
                                .background(Color(0xFF6E0F1A), shape = MaterialTheme.shapes.medium)
                                .clickable {
                                    navController.navigate("budget/${selectedFoods.joinToString(",")}/$type")
                                },
                            contentAlignment = Alignment.Center,
                            ) {
                            Text(
                                text = if (type == "me_surpreenda") "Me Surpreenda" else type,
                                color = Color.White,
                                fontSize = responsiveText,
                                fontWeight = FontWeight.Bold
                                )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(responsiveButtonHeight),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6E0F1A))
                ) {
                    Text("Voltar", fontSize = responsiveText, color = Color.White)
                }
            }
        }
    }
}
