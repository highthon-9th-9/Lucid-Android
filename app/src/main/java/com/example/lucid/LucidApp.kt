package com.example.lucid

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgs
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lucid.feature.home.HomeScreen
import com.example.lucid.feature.login.LoginScreen
import com.example.lucid.feature.result.ResultScreen

@Composable
fun LucidApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
    ) {
        composable("login") {
            LoginScreen (navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable(
            "result/{data}/{image}",
            listOf(
                navArgument("data") {
                    type = NavType.StringType
                },
                navArgument("image") {
                    type = NavType.StringType
                }
            )
        ) {
            ResultScreen(
                it.arguments?.getString("data") ?: "",
                it.arguments?.getString("image") ?: "",
                navController
            )
        }
    }
}