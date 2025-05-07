package com.example.sommeliervinhos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.sommeliervinhos.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") {
                        WelcomeScreen(navController)
                    }
                    composable("occasionSelection") {
                        OccasionScreen(navController)
                    }
                    composable(
                        route = "foodSelection/{occasion}",
                        arguments = listOf(navArgument("occasion") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                        FoodSelectionScreen(navController, occasion)
                    }
                    composable(
                        route = "wineTypeSelection/{occasion}/{foods}",
                        arguments = listOf(
                            navArgument("occasion") { type = NavType.StringType },
                            navArgument("foods") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                        val foods = backStackEntry.arguments?.getString("foods") ?: ""
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
                        val foods = backStackEntry.arguments?.getString("foods") ?: ""
                        val wineType = backStackEntry.arguments?.getString("wineType") ?: ""
                        BudgetScreen(navController, occasion, foods, wineType)
                    }
                    composable(
                        route = "wineSuggestions/{occasion}/{foods}/{wineType}/{price}",
                        arguments = listOf(
                            navArgument("occasion") { type = NavType.StringType },
                            navArgument("foods") { type = NavType.StringType },
                            navArgument("wineType") { type = NavType.StringType },
                            navArgument("price") { type = NavType.FloatType }
                        )
                    ) { backStackEntry ->
                        val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                        val foods = backStackEntry.arguments?.getString("foods") ?: ""
                        val wineType = backStackEntry.arguments?.getString("wineType") ?: ""
                        val price = backStackEntry.arguments?.getFloat("price") ?: 500f

                        val foodList = if (foods.isBlank()) emptyList() else foods.split(",")
                        ResultScreen(
                            navController = navController,
                            selectedFoods = foodList,
                            maxPrice = price,
                            selectedOccasion = occasion,
                            selectedWineType = wineType
                        )
                    }
                }
            }
        }
    }
}
