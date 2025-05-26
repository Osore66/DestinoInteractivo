package com.example.destinointeractivo.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.destinointeractivo.CombatScreenData
import com.example.destinointeractivo.CombatScreenLayout
import com.example.destinointeractivo.R
import com.example.destinointeractivo.NavViewModel
import com.example.destinointeractivo.navigation.AppScreens // Importa AppScreens

@Composable
fun Combat_005(
    navController: NavController,
    navViewModel: NavViewModel,
) {
    BackHandler { /* Evita retroceso */ }

    // Define los datos específicos para este combate, incluyendo las rutas de navegación
    val combatData = remember {
        CombatScreenData(
            enemyId = 5,
            backgroundMusicResId = R.raw.music_battlemusic_01,
            backgroundPainterResId = R.drawable.enemigofondo1,
            /* // Descomentar para imagen de enemigo
            enemyPainterResId = R.drawable.slime,
            enemyImageSize = 80.dp,
             */
            startDialogueResId = R.string.ES_001_combate,
            // --- Parámetros de navegación ---
            currentScreenRoute = AppScreens.Combat_005.route, // La ruta de esta pantalla
            nextScreenRouteOnWin = AppScreens.VictoriaScreen.route, // La pantalla a la que ir al ganar este combate
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