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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.destinointeractivo.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.VibrationViewModel
import com.example.destinointeractivo.VibrationViewModelFactory
import com.example.destinointeractivo.localizedString
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.stringMap
import com.example.destinointeractivo.viewmodel.EnemyViewModel
import com.example.destinointeractivo.viewmodel.PlayerViewModel
import kotlinx.coroutines.launch

val buttonShape = RoundedCornerShape(4.dp) // Ajusta el radio según tu preferencia

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
    navViewModel.lastScreen.value = AppScreens.MainScreen.route

    // Función para vibrar
    fun vibrate() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(30)
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val tamanyoFuenteMainscreen = 25.sp

            // Botón "Continuar"
            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    coroutineScope.launch {
                        val player = playerViewModel.getPlayerData()
                        val lastLevel = player?.lastLevel ?: AppScreens.SinMetodos_Combate.route
                        navController.navigate(route = lastLevel)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 5.dp, shape = buttonShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape
            ) {
                Text(
                    text = localizedString(R.string.btn_continuar, playerLanguage),
                    fontFamily = fuentePixelBold,
                    fontSize = tamanyoFuenteMainscreen
                )
            }

            // Botón "Nueva Partida"
            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
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
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 5.dp, shape = buttonShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape
            ) {
                Text(
                    text = localizedString(R.string.btn_nueva_partida, playerLanguage),
                    fontFamily = fuentePixelBold,
                    fontSize = tamanyoFuenteMainscreen
                )
            }

            /*

            // Botón de prueba para SinMetodos_Combate (opcional)
            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    navController.navigate(route = AppScreens.SinMetodos_Combate.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 5.dp, shape = buttonShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape
            ) {
                Text(
                    text = "SinMetodos_Combate",
                    fontFamily = fuentePixelBold,
                    fontSize = tamanyoFuenteMainscreen
                )
            }

             */

            // Botón de ajustes (sin cambios)
            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    navController.navigate(route = AppScreens.Ajustes.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 5.dp, shape = buttonShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape
            ) {
                Text(
                    text = localizedString(R.string.btn_ajustes, playerLanguage),
                    fontFamily = fuentePixelBold,
                    fontSize = tamanyoFuenteMainscreen
                )
            }
        }
    }
}





