package com.example.sommeliervinhos.screens

import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.sommeliervinhos.R // ⚠️ certifique-se do caminho correto

@Composable
fun WelcomeGif() {
    val context = LocalContext.current
    var loadError by remember { mutableStateOf(false) }

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    val gifUri = "android.resource://${context.packageName}/raw/animacao_logo"

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(gifUri) // ✅ URI no formato esperado
                .crossfade(true)
                .listener(
                    onSuccess = { _, _ -> loadError = false },
                    onError = { _, result ->
                        loadError = true
                        Log.e("WelcomeGif", "Erro ao carregar GIF: ${result.throwable}")
                    }
                )
                .build(),
            imageLoader = imageLoader,
            contentDescription = "Animação inicial",
            modifier = Modifier
                .width(360.dp)
                .height(280.dp)
        )

        if (loadError) {
            Text("❌ Falha ao carregar GIF", modifier = Modifier.padding(8.dp))
        }
    }
}
