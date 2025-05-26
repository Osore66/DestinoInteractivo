package com.example.destinointeractivo.Screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect // Importar LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
    var areButtonsEnabled by remember { mutableStateOf(true) }
    val lifecycleOwner = LocalLifecycleOwner.current
    navViewModel.lastScreen.value = AppScreens.MainScreen.route

    val lastLevelRoute by playerViewModel.lastLevel.collectAsState()

    // Observa el volumen de la música del PlayerViewModel
    val musicVolume by playerViewModel.playerMusicVolume.collectAsState() // Esto es un Int

    // **CAMBIO CLAVE AQUÍ:** Usar LaunchedEffect para reaccionar al cambio de musicVolume
    LaunchedEffect(musicVolume) {
        // Asegúrate de que BackgroundMusicPlayer esté inicializado
        BackgroundMusicPlayer.initialize(context.applicationContext)

        // Establece el volumen cada vez que musicVolume cambia (incluida la carga inicial)
        BackgroundMusicPlayer.setMusicVolume(musicVolume)
        Log.d("MainScreen", "Setting music volume from ViewModel to: $musicVolume")

        // Reproduce la música. Si ya está sonando, BackgroundMusicPlayer debería manejarlo.
        BackgroundMusicPlayer.playMusic(R.raw.music_alegre_03)
        Log.d("MainScreen", "Attempting to play music R.raw.music_alegre_03")
    }

    // El DisposableEffect(Unit) ya no es necesario si LaunchedEffect lo maneja.
    // Si quieres un control muy específico del ciclo de vida de la COMPOSABLE,
    // podrías mantener un DisposableEffect(Unit) que simplemente pause/reanude,
    // pero el LaunchedEffect(musicVolume) es más directo para la carga del volumen y el inicio.

    /*
    DisposableEffect(Unit) {
        BackgroundMusicPlayer.initialize(context.applicationContext)
        BackgroundMusicPlayer.setMusicVolume(musicVolume)
        Log.d("MainScreen", "Setting initial music volume from ViewModel to: $musicVolume")
        BackgroundMusicPlayer.playMusic(R.raw.music_alegre_03)
        onDispose {
            // Considera si quieres pausar o detener aquí si la pantalla no estará visible.
            // Para música de fondo que persiste, no harías nada aquí.
        }
    }
    */

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
                enabled = true,
                onClick = {
                    vibrationViewModel.vibrate(context)
                    SoundPlayer.playSoundButton(context)
                    coroutineScope.launch {
                        navController.navigate(route = lastLevelRoute)
                    }
                }
            )


            // Botón "Nueva Partida"
            ButtonStyle(
                text = localizedString(R.string.btn_nueva_partida, playerLanguage),
                fontFamily = fuentePixelBold,
                enabled = true,
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
                        playerViewModel.updateLastLevel(AppScreens.WeaponSelectionScreen.route)
                        navController.navigate(route = AppScreens.WeaponSelectionScreen.route)
                    }
                }
            )

            // Botón de ajustes
            ButtonStyle(
                text = localizedString(R.string.btn_ajustes, playerLanguage),
                fontFamily = fuentePixelBold,
                enabled = true,
                onClick = {
                    vibrationViewModel.vibrate(context)
                    SoundPlayer.playSoundButton(context)
                    navController.navigate(route = AppScreens.Ajustes.route)
                }
            )
        }
    }
}