package com.example.destinointeractivo.navigation

sealed class AppScreens(val route: String) {
    object MainScreen: AppScreens("MainScreen")
    object Ajustes: AppScreens("Ajustes")
    object Prueba: AppScreens("Prueba")
    object Combate_1: AppScreens("Combate_1")
    object Combate_2: AppScreens("Combate_2")
    object SinMetodos_Combate: AppScreens("SinMetodos_Combate")
}