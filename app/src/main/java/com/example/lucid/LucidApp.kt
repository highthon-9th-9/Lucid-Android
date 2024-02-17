package com.example.lucid

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lucid.feature.home.HomeScreen
import com.example.lucid.feature.login.LoginScreen
import com.example.lucid.feature.login.LoginViewModel

@Composable
fun LucidApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
    ) {
        composable("login") {
            LoginScreen (LoginViewModel())
        }
        composable("home") {
            HomeScreen()
        }
    }
}