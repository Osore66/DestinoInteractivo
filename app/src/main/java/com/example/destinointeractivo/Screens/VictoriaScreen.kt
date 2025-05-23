package com.example.destinointeractivo.Screens

import android.content.Context
import android.os.Build
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
import com.example.destinointeractivo.StatsStyle
import com.example.destinointeractivo.VibrationViewModel
import com.example.destinointeractivo.VibrationViewModelFactory
import com.example.destinointeractivo.localizedString
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.viewmodel.EnemyViewModel
import com.example.destinointeractivo.viewmodel.PlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun VictoriaScreen(navController: NavController, navViewModel: NavViewModel) {
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel(factory = VibrationViewModelFactory(context))
    val playerViewModel: PlayerViewModel = viewModel()
    val enemyViewModel: EnemyViewModel = viewModel()
    val playerLanguage by playerViewModel.playerLanguage.collectAsState()
    navViewModel.lastScreen.value = AppScreens.VictoriaScreen.route

    // Carga las fuentes y datos iniciales
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))

    // Observar datos del jugador
    val playerLife by playerViewModel.playerLife.collectAsState(initial = 0)
    val playerDamage by playerViewModel.playerDamage.collectAsState(initial = 0)
    val playerDefense by playerViewModel.playerDefense.collectAsState(initial = 0)
    val playerPotions by playerViewModel.playerPotions.collectAsState(initial = 0)
    val playerMaxLife by playerViewModel.playerMaxLife.collectAsState(initial = 0)
    val playerPotionHealAmount by playerViewModel.playerPotionHealAmount.collectAsState(initial = 0)

    // Estilo de los stats
    val spacerIcons = 4.dp

    DisposableEffect(Unit) {
        // Reproducir la música de combate al entrar a esta pantalla
        BackgroundMusicPlayer.playMusic(R.raw.music_victory)
        onDispose {
            // No hacemos nada aquí. La música seguirá sonando si el usuario navega a otra pantalla.
            // La pausa global la maneja BackgroundMusicPlayer cuando la app se va a segundo plano.
        }
    }

    Scaffold(
        topBar = {
            Column {
                // Barra de estado superior (con stats y ajustes)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.VeryDarkGrey))
                        .padding(start = 16.dp, end = 16.dp, top = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Stats del jugador (vida, defensa, pociones)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StatsStyle(
                            iconRes = R.drawable.corazon,
                            text = "$playerLife/$playerMaxLife",
                            size = 25.dp
                        )
                        Spacer(modifier = Modifier.width(spacerIcons))
                        StatsStyle(iconRes = R.drawable.espada, text = "$playerDamage", size = 23.dp)
                        Spacer(modifier = Modifier.width(spacerIcons))
                        StatsStyle(iconRes = R.drawable.escudo, text = "$playerDefense", size = 30.dp)
                        Spacer(modifier = Modifier.width(spacerIcons))
                        //Cantidad pociones
                        StatsStyle(iconRes = R.drawable.potion, text = "$playerPotions", size = 23.dp)
                        Spacer(modifier = Modifier.width(spacerIcons))
                        //Cantidad curación
                        StatsStyle(iconRes = R.drawable.healpotion, text = "$playerPotionHealAmount", size = 23.dp)
                    }

                    // Botón de ajustes (engranaje)
                    IconButton(
                        onClick = {
                            vibrationViewModel.vibrate(context)
                            SoundPlayer.playSoundButton(context)
                            navController.navigate(route = AppScreens.Ajustes.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings_icon),
                            contentDescription = "Settings",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                // Espaciado inferior
                Spacer(modifier = Modifier.height(10.dp))
            }
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
                        fontSize = 40.sp,
                        color = Color.White,
                        lineHeight = 60.sp,
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