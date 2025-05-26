package com.example.destinointeractivo.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.destinointeractivo.viewmodel.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.functions.StatType
import com.example.destinointeractivo.functions.VictoryRewardOption
import com.example.destinointeractivo.functions.VictoryScreenData
import com.example.destinointeractivo.functions.VictoryScreenLayout

@Composable
fun Combat_003_Victory(
    navController: NavController,
    navViewModel: NavViewModel
) {
    navViewModel.lastScreen.value = AppScreens.Combat_003_Victory.route
    BackHandler { /* Evita retroceso */ }

    // Define los datos específicos para esta pantalla de victoria
    val victoryData = remember {
        VictoryScreenData(
            messageTextResId = R.string.victoria_003_message,
            nextLevelRoute = AppScreens.Combat_004.route,
            rewardOptions = listOf(
                VictoryRewardOption(R.string.victoria_001_damage, 1, StatType.DAMAGE),
                //VictoryRewardOption(R.string.victoria_001_shield, 1, StatType.DEFENSE),
                VictoryRewardOption(R.string.victoria_001_potion, 1, StatType.POTION),
                VictoryRewardOption(R.string.victoria_001_heal, 15, StatType.POTION_HEAL_AMOUNT)
            )
        )
    }
    // Llama a la función de layout genérica de victoria desde el archivo utils
    VictoryScreenLayout(
        navController = navController,
        navViewModel = navViewModel,
        victoryData = victoryData
    )
}