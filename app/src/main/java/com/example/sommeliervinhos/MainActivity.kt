package com.example.sommeliervinhos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sommeliervinhos.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "welcome") {
                composable("welcome") {
                    WelcomeScreen(navController)
                }

                composable("occasion") {
                    OccasionScreen(navController)
                }

                composable("foodSelection/{occasion}",
                    arguments = listOf(navArgument("occasion") { type = NavType.StringType })
                ) { backStackEntry ->
                    val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                    FoodSelectionScreen(navController, occasion)
                }

                composable(
                    "wineType/{occasion}/{foods}",
                    arguments = listOf(
                        navArgument("occasion") { type = NavType.StringType },
                        navArgument("foods") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                    val foodsParam = backStackEntry.arguments?.getString("foods") ?: ""
                    val foods = if (foodsParam.isEmpty()) emptyList() else foodsParam.split(",")
                    WineTypeScreen(navController, occasion, foods)
                }
                composable(
                    "budgetSelection/{occasion}/{foods}/{wineType}",
                    arguments = listOf(
                        navArgument("occasion") { type = NavType.StringType },
                        navArgument("foods") { type = NavType.StringType },
                        navArgument("wineType") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                    val foodsParam = backStackEntry.arguments?.getString("foods") ?: ""
                    val foods = if (foodsParam.isEmpty()) emptyList() else foodsParam.split(",")
                    val wineType = backStackEntry.arguments?.getString("wineType") ?: ""
                    BudgetScreen(navController, occasion, foods, wineType)
                }

                composable(
                    "result/{occasion}/{foods}/{wineType}/{maxPrice}",
                    arguments = listOf(
                        navArgument("occasion") { type = NavType.StringType },
                        navArgument("foods") { type = NavType.StringType },
                        navArgument("wineType") { type = NavType.StringType },
                        navArgument("maxPrice") { type = NavType.FloatType }
                    )
                ) { backStackEntry ->
                    val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                    val foodsParam = backStackEntry.arguments?.getString("foods") ?: ""
                    val foods = if (foodsParam.isEmpty()) emptyList() else foodsParam.split(",")
                    val wineType = backStackEntry.arguments?.getString("wineType") ?: ""
                    val maxPrice = backStackEntry.arguments?.getFloat("maxPrice") ?: 500f
                    ResultScreen(navController, foods, maxPrice, occasion, wineType)
                }
            }
        }
    }
}
