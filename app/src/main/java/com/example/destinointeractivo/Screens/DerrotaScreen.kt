package com.example.destinointeractivo.Screens

import android.content.Context
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
import com.example.destinointeractivo.BackgroundMusicPlayer
import com.example.destinointeractivo.ButtonStyle
import com.example.destinointeractivo.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.SoundPlayer
import com.example.destinointeractivo.VibrationViewModel
import com.example.destinointeractivo.VibrationViewModelFactory
import com.example.destinointeractivo.localizedString
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.viewmodel.EnemyViewModel
import com.example.destinointeractivo.viewmodel.PlayerViewModel

// Importa la nueva función PlayerStatusBar desde el paquete correcto
import com.example.destinointeractivo.PlayerStatusBar

import kotlinx.coroutines.launch

@Composable
fun DerrotaScreen(navController: NavController, navViewModel: NavViewModel) {
    BackHandler { /* Evita retroceso */ }
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel(factory = VibrationViewModelFactory(context))
    val playerViewModel: PlayerViewModel = viewModel()
    val enemyViewModel: EnemyViewModel = viewModel()

    navViewModel.lastScreen.value = AppScreens.DerrotaScreen.route

    // Carga las fuentes
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))

    DisposableEffect(Unit) {
        // Reproducir la música de combate al entrar a esta pantalla
        BackgroundMusicPlayer.playMusic(R.raw.music_derrota_02)
        onDispose {
            // No hacemos nada aquí. La música seguirá sonando si el usuario navega a otra pantalla.
        }
    }

    Scaffold(
        topBar = {
            // Llama a la nueva función PlayerStatusBar
            PlayerStatusBar(
                navController = navController,
                vibrationViewModel = vibrationViewModel,
                playerViewModel = playerViewModel, // Pasa la instancia del PlayerViewModel
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
                        text = localizedString(R.string.derrota_mensaje, playerViewModel.playerLanguage.collectAsState().value),
                        fontFamily = fuentePixelBold,
                        fontSize = 40.sp,
                        color = Color.White,
                        lineHeight = 60.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón "Reiniciar" usando ButtonStyle
                    ButtonStyle(
                        text = localizedString(R.string.combate_reiniciar, playerViewModel.playerLanguage.collectAsState().value),
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