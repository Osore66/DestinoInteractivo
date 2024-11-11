package com.example.destinointeractivo.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.destinointeractivo.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.VibrationViewModel
import com.example.destinointeractivo.navigation.AppScreens
import kotlin.math.round

@Composable
fun Ajustes(navController: NavController, navViewModel: NavViewModel) {

    BackHandler {
        // Evita el retroceso
    }
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel { VibrationViewModel(context) }

    val volumenEfectos = remember { mutableStateOf(5f) }
    val volumenMusica = remember { mutableStateOf(5f) }
    val idiomas = listOf("Español", "English")
    val idiomaSeleccionado = remember { mutableStateOf(idiomas[0]) }
    val expanded = remember { mutableStateOf(false) }
    val tamanyoFuenteAjustes = 25.sp
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))
    val buttonShape = RoundedCornerShape(4.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202020))
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 80.dp)
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
                    text = "AJUSTES",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                IconButton(onClick = {
                    vibrationViewModel.vibrate(context)
                    // Navega a la última pantalla visitada
                    navController.navigate(navViewModel.lastScreen.value) {
                        popUpTo(AppScreens.Ajustes.route) { inclusive = true }
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.cerrar),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // VolumeSlider para efectos
            VolumeSlider(
                label = "Volumen efectos",
                value = volumenEfectos.value,
                onValueChange = {
                    volumenEfectos.value = round(it) // Redondear a entero
                },
                onValueChangeFinished = {
                    volumenEfectos.value = round(volumenEfectos.value) // Asegura que se mantenga en enteros
                    vibrationViewModel.vibrate(context)
                },
                fontFamily = fuentePixelBold
            )

            // VolumeSlider para música
            VolumeSlider(
                label = "Volumen música",
                value = volumenMusica.value,
                onValueChange = {
                    volumenMusica.value = round(it) // Redondear a entero
                },
                onValueChangeFinished = {
                    volumenMusica.value = round(volumenMusica.value) // Asegura que se mantenga en enteros
                    vibrationViewModel.vibrate(context)
                },
                fontFamily = fuentePixelBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox para activar/desactivar la vibración global
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Vibración",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Checkbox(
                    checked = vibrationViewModel.vibracionActiva.value,
                    onCheckedChange = { isChecked ->
                        vibrationViewModel.setVibracionActiva(isChecked)
                        vibrationViewModel.vibrate(context)
                    },
                    modifier = Modifier.padding(start = 8.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White,
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Menú desplegable de idioma
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Idioma",
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Spacer(modifier = Modifier.width(16.dp))
                Box {
                    OutlinedButton(
                        onClick = {
                            vibrationViewModel.vibrate(context)
                            expanded.value = true
                        },
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Text(
                            text = idiomaSeleccionado.value,
                            fontFamily = fuentePixelBold,
                            color = Color.White
                        )
                    }

                    Box(modifier = Modifier.padding(start = 26.dp, top = 50.dp)) {
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            modifier = Modifier.background(Color.DarkGray)
                        ) {
                            idiomas.forEachIndexed { index, idioma ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            vibrationViewModel.vibrate(context)
                                            idiomaSeleccionado.value = idioma
                                            expanded.value = false
                                        }
                                        .background(Color.DarkGray)
                                        .padding(12.dp, 3.dp)
                                ) {
                                    Text(
                                        text = idioma,
                                        fontFamily = fuentePixelBold,
                                        color = Color.White
                                    )
                                }
                                if (index < idiomas.size - 1) {
                                    Divider(thickness = 0.5.dp, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Botón para salir al menú
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
                    text = "Guardar y salir",
                    fontFamily = fuentePixelBold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

// Función VolumeSlider
@Composable
fun VolumeSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    fontFamily: FontFamily
) {
    Column {
        Text(
            text = "$label: ${value.toInt()}", // Muestra el valor actual como entero
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = fontFamily
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            onValueChangeFinished = onValueChangeFinished,
            valueRange = 0f..10f,
            steps = 9, // Ajuste para que el slider tenga 10 posiciones
            colors = SliderDefaults.colors(
                thumbColor = if (value == 0f) Color.Gray else Color.White, // Cambia el color de la bolita
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.Gray
            )
        )
    }
}
