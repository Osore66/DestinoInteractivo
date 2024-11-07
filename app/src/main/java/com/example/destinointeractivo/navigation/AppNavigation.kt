package com.example.destinointeractivo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.destinointeractivo.Screens.MainScreen
import com.example.destinointeractivo.Screens.Ajustes
import com.example.destinointeractivo.Screens.Prueba
import com.example.destinointeractivo.NavViewModel // Asegúrate de importar el NavViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navViewModel: NavViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(route = AppScreens.MainScreen.route) {
            MainScreen(navController, navViewModel)
        }
        composable(route = AppScreens.Ajustes.route) {
            Ajustes(navController, navViewModel)
        }
        composable(route = AppScreens.Prueba.route) {
            Prueba(navController, navViewModel)
        }
    }
}
