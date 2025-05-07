package com.example.sommeliervinhos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle


@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5103DE))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(6.dp))

        WelcomeGif()

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = buildAnnotatedString {
                append("🍷 Sommelier Digital\n")
                withStyle(style = SpanStyle(color = Color(0xFFE55428))) {
                    append("Cartaz Fácil")
                }
            },
            fontSize = 80.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Encontre o vinho ideal para sua refeição",
            fontSize = 60.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Button(
            onClick = { navController.navigate("occasion") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 40.dp)
                .height(80.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF71E791),
                contentColor = Color.White
            )
        ) {
            Text("COMEÇAR", fontSize = 33.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}
