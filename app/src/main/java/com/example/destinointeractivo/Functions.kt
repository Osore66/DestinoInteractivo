package com.example.destinointeractivo

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.destinointeractivo.navigation.AppScreens

val tamanyoFuenteCombate = 20.sp


@Composable
fun BarraEstado(
    vibrationViewModel: VibrationViewModel,
    navController: NavController,
    context: Context,
) {
    val spacerIcons = 14.dp

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.VeryDarkGrey))
                .padding(start = 16.dp, end = 16.dp, top = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                StatsStyle(
                    iconRes = R.drawable.corazon,
                    text = "15/15",
                    size = 25.dp
                ) // Ancho fijo para "15/15"
                Spacer(modifier = Modifier.width(spacerIcons))
                StatsStyle(iconRes = R.drawable.espada, text = "3", size = 23.dp)
                Spacer(modifier = Modifier.width(spacerIcons))
                StatsStyle(iconRes = R.drawable.escudo, text = "1", size = 30.dp)
                Spacer(modifier = Modifier.width(spacerIcons))
                StatsStyle(iconRes = R.drawable.potion, text = "2", size = 23.dp)
            }
            IconButton(onClick = {
                vibrationViewModel.vibrate(context)
                navController.navigate(route = AppScreens.Ajustes.route)
            }) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.settings_icon),
                    contentDescription = "Settings",
                    tint = Color.White,
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun StatsStyle(iconRes: Int, text: String, size: Dp = 24.dp, isFixedWidth: Boolean = false) {
    Row(
        //el modifier es para que uno o varios iconos (junto a su texto) ocupe una medida fija poniendole isFixedWidth = true, pero al final he decidio no usarlo.
        //modifier = if (isFixedWidth) Modifier.width(120.dp) else Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(size)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

//Pantalla del enemigo
@Composable
fun EnemigoImagenyFondo(imgFondo: Int, imgEnemigo: Int? ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        // Fondo
        Image(
            painter = painterResource(imgFondo),
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
            // Imagen del enemigo (condicional)
            if (imgEnemigo != null) { // Comprueba si se proporciona imgEnemigo
                Image(
                    painter = painterResource(imgEnemigo),
                    contentDescription = "Slime",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                //Vacío para que no haga nada
            }
        }
    }
}

//Botones
@Composable
fun ButtonSection(
    vibrationViewModel: VibrationViewModel,
    fuentePixelBold: FontFamily,
    context: Context,
    isAttackButtonEnabled: Boolean // Recibimos el estado del botón Atacar
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        ButtonStyle(text = "Atacar", fontFamily = fuentePixelBold, enabled = isAttackButtonEnabled) {
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

@Composable
fun ButtonStyle(text: String, fontFamily: FontFamily, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.DarkGray
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



@Composable
fun TextandButton(
    vibrationViewModel: VibrationViewModel,
    fuentePixelBold: FontFamily,
    context: Context,
    customText: String,
    isAttackButtonEnabled: Boolean // Recibimos el estado del botón Atacar
) {
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
                text = customText,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = fuentePixelBold,
                textAlign = TextAlign.Center
            )
        }

        // Botones de acción (Se quedan en la parte inferior)
        ButtonSection(
            vibrationViewModel = vibrationViewModel,
            fuentePixelBold = fuentePixelBold,
            context = context,
            isAttackButtonEnabled = isAttackButtonEnabled // Pasamos el estado a ButtonSection
        )
    }
}