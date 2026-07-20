package com.example.tawajoodtask.feature.news.presentation.navigation
import android.net.Uri

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object Home : Screen("home")

    data object Details : Screen("details/{url}") {

        fun createRoute(url: String): String {
            return "details/${Uri.encode(url)}"
        }
    }
}