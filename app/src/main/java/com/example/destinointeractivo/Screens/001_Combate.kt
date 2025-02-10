package com.example.destinointeractivo.Screens

import android.content.Context
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.destinointeractivo.BarraEstado
import com.example.destinointeractivo.ButtonSection
import com.example.destinointeractivo.EnemigoImagenyFondo
import com.example.destinointeractivo.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.StatsStyle
import com.example.destinointeractivo.VibrationViewModel
import com.example.destinointeractivo.navigation.AppScreens


@Composable
fun Combate_001(
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
                    EnemigoImagenyFondo(R.drawable.paisaje, R.drawable.slime /* imgEnemigo = null */ )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Texto de combate y botones de acción
                    TextandButton(
                        vibrationViewModel = vibrationViewModel,
                        fuentePixelBold = fuentePixelBold,
                        context = context,
                        //Texto descriptivo
                        customText = stringResource(id = R.string.ES_001_combate)
                    )
                }
            }
        }
    )
}


