package com.example.destinointeractivo.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.destinointeractivo.sound.BackgroundMusicPlayer
import com.example.destinointeractivo.functions.ButtonStyle
import com.example.destinointeractivo.viewmodel.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.sound.SoundPlayer
import com.example.destinointeractivo.viewmodel.VibrationViewModel
import com.example.destinointeractivo.viewmodel.VibrationViewModelFactory
import com.example.destinointeractivo.functions.localizedString
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.viewmodel.EnemyViewModel
import com.example.destinointeractivo.viewmodel.PlayerViewModel
import com.example.destinointeractivo.functions.PlayerStatusBar

import kotlinx.coroutines.launch

@Composable
fun VictoriaScreen(navController: NavController, navViewModel: NavViewModel) {
    BackHandler { /* Evita retroceso */ }
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel(factory = VibrationViewModelFactory(context))
    val playerViewModel: PlayerViewModel = viewModel()
    val enemyViewModel: EnemyViewModel = viewModel()
    val playerLanguage by playerViewModel.playerLanguage.collectAsState()
    navViewModel.lastScreen.value = AppScreens.VictoriaScreen.route

    // Carga las fuentes y datos iniciales
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))

    DisposableEffect(Unit) {
        BackgroundMusicPlayer.playMusic(R.raw.music_victory)
        onDispose {
            // No hacemos nada aquí. La música seguirá sonando si el usuario navega a otra pantalla.
        }
    }

    Scaffold(
        topBar = {
            PlayerStatusBar(
                navController = navController,
                vibrationViewModel = vibrationViewModel,
                playerViewModel = playerViewModel,
                context = context
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.VeryDarkGrey))
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = localizedString(R.string.victoria_mensaje, playerLanguage),
                        fontFamily = fuentePixelBold,
                        fontSize = 24.sp,
                        color = Color.White,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón "Reiniciar" usando ButtonStyle
                    ButtonStyle(
                        text = localizedString(R.string.combate_reiniciar, playerLanguage),
                        fontFamily = fuentePixelBold,
                        enabled = true,
                        onClick = {
                            vibrationViewModel.vibrate(context)
                            navViewModel.viewModelScope.launch {
                                try {
                                    val settings = playerViewModel.getPlayerSettings()
                                    playerViewModel.resetPlayerData(settings)
                                    enemyViewModel.resetEnemyData()
                                } catch (e: Exception) {
                                    playerViewModel.resetPlayerDataWithDefaultSettings()
                                    enemyViewModel.resetEnemyData()
                                }
                                SoundPlayer.playSoundButton(context)
                                navController.navigate(route = AppScreens.MainScreen.route)
                            }
                        }
                    )
                }
            }
        }
    )
}