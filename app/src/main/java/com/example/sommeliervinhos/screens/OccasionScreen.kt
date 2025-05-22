package com.example.sommeliervinhos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun OccasionScreen(navController: NavHostController) {
    val occasions = listOf(
        "Para presentear🎁",
        "Para celebrar🎉",
        "Para um encontro💘",
        "Para uma refeição🍖",
        "Qualquer hora⏳"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5103DE))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = buildAnnotatedString {
                append("Para qual finalidade\n")
                withStyle(style = SpanStyle(color = Color.Yellow)) {
                    append("está buscando?")
                }
            },
            fontSize = 80.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(128.dp))

        occasions.forEach { occasion ->
            Button(
                onClick = {
                    val encodedOccasion = occasion.replace(" ", "_")
                    navController.navigate("foodSelection/$encodedOccasion")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF71E791),
                    contentColor = Color.White
                )
            ) {
                Text(
                    occasion,
                    fontSize = 60.sp
                )
            }
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
