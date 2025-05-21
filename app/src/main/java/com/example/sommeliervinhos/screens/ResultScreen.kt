package com.example.sommeliervinhos.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sommeliervinhos.data.loadWinesFromAssets
import com.example.sommeliervinhos.model.Wine
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ResultScreen(
    navController: NavHostController,
    selectedFoods: List<String>,
    maxPrice: Float,
    selectedOccasion: String,
    selectedWineType: String
) {
    val context = LocalContext.current
    var wines by remember { mutableStateOf(emptyList<Wine>()) }

    LaunchedEffect(Unit) {
        wines = loadWinesFromAssets(context)
        Log.d("WINE_DEBUG", "Loaded wines: ${wines.joinToString { it.name }}")
    }

    val filteredWines = wines.filter { wine ->
        val normalizedSelectedFoods = selectedFoods.map {
            it.lowercase().trim().replace(Regex("\\s+"), " ")
        }
        val matchesFood = normalizedSelectedFoods.contains("none") || wine.pairing.any { pairing ->
            val normalizedPairing = pairing.lowercase().trim().replace(Regex("\\s+"), " ")
            normalizedSelectedFoods.any { selected ->
                val match = normalizedPairing.contains(selected) || selected.contains(normalizedPairing) ||
                        normalizedPairing.split(" ").any { word -> selected.contains(word) }
                Log.d("MATCH_CHECK", "Food Compare: [$normalizedPairing] vs [$selected] → $match")
                match
            }
        }

        val normalizedType = selectedWineType.lowercase().trim().replace(Regex("\\s+"), " ")
        val wineTypeNormalized = wine.type.lowercase().trim().replace(Regex("\\s+"), " ")
        val matchesType = normalizedType == "me_surpreenda" ||
                normalizedType in wineTypeNormalized ||
                wineTypeNormalized in normalizedType ||
                wineTypeNormalized.split(" ").any { word -> normalizedType.contains(word) }

        val matchesPrice = wine.price <= maxPrice

        Log.d(
            "FILTER_LOGIC", "${wine.name} → type: ${wine.type}, price: ${wine.price}, " +
                    "matchesType=$matchesType, matchesFood=$matchesFood, matchesPrice=$matchesPrice"
        )

        matchesFood && matchesType && matchesPrice
    }

    LazyColumn(modifier = Modifier.background(Color(0xFFFFFFFF)).fillMaxSize().padding(200.dp)) {
        items(filteredWines) { wine ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Column(modifier = Modifier.background(Color(0xFFDCDCDC)).padding(16.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(wine.image),
                        contentDescription = "Imagem do vinho",
                        contentScale = ContentScale.Fit, // ou .Inside
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(380.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Text(
                        wine.name,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        modifier = Modifier.fillMaxWidth().padding(12.dp)
                    )
                    Text(
                        wine.type,
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Harmoniza com: ${wine.pairing.joinToString()}",
                        fontSize = 27.sp,
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        textAlign = TextAlign.Center,
                    )
                    Column(modifier = Modifier.height(70.dp)) {
                        Text(
                            text = "Preço: R$ %.2f".format(wine.price),
                            textAlign = TextAlign.Center,
                            color = Color(0xFF41CD64),
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
    Button(
        onClick = { navController.navigate("welcome") },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text("Voltar ao início")
    }
}
