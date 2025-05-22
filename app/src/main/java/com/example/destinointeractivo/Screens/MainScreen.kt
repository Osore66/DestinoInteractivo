package com.example.destinointeractivo.Screens

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.destinointeractivo.ButtonStyle // Importa ButtonStyle
import com.example.destinointeractivo.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.SoundPlayer
import com.example.destinointeractivo.VibrationViewModel
import com.example.destinointeractivo.VibrationViewModelFactory
import com.example.destinointeractivo.localizedString
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.viewmodel.EnemyViewModel
import com.example.destinointeractivo.viewmodel.PlayerViewModel
import kotlinx.coroutines.launch
import com.example.destinointeractivo.BackgroundMusicPlayer


@Composable
fun MainScreen(navController: NavController, navViewModel: NavViewModel) {
    BackHandler { /* Evita retroceso */ }

    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel(factory = VibrationViewModelFactory(context))
    val playerViewModel: PlayerViewModel = viewModel()
    val enemyViewModel: EnemyViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val playerLanguage by playerViewModel.playerLanguage.collectAsState()
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))
    var areButtonsEnabled by remember { mutableStateOf(true) } // Cooldown global (aunque no se usa en este caso para los botones)
    val lifecycleOwner = LocalLifecycleOwner.current
    navViewModel.lastScreen.value = AppScreens.MainScreen.route

    DisposableEffect(lifecycleOwner) {
        BackgroundMusicPlayer.initialize(context)
        lifecycleOwner.lifecycle.addObserver(BackgroundMusicPlayer)
        BackgroundMusicPlayer.playMusic(R.raw.music_alegre_03)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(BackgroundMusicPlayer)
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(R.drawable.portada),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Botones centrados
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 400.dp, end = 16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // Botón "Continuar"
            ButtonStyle(
                text = localizedString(R.string.btn_continuar, playerLanguage),
                fontFamily = fuentePixelBold,
                enabled = true, // Siempre activo para continuar
                onClick = {
                    vibrationViewModel.vibrate(context)
                    SoundPlayer.playSoundButton(context)
                    coroutineScope.launch {
                        val player = playerViewModel.getPlayerData()
                        val lastLevel = player?.lastLevel ?: AppScreens.SinMetodos_Combate.route
                        navController.navigate(route = lastLevel)
                    }
                }
            )

            // Botón "Nueva Partida"
            ButtonStyle(
                text = localizedString(R.string.btn_nueva_partida, playerLanguage),
                fontFamily = fuentePixelBold,
                enabled = true, // Siempre activo para nueva partida
                onClick = {
                    vibrationViewModel.vibrate(context)
                    SoundPlayer.playSoundNewgame(context)
                    coroutineScope.launch {
                        try {
                            val settings = playerViewModel.getPlayerSettings()
                            playerViewModel.resetPlayerData(settings)
                            enemyViewModel.resetEnemyData()
                        } catch (e: Exception) {
                            playerViewModel.resetPlayerDataWithDefaultSettings()
                            enemyViewModel.resetEnemyData()
                        }
                        navController.navigate(route = AppScreens.SinMetodos_Combate.route)
                    }
                }
            )

            // Botón de ajustes
            ButtonStyle(
                text = localizedString(R.string.btn_ajustes, playerLanguage),
                fontFamily = fuentePixelBold,
                enabled = true, // Siempre activo
                onClick = {
                    vibrationViewModel.vibrate(context)
                    SoundPlayer.playSoundButton(context)
                    navController.navigate(route = AppScreens.Ajustes.route)
                }
            )
        }
    }
}