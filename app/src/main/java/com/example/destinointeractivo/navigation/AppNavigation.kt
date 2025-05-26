package com.example.destinointeractivo.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.destinointeractivo.screens.MainScreen
import com.example.destinointeractivo.screens.Ajustes
import com.example.destinointeractivo.viewmodel.NavViewModel
import com.example.destinointeractivo.screens.Combat_001
import com.example.destinointeractivo.screens.Combat_002
import com.example.destinointeractivo.screens.Combat_003
import com.example.destinointeractivo.screens.Combat_004
import com.example.destinointeractivo.screens.Combat_005
import com.example.destinointeractivo.screens.Combat_001_Victory
import com.example.destinointeractivo.screens.Combat_002_Victory
import com.example.destinointeractivo.screens.Combat_003_Victory
import com.example.destinointeractivo.screens.Combat_004_Victory
import com.example.destinointeractivo.screens.DerrotaScreen
import com.example.destinointeractivo.screens.VictoriaScreen
import com.example.destinointeractivo.screens.WeaponSelectionScreen

// Transiciones sin animación
val noAnimationEnterTransition: EnterTransition = fadeIn(animationSpec = tween(0))
val noAnimationExitTransition: ExitTransition = fadeOut(animationSpec = tween(0))

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navViewModel: NavViewModel = viewModel()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(
            route = AppScreens.MainScreen.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            MainScreen(navController, navViewModel)
        }
        composable(
            route = AppScreens.Ajustes.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            Ajustes(navController, navViewModel)
        }
        composable(
            route = AppScreens.Combat_001.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            Combat_001(navController, navViewModel)
        }
        composable(
            route = AppScreens.Combat_002.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            Combat_002(navController, navViewModel)
        }
        composable(
            route = AppScreens.Combat_003.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            Combat_003(navController, navViewModel)
        }
        composable(
            route = AppScreens.Combat_004.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            Combat_004(navController, navViewModel)
        }
        composable(
            route = AppScreens.Combat_005.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            Combat_005(navController, navViewModel)
        }
        composable(
            route = AppScreens.VictoriaScreen.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            VictoriaScreen(navController, navViewModel)
        }
        composable(
            route = AppScreens.DerrotaScreen.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            DerrotaScreen(navController, navViewModel)
        }
        composable(
            route = AppScreens.Combat_001_Victory.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            Combat_001_Victory(navController, navViewModel)
        }
        composable(
            route = AppScreens.Combat_002_Victory.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            Combat_002_Victory(navController, navViewModel)
        }
        composable(
            route = AppScreens.Combat_003_Victory.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            Combat_003_Victory(navController, navViewModel)
        }
        composable(
            route = AppScreens.Combat_004_Victory.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            Combat_004_Victory(navController, navViewModel)
        }
        composable(
            route = AppScreens.WeaponSelectionScreen.route,
            enterTransition = { noAnimationEnterTransition },
            exitTransition = { noAnimationExitTransition }
        ) {
            WeaponSelectionScreen(navController, navViewModel)
        }
    }
}