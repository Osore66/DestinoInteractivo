package com.example.destinointeractivo.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.destinointeractivo.sound.BackgroundMusicPlayer
import com.example.destinointeractivo.viewmodel.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.sound.SoundPlayer
import com.example.destinointeractivo.viewmodel.VibrationViewModel
import com.example.destinointeractivo.viewmodel.VibrationViewModelFactory
import com.example.destinointeractivo.functions.localizedString
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.viewmodel.EnemyViewModel
import com.example.destinointeractivo.viewmodel.PlayerViewModel
import kotlin.math.round

@Composable
fun Ajustes(navController: NavController, navViewModel: NavViewModel) {

    BackHandler { /* Evita retroceso */ }
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel(
        factory = VibrationViewModelFactory(context)
    )
    val playerViewModel: PlayerViewModel = viewModel()

    val effectsVolumeDB by playerViewModel.playerEffectsVolume.collectAsState(initial = 10)
    val musicVolumeDB by playerViewModel.playerMusicVolume.collectAsState(initial = 10)

    var volumenEfectos by remember { mutableStateOf(effectsVolumeDB.toFloat()) }
    var volumenMusica by remember { mutableStateOf(musicVolumeDB.toFloat()) }

    val expanded = remember { mutableStateOf(false) }
    val tamanyoFuenteAjustes = 25.sp
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))
    val buttonShape = RoundedCornerShape(4.dp)
    val enemyViewModel: EnemyViewModel = viewModel()
    val playerLanguage by playerViewModel.playerLanguage.collectAsState()
    val idiomas = listOf("Español", "English")
    val idiomaSeleccionado = remember { mutableStateOf(idiomas[0]) }
    val refreshKey = remember { mutableStateOf(0) }

    LaunchedEffect(effectsVolumeDB, musicVolumeDB) {
        if (volumenEfectos.toInt() != effectsVolumeDB) {
            volumenEfectos = effectsVolumeDB.toFloat()
        }
        if (volumenMusica.toInt() != musicVolumeDB) {
            volumenMusica = musicVolumeDB.toFloat()
        }
        SoundPlayer.setEffectsVolume(effectsVolumeDB.toFloat())
        BackgroundMusicPlayer.setMusicVolume(musicVolumeDB)
    }

    DisposableEffect(Unit) {
        BackgroundMusicPlayer.resumeMusic()
        onDispose {
            // No hacemos nada aquí.
        }
    }

    fun forceRecomposition() {
        refreshKey.value = refreshKey.value + 1
    }

    LaunchedEffect(playerLanguage) {
        idiomaSeleccionado.value = if (playerLanguage == "es") "Español" else "English"
    }

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
                    text = localizedString(R.string.btn_ajustes, playerLanguage),
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                IconButton(onClick = {
                    vibrationViewModel.vibrate(context)
                    SoundPlayer.playSoundButton(context)
                    navController.navigate(navViewModel.lastScreen.value) {
                        popUpTo(AppScreens.Ajustes.route) { inclusive = true }
                    }
                }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.cerrar),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // SLIDER DE VOLUMEN DE EFECTOS
            VolumeSlider(
                label = localizedString(R.string.ajustes_volumen_efectos, playerLanguage),
                value = volumenEfectos,
                onValueChange = { newValue ->
                    // Redondea el valor y conviértelo a Float para el estado del slider
                    val newRoundedFloat = round(newValue)
                    val newRoundedInt = newRoundedFloat.toInt()

                    // Solo si el valor entero ha cambiado, ejecuta vibración, sonido y persiste
                    if (newRoundedInt != volumenEfectos.toInt()) {
                        vibrationViewModel.vibrate(context)
                        SoundPlayer.playSoundButton(context)
                        playerViewModel.updatePlayerEffectsVolume(newRoundedInt)
                    }
                    // Actualiza el estado local del slider con el valor entero redondeado
                    volumenEfectos = newRoundedFloat
                    // Actualiza el reproductor de sonido inmediatamente (pasa Float o Int según SoundPlayer)
                    SoundPlayer.setEffectsVolume(newRoundedFloat)
                },
                onValueChangeFinished = { /* Mantén vacío, la lógica está en onValueChange */ },
                fontFamily = fuentePixelBold
            )

            // SLIDER DE VOLUMEN DE MÚSICA
            VolumeSlider(
                label = localizedString(R.string.ajustes_volumen_musica, playerLanguage),
                value = volumenMusica,
                onValueChange = { newValue ->
                    // Redondea el valor y conviértelo a Float para el estado del slider
                    val newRoundedFloat = round(newValue)
                    val newRoundedInt = newRoundedFloat.toInt()

                    // Solo si el valor entero ha cambiado, ejecuta vibración, sonido y persiste
                    if (newRoundedInt != volumenMusica.toInt()) {
                        vibrationViewModel.vibrate(context)
                        SoundPlayer.playSoundButton(context)
                        playerViewModel.updatePlayerMusicVolume(newRoundedInt)
                    }
                    // Actualiza el estado local del slider con el valor entero redondeado
                    volumenMusica = newRoundedFloat
                    // Actualiza el reproductor de música inmediatamente (pasa Int)
                    BackgroundMusicPlayer.setMusicVolume(newRoundedInt)
                },
                onValueChangeFinished = { /* Mantén vacío, la lógica está en onValueChange */ },
                fontFamily = fuentePixelBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox para vibración
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = localizedString(R.string.ajustes_vibracion, playerLanguage),
                    color = Color.White,
                    fontSize = tamanyoFuenteAjustes,
                    fontFamily = fuentePixelBold
                )
                Checkbox(
                    checked = vibrationViewModel.vibracionActiva.value,
                    onCheckedChange = { isChecked ->
                        vibrationViewModel.setVibracionActiva(isChecked)
                        vibrationViewModel.vibrate(context)
                        SoundPlayer.playSoundButton(context)
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = localizedString(R.string.ajustes_idioma, playerLanguage),
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

                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        idiomas.forEach { idioma ->
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = {
                                    Text(
                                        text = localizedString(
                                            if (idioma == "Español") R.string.ajustes_idioma_espanol else R.string.ajustes_idioma_ingles,
                                            playerLanguage
                                        ),
                                        fontFamily = fuentePixelBold,
                                        color = Color.White,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                },
                                onClick = {
                                    vibrationViewModel.vibrate(context)
                                    SoundPlayer.playSoundButton(context)
                                    val newLanguage = if (idioma == "Español") "es" else "en"
                                    playerViewModel.updateAppLanguage(context, newLanguage)
                                    forceRecomposition()
                                    idiomaSeleccionado.value = idioma
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Botón para salir al menú
            Button(
                onClick = {
                    vibrationViewModel.vibrate(context)
                    SoundPlayer.playSoundButton(context)
                    navController.navigate(route = AppScreens.MainScreen.route) {
                        popUpTo(AppScreens.MainScreen.route) { inclusive = false }
                    }
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
                    text = localizedString(R.string.ajustes_salir, playerLanguage),
                    fontFamily = fuentePixelBold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun VolumeSlider(
    label: String,
    value: Float, // Este es el valor del slider (0f-10f, siempre un entero)
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (() -> Unit)?, // Puede ser null
    fontFamily: FontFamily
) {
    Column {
        Text(
            text = "$label: ${value.toInt()}", // Mostrar el valor redondeado como Int
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = fontFamily
        )
        Slider(
            value = value, // El Slider trabajará con el Float entero
            onValueChange = onValueChange,
            onValueChangeFinished = onValueChangeFinished, // Puede ser null
            valueRange = 0f..10f,
            steps = 9, // Para 0-10, 9 pasos dan 10 segmentos (0, 1, ..., 10)
            colors = SliderDefaults.colors(
                thumbColor = if (value == 0f) Color.Gray else Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.Gray
            )
        )
    }
}