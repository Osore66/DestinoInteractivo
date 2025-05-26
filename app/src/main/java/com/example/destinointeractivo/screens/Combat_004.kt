package com.example.destinointeractivo.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.destinointeractivo.functions.CombatScreenData
import com.example.destinointeractivo.functions.CombatScreenLayout
import com.example.destinointeractivo.R
import com.example.destinointeractivo.viewmodel.NavViewModel
import com.example.destinointeractivo.navigation.AppScreens

@Composable
fun Combat_004(
    navController: NavController,
    navViewModel: NavViewModel,
) {
    BackHandler { /* Evita retroceso */ }

    // Define los datos específicos para este combate, incluyendo las rutas de navegación
    val combatData = remember {
        CombatScreenData(
            enemyId = 4,
            backgroundMusicResId = R.raw.music_battlemusic_04,
            backgroundPainterResId = R.drawable.enemigofondo3,
            /* // Descomentar para imagen de enemigo
            enemyPainterResId = R.drawable.slime,
            enemyImageSize = 80.dp,
             */
            startDialogueResId = R.string.combate_004,
            // --- Parámetros de navegación ---
            currentScreenRoute = AppScreens.Combat_004.route, // La ruta de esta pantalla
            nextScreenRouteOnWin = AppScreens.Combat_004_Victory.route, // La pantalla a la que ir al ganar este combate
            settingsScreenRoute = AppScreens.Ajustes.route // Ruta a la pantalla de ajustes (por defecto)
        )
    }

    // Llama a la función de layout genérica de combate
    CombatScreenLayout(
        navController = navController,
        navViewModel = navViewModel,
        combatData = combatData
    )
}