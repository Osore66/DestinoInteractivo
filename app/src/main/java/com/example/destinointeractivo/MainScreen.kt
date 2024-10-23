package com.example.destinointeractivo

import com.example.destinointeractivo.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val buttonShape = RoundedCornerShape(8.dp) // Ajusta el radio según tu preferencia

@Preview
@Composable
fun MainScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black) // Esto es temporal, el fondo se reemplaza por la imagen
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(R.drawable.destinointeractivo), // Cambia por tu imagen
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(), // Ocupar todo el espacio disponible
            contentScale = ContentScale.Crop // Escalar la imagen para llenar todo el espacio
        )

        // Columna con botones superpuestos en el centro
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center), // Centrar la columna
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centrar verticalmente
        ) {
            val buttonShape = RoundedCornerShape(4.dp) // Define la forma del botón
            val padding = 8.dp
            val tamanyofuente = 25.sp

            Button(
                onClick = { /* Acción de continuar */ },
                modifier = Modifier
                    .padding(padding)
                    .padding(top = 0.dp)
                    .fillMaxWidth(0.7f)
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
                    fontFamily = FontFamily(Font(R.font.pixelgeorgiabold)),
                    fontSize = tamanyofuente
                )
            }

            Button(
                onClick = { /* Acción de continuar */ },
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth(0.7f)
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
                    fontFamily = FontFamily(Font(R.font.pixelgeorgiabold)),
                    fontSize = tamanyofuente
                )
            }

            Button(
                onClick = { /* Acción de continuar */ },
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth(0.7f)
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
                    fontFamily = FontFamily(Font(R.font.pixelgeorgiabold)),
                    fontSize = tamanyofuente
                )
            }
        }
    }
}

