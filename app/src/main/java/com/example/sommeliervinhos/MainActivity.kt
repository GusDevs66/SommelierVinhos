package com.example.sommeliervinhos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sommeliervinhos.screens.*
import com.example.sommeliervinhos.data.VinhoRepository
import com.example.sommeliervinhos.viewmodel.VinhoViewModelFactory
import com.example.sommeliervinhos.viewmodel.VinhoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sommeliervinhos.model.Wine
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val repository = VinhoRepository()
            val factory = VinhoViewModelFactory(repository)
            val viewModel: VinhoViewModel = viewModel(factory = factory)

            Surface {
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { WelcomeScreen(navController) }
                    composable("occasion") {
                        OccasionScreen(navController)
                    }
                    composable("foodSelection/{occasion}", arguments = listOf(
                        navArgument("occasion") { type = NavType.StringType }
                    )) { backStackEntry ->
                        val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                        FoodSelectionScreen(navController)
                    }
                    composable("wineTypeSelection/{foods}", arguments = listOf(
                        navArgument("foods") { type = NavType.StringType }
                    )) { backStackEntry ->
                        val foods = backStackEntry.arguments?.getString("foods")?.split(",") ?: emptyList()
                        WineTypeScreen(navController, foods)
                    }
                    composable("budget/{foods}/{wineType}", arguments = listOf(
                        navArgument("foods") { type = NavType.StringType },
                        navArgument("wineType") { type = NavType.StringType }
                    )) { backStackEntry ->
                        val foods = backStackEntry.arguments?.getString("foods")?.split(",") ?: emptyList()
                        val wineType = backStackEntry.arguments?.getString("wineType") ?: ""
                        BudgetScreen(
                            navController = navController,
                            selectedFoods = foods,
                            selectedWineType = wineType
                        )
                    }
                    composable("result/{foods}/{wineType}/{budget}", arguments = listOf(
                        navArgument("foods") { type = NavType.StringType },
                        navArgument("wineType") { type = NavType.StringType },
                        navArgument("budget") { type = NavType.FloatType }
                    )) { backStackEntry ->
                        val foods = backStackEntry.arguments?.getString("foods")?.split(",") ?: emptyList()
                        val wineType = backStackEntry.arguments?.getString("wineType") ?: ""
                        val budget = backStackEntry.arguments?.getFloat("budget") ?: 0f

                        val repository = VinhoRepository()
                        val factory = VinhoViewModelFactory(repository)
                        val viewModel: VinhoViewModel = viewModel(factory = factory)

                        ResultScreen(navController, foods, budget, wineType, viewModel)
                    }
                    composable(
                        route = "wineDetail/{wineJson}",
                        arguments = listOf(navArgument("wineJson") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val json = backStackEntry.arguments?.getString("wineJson") ?: ""
                        val wine = Gson().fromJson(json, Wine::class.java)
                        WineDetailScreen(navController, wine)
                    }
                }
            }
        }
    }
}
