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
import com.example.destinointeractivo.navigation.AppScreens

val buttonShape = RoundedCornerShape(4.dp) // Ajusta el radio según tu preferencia

@Composable
fun MainScreen(navController: NavController, navViewModel: NavViewModel) {

    BackHandler {
        // No hacer nada, evita el retroceso
    }
    val context = LocalContext.current // Para acceder al contexto y vibrar
    val vibrationViewModel: VibrationViewModel = viewModel { VibrationViewModel(context) }
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))
    navViewModel.lastScreen.value = AppScreens.MainScreen.route

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
            .background(Color.Black) // Esto es temporal, el fondo se reemplaza por la imagen
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(R.drawable.portada),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(), // Ocupar todo el espacio disponible
            contentScale = ContentScale.Crop // Escalar la imagen para llenar todo el espacio
        )

        // Columna con botones superpuestos en el centro
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 400.dp, end = 16.dp)
                .align(Alignment.Center), // Centrar la columna
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)  // Separación de los botones
        ) {
            val buttonShape = buttonShape // Define la forma del botón
            val paddingTopBotones = PaddingValues(top = 0.dp)
            val tamanyoFuenteMainscreen = 25.sp

            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    navController.navigate(route = AppScreens.Prueba.route)
                    // Acción de continuar
                },
                modifier = Modifier
                    .padding(paddingTopBotones)
                    .fillMaxWidth(1f)
                    .shadow(
                        elevation = 5.dp,
                        shape = buttonShape,
                        ambientColor = Color.LightGray,
                        clip = true,
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape // Usa la misma forma para el botón
            ) {
                Text(
                    text = "Continuar",
                    fontFamily = fuentePixelBold,
                    fontSize = tamanyoFuenteMainscreen
                )
            }

            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    navController.navigate(route = AppScreens.Combate_1.route)
                    // Acción de nueva partida
                },
                modifier = Modifier
                    .padding(paddingTopBotones)
                    .fillMaxWidth(1f)
                    .shadow(
                        elevation = 5.dp,
                        shape = buttonShape,
                        ambientColor = Color.LightGray,
                        clip = true,
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape // Usa la misma forma para el botón
            ) {
                Text(
                    text = "Nueva partida",
                    fontFamily = fuentePixelBold,
                    fontSize = tamanyoFuenteMainscreen
                )
            }

            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    navController.navigate(route = AppScreens.Ajustes.route)
                },
                modifier = Modifier
                    .padding(paddingTopBotones)
                    .fillMaxWidth(1f)
                    .shadow(
                        elevation = 5.dp,
                        shape = buttonShape,
                        ambientColor = Color.LightGray,
                        clip = true,
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape // Usa la misma forma para el botón
            ) {
                Text(
                    text = "Ajustes",
                    fontFamily = fuentePixelBold,
                    fontSize = tamanyoFuenteMainscreen
                )
            }
        }
    }
}





