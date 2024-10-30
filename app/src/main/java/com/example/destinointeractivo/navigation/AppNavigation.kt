package com.example.destinointeractivo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.destinointeractivo.MainScreen
import com.example.destinointeractivo.Ajustes
import com.example.destinointeractivo.Prueba
import com.example.destinointeractivo.NavViewModel // Asegúrate de importar el NavViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navViewModel: NavViewModel = viewModel() // Crea la instancia del ViewModel

    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(route = AppScreens.MainScreen.route) {
            MainScreen(navController, navViewModel) // Pasa el ViewModel aquí
        }
        composable(route = AppScreens.Ajustes.route) {
            Ajustes(navController, navViewModel) // Pasa el ViewModel aquí
        }
        composable(route = AppScreens.Prueba.route) {
            Prueba(navController, navViewModel) // Pasa el ViewModel aquí
        }
    }
}
