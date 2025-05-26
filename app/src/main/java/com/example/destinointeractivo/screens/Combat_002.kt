package com.example.destinointeractivo.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.destinointeractivo.functions.CombatScreenData
import com.example.destinointeractivo.functions.CombatScreenLayout
import com.example.destinointeractivo.R
import com.example.destinointeractivo.viewmodel.NavViewModel
import com.example.destinointeractivo.navigation.AppScreens

@Composable
fun Combat_002(
    navController: NavController,
    navViewModel: NavViewModel,
) {
    BackHandler { /* Evita retroceso */ }

    // Define los datos específicos para este combate, incluyendo las rutas de navegación
    val combatData = remember {
        CombatScreenData(
            enemyId = 2,
            backgroundMusicResId = R.raw.music_battlemusic_02,
            backgroundPainterResId = R.drawable.paisaje1,
            enemyPainterResId = R.drawable.enemigo1,
            enemyImageSize = 190.dp,
            startDialogueResId = R.string.combate_002,
            // --- Parámetros de navegación ---
            currentScreenRoute = AppScreens.Combat_002.route, // La ruta de esta pantalla
            nextScreenRouteOnWin = AppScreens.Combat_002_Victory.route, // La pantalla a la que ir al ganar este combate
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