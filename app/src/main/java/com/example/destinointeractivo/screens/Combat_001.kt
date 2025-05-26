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
fun Combat_001(
    navController: NavController,
    navViewModel: NavViewModel,
) {
    BackHandler { /* Evita retroceso */ }

    // Define los datos específicos para este combate, incluyendo las rutas de navegación
    val combatData = remember {
        CombatScreenData(
            enemyId = 1,
            backgroundMusicResId = R.raw.music_battlemusic_01,
            backgroundPainterResId = R.drawable.paisaje2,
            enemyPainterResId = R.drawable.goblin1,
            enemyImageSize = 140.dp,
            startDialogueResId = R.string.combate_001,
            // --- Parámetros de navegación ---
            currentScreenRoute = AppScreens.Combat_001.route, // La ruta de esta pantalla
            nextScreenRouteOnWin = AppScreens.Combat_001_Victory.route, // La pantalla a la que ir al ganar este combate
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