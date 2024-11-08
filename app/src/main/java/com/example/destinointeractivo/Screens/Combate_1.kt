package com.example.destinointeractivo.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.destinointeractivo.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.VibrationViewModel
import com.example.destinointeractivo.navigation.AppScreens

val tamanyoFuenteCombate = 20.sp

@Composable
fun Combate_1(navController: NavController, navViewModel: NavViewModel) {
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel { VibrationViewModel(context) }
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))
    val buttonShape = RoundedCornerShape(4.dp)
    navViewModel.lastScreen.value = AppScreens.Combate_1.route

    val spacerIcons = 14.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.VeryDarkGrey))
            .padding(top = 40.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Barra de estado (fondo negro con iconos)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.VeryDarkGrey))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconWithText(iconRes = R.drawable.corazon, text = "15/15", size = 25.dp)
                    Spacer(modifier = Modifier.width(spacerIcons))
                    IconWithText(iconRes = R.drawable.espada, text = "3", size = 23.dp)
                    Spacer(modifier = Modifier.width(spacerIcons))
                    IconWithText(iconRes = R.drawable.escudo, text = "1", size = 30.dp)
                    Spacer(modifier = Modifier.width(spacerIcons))
                    IconWithText(iconRes = R.drawable.potion, text = "2", size = 23.dp)
                }
                IconButton(onClick = {
                    vibrationViewModel.vibrate(context)
                    navController.navigate(route = AppScreens.Ajustes.route)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.settings_icon),
                        contentDescription = "Cerrar",
                        tint = Color.White
                    )
                }
            }

            // Sección enemigo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                // Fondo
                Image(
                    painter = painterResource(id = R.drawable.paisaje),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.5f), buttonShape)
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconWithText(iconRes = R.drawable.corazon, text = "5/10", size = 25.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            IconWithText(iconRes = R.drawable.espada, text = "1", size = 23.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            IconWithText(iconRes = R.drawable.escudo, text = "1", size = 30.dp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Imagen del enemigo
                    Image(
                        painter = painterResource(id = R.drawable.slime),
                        contentDescription = "Slime",
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección final
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(colorResource(id = R.color.VeryDarkGrey))
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Texto de combate
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Un slime te impide avanzar, ¿qué decides hacer?",
                        color = Color.White,
                        fontSize = tamanyoFuenteCombate,
                        fontFamily = fuentePixelBold,
                        textAlign = TextAlign.Center
                    )
                }

                // Botones de acción
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp)
                ) {
                    ActionButton(text = "Atacar", fontFamily = fuentePixelBold) {
                        vibrationViewModel.vibrate(context)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ActionButton(text = "Defender", fontFamily = fuentePixelBold) {
                        vibrationViewModel.vibrate(context)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ActionButton(text = "Poción", fontFamily = fuentePixelBold) {
                        vibrationViewModel.vibrate(context)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ActionButton(text = "Ataque enemigo", fontFamily = fuentePixelBold) {
                        vibrationViewModel.vibrate(context)
                    }
                }
            }
        }
    }
}

@Composable
fun IconWithText(iconRes: Int, text: String, size: Dp = 24.dp) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(size)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ActionButton(text: String, fontFamily: FontFamily, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text,
            fontFamily = fontFamily,
            fontSize = tamanyoFuenteCombate
        )
    }
}
