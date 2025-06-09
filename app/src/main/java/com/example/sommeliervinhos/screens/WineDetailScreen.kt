package com.example.sommeliervinhos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sommeliervinhos.R
import com.example.sommeliervinhos.model.Wine

@Composable
fun WineDetailScreen(navController: NavHostController, wine: Wine) {
    val countryMapRes = when (wine.vinhoPais?.lowercase()) {
        "chile" -> R.drawable.map_chile
        "italia" -> R.drawable.map_italy
        "argentina" -> R.drawable.map_argentina
        "portugal" -> R.drawable.map_portugal
        "eua" -> R.drawable.map_usa
        "frança" -> R.drawable.map_france
        else -> R.drawable.map_world
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.wine_grapes_3by2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = wine.vinhoNome.replace("+", " "),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Wine Image responsiva
                        Image(
                            painter = rememberAsyncImagePainter(wine.vinhoImg),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 16.dp),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Details
                        Text(
                            text = "Tipo: ${wine.vinhoTipo.replace("+", " ")}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "País: ${wine.vinhoPais?.replace("+", " ") ?: "Desconhecido"}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Região: ${wine.vinhoRegiao?.replace("+", " ") ?: "Não informada"}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Teor alcoólico: ${wine.vinhoTeor.replace("+", " ")}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )

                        // Price
                        Spacer(modifier = Modifier.height(32.dp))

                        // Harmonização
                        Text(
                            text = wine.harmonizacao.replace("+", " "),
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                                .padding(16.dp)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "Preço: ${wine.price?.let { "R$ $it" } ?: "Não informado"}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Map responsivo
                        Image(
                            painter = rememberAsyncImagePainter(countryMapRes),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 400.dp)
                                .padding(bottom = 16.dp),
                            contentScale = ContentScale.Fit
                        )
                        Button(
                            onClick = { navController.navigate("welcome") {
                                popUpTo(0) { inclusive = true }
                            } },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 16.dp)
                        ) {
                            Text("Voltar para o início")
                        }
                        Button(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        ) {
                            Text("Voltar para tela anterior")
                        }
                    }
                }
            }
        }
    }
}
