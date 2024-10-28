package com.example.destinointeractivo.navigation

sealed class AppScreens(val route: String) {
    object MainScreen: AppScreens("MainScreen")
    object Ajustes: AppScreens("Ajustes")
}