package com.example.sommeliervinhos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sommeliervinhos.R

val PlayfairFont = FontFamily(
    Font(R.font.playfairdisplayregular, weight = FontWeight.Normal),
    Font(R.font.playfairdisplaybold, weight = FontWeight.Bold),
    Font(R.font.playfairdisplayitalic, weight = FontWeight.Normal),
    Font(R.font.playfairdisplaybolditalic, weight = FontWeight.Bold)
)

@Composable
fun WelcomeScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val responsiveFontSize = with(LocalDensity.current) { (screenWidth.value * 0.07f).sp }
    val responsiveLineHeight = with(LocalDensity.current) { (screenWidth.value * 0.09f).sp }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.wine_grapes_3by2),
            contentDescription = "background wine image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = screenWidth * 0.05f, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val titleFontSize = with(LocalDensity.current) { (screenWidth * 0.07f).toSp() }
            val subtitleFontSize = with(LocalDensity.current) { (screenWidth * 0.045f).toSp() }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Sommelier de\n")
                        withStyle(style = SpanStyle(color = Color(0xFFFFF1E6))) {
                            append("vinho")
                        }
                    },
                    fontSize = titleFontSize,
                    color = Color(0xFFFFF1E6),
                    fontWeight = FontWeight.Bold,
                    fontFamily = PlayfairFont,
                    textAlign = TextAlign.Center,
                    lineHeight = responsiveLineHeight,
                    modifier = Modifier.widthIn(max = screenWidth * 0.5f)
                )
            }

            Button(
                onClick = { navController.navigate("occasion") },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .widthIn(min = 220.dp)
                    .heightIn(min = 64.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF71E791),
                    contentColor = Color.White
                )
            ) {
                Text("COMEÇAR", fontSize = 24.sp, color = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Escolhas guiadas para\n")
                    withStyle(style = SpanStyle(color = Color(0xFFE55428))) {
                        append("paladares exigentes.")
                    }
                },
                fontSize = responsiveFontSize,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = responsiveLineHeight,
                fontFamily = PlayfairFont,
                modifier = Modifier.widthIn(max = screenWidth * 0.5f)
            )
        }
    }
}
