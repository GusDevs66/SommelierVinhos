package com.example.sommeliervinhos.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sommeliervinhos.R
import com.example.sommeliervinhos.viewmodel.VinhoViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ResultScreen(
    navController: NavHostController,
    foods: List<String>,
    budget: Float,
    wineType: String,
    viewModel: VinhoViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val vinhosFiltrados by viewModel.vinhosFiltrados.observeAsState(emptyList())

    val filtradosPorNome = vinhosFiltrados.filter { vinho ->
        vinho.vinhoNome.lowercase().contains(searchQuery.lowercase())
    }
    val (comPreco, semPreco) = filtradosPorNome.partition { vinho ->
        vinho.price != null && vinho.price != "Carregando..."
    }
    val ordenadosComPreco = comPreco.sortedByDescending { vinho ->
        vinho.price?.replace(",", ".")?.toFloatOrNull() ?: 0f
    }
    val vinhosExibidos = ordenadosComPreco + semPreco

    LaunchedEffect(Unit) {
        viewModel.buscarVinhos(foods, wineType) { error ->
            Log.e("ResultScreen", "Erro ao buscar vinhos", error)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.wine_grapes_3by2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por nome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.8f))
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

            if (vinhosExibidos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(vinhosExibidos) { vinho ->
                        val wineJson = URLEncoder.encode(Gson().toJson(vinho), StandardCharsets.UTF_8.toString())
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .background(Color.White.copy(alpha = 0.8f))
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate("wineDetail/$wineJson")
                                }
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(vinho.vinhoImg),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .padding(bottom = 16.dp),
                                    contentScale = ContentScale.Fit
                                )
                                Text(
                                    text = vinho.vinhoNome,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    ),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "Tipo: ${vinho.vinhoTipo}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp
                                    ),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "Preço: ${vinho.price ?: "Carregando..."}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Color(0xFF388E3C)
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text("Voltar para tela anterior")
            }
        }
    }
}
