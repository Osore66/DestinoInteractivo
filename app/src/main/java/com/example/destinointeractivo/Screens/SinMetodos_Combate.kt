package com.example.destinointeractivo.Screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.destinointeractivo.BarraEstado
import com.example.destinointeractivo.ButtonStyle
import com.example.destinointeractivo.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.StatsStyle
import com.example.destinointeractivo.VibrationViewModel
import com.example.destinointeractivo.navigation.AppScreens


@Composable
fun SinMetodos_Combate(
    navController: NavController,
    navViewModel: NavViewModel,

    ) {
    // Contexto local
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel { VibrationViewModel(context) }
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))

    // Guardar la última pantalla en el NavViewModel
    navViewModel.lastScreen.value = AppScreens.Combate_1.route

    // Estructura principal de la pantalla
    Scaffold(
        topBar = {
            // Barra de estado reutilizable
            BarraEstado(
                vibrationViewModel = vibrationViewModel,
                navController = navController,
                context = context
            )
        },
        content = { innerPadding ->
            // Prevenir navegación hacia atrás
            BackHandler { }
            // Contenido principal de la pantalla
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.VeryDarkGrey))
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Sección del enemigo
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        // Fondo
                        Image(
                            painter = painterResource(id = R.drawable.enemigofondo1),
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
                                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                    .padding(8.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    StatsStyle(iconRes = R.drawable.corazon, text = "5/10", size = 25.dp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    StatsStyle(iconRes = R.drawable.espada, text = "1", size = 23.dp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    StatsStyle(iconRes = R.drawable.escudo, text = "1", size = 30.dp)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Imagen del enemigo
                            /*
                            Image(
                                painter = painterResource(R.drawable.enemigo1),
                                contentDescription = "Slime",
                                modifier = Modifier
                                    .size(180.dp)
                                    .align(Alignment.CenterHorizontally)

                            )

                             */
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Texto de combate y botones de acción
                    // Usar Column para dividir el espacio
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp)
                    ) {
                        // Texto de combate (Ocupa el espacio disponible)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),  // El texto ocupa el espacio restante
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Soy un simple ejemplo de prueba para ver cúanto ocupo, Soy un simple ejemplo de prueba para ver cúanto ocupo, Soy un simple ejemplo de prueba para ver cúanto ocupo, Soy un simple ejemplo de prueba para ver cúanto ocupo",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = fuentePixelBold,
                                textAlign = TextAlign.Center
                            )
                        }

                        // Botones de acción (Se quedan en la parte inferior)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp)
                        ) {
                            ButtonStyle(text = "Atacar", fontFamily = fuentePixelBold) {
                                vibrationViewModel.vibrate(context)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            ButtonStyle(text = "Defender", fontFamily = fuentePixelBold) {
                                vibrationViewModel.vibrate(context)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            ButtonStyle(text = "Poción", fontFamily = fuentePixelBold) {
                                vibrationViewModel.vibrate(context)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            ButtonStyle(text = "Ataque enemigo", fontFamily = fuentePixelBold) {
                                vibrationViewModel.vibrate(context)
                            }
                        }
                    }
                }
            }
        }
    )
}


