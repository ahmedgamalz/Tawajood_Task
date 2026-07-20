package com.example.tawajoodtask.feature.news.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tawajoodtask.feature.news.presentation.details.DetailsScreen
import com.example.tawajoodtask.feature.news.presentation.home.HomeScreen
import com.example.tawajoodtask.feature.news.presentation.splash.SplashScreen

@Composable
fun NewsNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Details.route) {
            DetailsScreen(navController = navController)
        }
    }
}