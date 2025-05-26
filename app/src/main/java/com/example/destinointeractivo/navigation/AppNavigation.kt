package com.example.destinointeractivo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.destinointeractivo.Screens.MainScreen
import com.example.destinointeractivo.Screens.Ajustes
import com.example.destinointeractivo.NavViewModel // Asegúrate de importar el NavViewModel
import com.example.destinointeractivo.Screens.Combat_001
import com.example.destinointeractivo.Screens.Combat_002
import com.example.destinointeractivo.Screens.Combat_003
import com.example.destinointeractivo.Screens.Combat_004
import com.example.destinointeractivo.Screens.Combat_005
import com.example.destinointeractivo.Screens.Combat_001_Victory
import com.example.destinointeractivo.Screens.Combat_002_Victory
import com.example.destinointeractivo.Screens.Combat_003_Victory
import com.example.destinointeractivo.Screens.Combat_004_Victory

import com.example.destinointeractivo.Screens.DerrotaScreen
import com.example.destinointeractivo.Screens.VictoriaScreen
import com.example.destinointeractivo.Screens.WeaponSelectionScreen

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
        composable(route = AppScreens.Combat_001.route) {
            Combat_001(navController, navViewModel)
        }
        composable(route = AppScreens.Combat_002.route) {
            Combat_002(navController, navViewModel)
        }
        composable(route = AppScreens.Combat_003.route) {
            Combat_003(navController, navViewModel)
        }
        composable(route = AppScreens.Combat_004.route) {
            Combat_004(navController, navViewModel)
        }
        composable(route = AppScreens.Combat_005.route) {
            Combat_005(navController, navViewModel)
        }
        composable(route = AppScreens.VictoriaScreen.route) {
            VictoriaScreen(navController, navViewModel)
        }
        composable(route = AppScreens.DerrotaScreen.route) {
            DerrotaScreen(navController, navViewModel)
        }
        composable(route = AppScreens.Combat_001_Victory.route) {
            Combat_001_Victory(navController, navViewModel)
        }
        composable(route = AppScreens.Combat_002_Victory.route) {
            Combat_002_Victory(navController, navViewModel)
        }
        composable(route = AppScreens.Combat_003_Victory.route) {
            Combat_003_Victory(navController, navViewModel)
        }
        composable(route = AppScreens.Combat_004_Victory.route) {
            Combat_004_Victory(navController, navViewModel)
        }

        composable(route = AppScreens.WeaponSelectionScreen.route) {
            WeaponSelectionScreen(navController, navViewModel)
        }




    }
}
