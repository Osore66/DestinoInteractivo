package com.example.destinointeractivo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.destinointeractivo.navigation.AppScreens

@Composable
fun Prueba(navController: NavController, navViewModel: NavViewModel) {
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel { VibrationViewModel(context) }
    val tamanyoFuenteAjustes = 20.sp
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))
    val buttonShape = RoundedCornerShape(4.dp)
    navViewModel.lastScreen.value = AppScreens.Prueba.route

    // Estado para vida actual y máxima del jugador
    val vidaJugador = remember { mutableStateOf(5) }
    val vidaMaximaJugador = remember { mutableStateOf(10) }

    // Estado para vida actual y máxima del enemigo
    val vidaEnemigo = remember { mutableStateOf(20) }
    val vidaMaximaEnemigo = remember { mutableStateOf(20) }

    // Estado para daño y escudo
    val dañoJugador = remember { mutableStateOf(3) }
    val escudoEnemigo = 2

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202020))
            .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 80.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            // Encabezado
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "PRUEBA",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                // Modificación en el botón de cierre
                IconButton(onClick = {
                    vibrationViewModel.vibrate(context)
                    // Navega hacia la pantalla de Ajustes en lugar de cerrar
                    navController.navigate(route = AppScreens.Ajustes.route)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.settings_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Estadísticas del jugador
            Text(
                text = "Mis stats",
                color = Color.White,
                fontSize = tamanyoFuenteAjustes,
                fontFamily = fuentePixelBold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Vida:",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${vidaJugador.value}/${vidaMaximaJugador.value}",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Daño:",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${dañoJugador.value}",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Escudo:",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$escudoEnemigo",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Estadísticas del enemigo
            Text(
                text = "Enemigo",
                color = Color.White,
                fontSize = tamanyoFuenteAjustes,
                fontFamily = fuentePixelBold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Vida:",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${vidaEnemigo.value}/${vidaMaximaEnemigo.value}",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Daño:",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "3",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Escudo:",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$escudoEnemigo",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Botón de ataque
            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    val dañoEfectivo = (dañoJugador.value - escudoEnemigo).coerceAtLeast(0)
                    vidaEnemigo.value = (vidaEnemigo.value - dañoEfectivo).coerceAtLeast(0)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        shape = buttonShape,
                        clip = false
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape
            ) {
                Text(
                    text = "Atacar",
                    fontFamily = fuentePixelBold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            // Botón para aumentar ataque
            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    dañoJugador.value += 2
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        shape = buttonShape,
                        clip = false
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape
            ) {
                Text(
                    text = "+2 ataque",
                    fontFamily = fuentePixelBold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón para aumentar ataque y vida
            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    dañoJugador.value += 2
                    vidaMaximaJugador.value += 1
                    vidaJugador.value = (vidaJugador.value + 1).coerceAtMost(vidaMaximaJugador.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        shape = buttonShape,
                        clip = false
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape
            ) {
                Text(
                    text = "+2 ataque, +1 vida",
                    fontFamily = fuentePixelBold,
                    fontSize = 20.sp
                )
            }


            Spacer(modifier = Modifier.height(20.dp))


            // Botón para aumentar vida
            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    vidaJugador.value = (vidaJugador.value + 1).coerceAtMost(vidaMaximaJugador.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        shape = buttonShape,
                        clip = false
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape
            ) {
                Text(
                    text = "Curar +1 vida",
                    fontFamily = fuentePixelBold,
                    fontSize = 20.sp
                )
            }


            Spacer(modifier = Modifier.height(20.dp))



            // Botón para aumentar vida ENEMIGA
            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    vidaEnemigo.value = (vidaEnemigo.value + 1).coerceAtMost(vidaMaximaEnemigo.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        shape = buttonShape,
                        clip = false
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape
            ) {
                Text(
                    text = "Curar +1 vida ENEMIGO",
                    fontFamily = fuentePixelBold,
                    fontSize = 20.sp
                )
            }


            Spacer(modifier = Modifier.height(20.dp))


            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    navController.navigate(route = AppScreens.MainScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        shape = buttonShape,
                        clip = false
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = buttonShape
            ) {
                Text(
                    text = "Inicio",
                    fontFamily = fuentePixelBold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
