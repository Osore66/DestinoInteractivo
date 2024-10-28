package com.example.destinointeractivo

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.destinointeractivo.navigation.AppScreens

//@Preview
@Composable
fun Ajustes(navController: NavController, text: String?) {
    val volumenEfectos = remember { mutableStateOf(0.5f) }
    val volumenMusica = remember { mutableStateOf(0.5f) }
    val vibracionActiva = remember { mutableStateOf(true) }
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
            .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 80.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopStart)
        ) {
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
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.cerrar),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            VolumeSlider(
                "Volumen efectos",
                volumenEfectos.value,
                { volumenEfectos.value = it },
                fontFamily = fuentePixelBold
            )
            VolumeSlider(
                "Volumen música",
                volumenMusica.value,
                { volumenMusica.value = it },
                fontFamily = fuentePixelBold
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                    checked = vibracionActiva.value,
                    onCheckedChange = { vibracionActiva.value = it },
                    modifier = Modifier.padding(start = 8.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White, // Color cuando está marcado
                        uncheckedColor = Color.Gray, // Color cuando no está marcado
                        checkmarkColor = Color.Black // Color del checkmark
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                        onClick = { expanded.value = true },
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Text(text = idiomaSeleccionado.value, fontFamily = fuentePixelBold)
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        idiomas.forEach { idioma ->
                            Text(
                                text = idioma,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        idiomaSeleccionado.value = idioma
                                        expanded.value = false
                                    }
                                    .padding(8.dp),
                                fontFamily = fuentePixelBold,
                                color = Color.White // Cambia el color del texto aquí
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.navigate(route = AppScreens.MainScreen.route) },
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
                        text = "Salir al menú",
                        fontFamily = fuentePixelBold,
                        fontSize = 20.sp
                    )

/*
                text?.let {
                    Text(
                        text = it,
                        fontFamily = fuentePixelBold,
                        fontSize = 20.sp
                    )
                }

 */

            }
        }
    }
}


@Composable
fun VolumeSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    fontFamily: FontFamily
) {
    Column {
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = fontFamily
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.Gray
            )
        )
    }
}