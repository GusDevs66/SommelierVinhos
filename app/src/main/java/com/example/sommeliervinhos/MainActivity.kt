package com.example.sommeliervinhos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
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
            Surface {
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { WelcomeScreen(navController) }
                    composable("welcomeGif") { WelcomeGif() }
                    composable("occasion") {
                        OccasionScreen(navController)
                    }
                    composable("foodSelection/{occasion}", arguments = listOf(
                        navArgument("occasion") { type = NavType.StringType }
                    )) { backStackEntry ->
                        val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                        FoodSelectionScreen(navController, occasion)
                    }
                    composable("wineTypeSelection/{occasion}/{foods}", arguments = listOf(
                        navArgument("occasion") { type = NavType.StringType },
                        navArgument("foods") { type = NavType.StringType }
                    )) { backStackEntry ->
                        val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                        val foods = backStackEntry.arguments?.getString("foods")?.split(",") ?: emptyList()
                        WineTypeScreen(navController, occasion, foods)
                    }
                    composable("budget/{occasion}/{foods}/{wineType}", arguments = listOf(
                        navArgument("occasion") { type = NavType.StringType },
                        navArgument("foods") { type = NavType.StringType },
                        navArgument("wineType") { type = NavType.StringType }
                    )) { backStackEntry ->
                        val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                        val foods = backStackEntry.arguments?.getString("foods")?.split(",") ?: emptyList()
                        val wineType = backStackEntry.arguments?.getString("wineType") ?: ""
                        BudgetScreen(
                            navController = navController,
                            occasion = occasion,
                            selectedFoods = foods,
                            selectedWineType = wineType
                        )
                    }
                    composable("result/{occasion}/{foods}/{wineType}/{budget}", arguments = listOf(
                        navArgument("occasion") { type = NavType.StringType },
                        navArgument("foods") { type = NavType.StringType },
                        navArgument("wineType") { type = NavType.StringType },
                        navArgument("budget") { type = NavType.FloatType }
                    )) { backStackEntry ->
                        val occasion = backStackEntry.arguments?.getString("occasion") ?: ""
                        val foods = backStackEntry.arguments?.getString("foods")?.split(",") ?: emptyList()
                        val wineType = backStackEntry.arguments?.getString("wineType") ?: ""
                        val budget = backStackEntry.arguments?.getFloat("budget") ?: 0f
                        ResultScreen(navController, foods, budget, occasion, wineType)
                    }
                }
            }
        }
    }
}