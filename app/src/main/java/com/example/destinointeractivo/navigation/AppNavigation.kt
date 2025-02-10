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
import com.example.destinointeractivo.Screens.Combate_001
import com.example.destinointeractivo.Screens.Combate_002
import com.example.destinointeractivo.Screens.SinMetodos_Combate

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
        composable(route = AppScreens.Combate_1.route) {
            Combate_001(navController, navViewModel)
        }
        composable(route = AppScreens.Combate_2.route) {
            Combate_002(navController, navViewModel)
        }
        composable(route = AppScreens.SinMetodos_Combate.route) {
            SinMetodos_Combate(navController, navViewModel)
        }
    }
}
