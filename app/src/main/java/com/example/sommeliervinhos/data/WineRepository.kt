package com.example.sommeliervinhos.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.sommeliervinhos.model.Wine

fun loadWinesFromAssets(context: Context): List<Wine> {
    val jsonString = context.assets.open("vinhos_recomendados.json")
        .bufferedReader()
        .use { it.readText() }

    val type = object : TypeToken<List<Wine>>() {}.type
    return Gson().fromJson(jsonString, type)
}
