package com.example.destinointeractivo.screens

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.destinointeractivo.R
import com.example.destinointeractivo.animations.FloatingText
import com.example.destinointeractivo.functions.ButtonStyle
import com.example.destinointeractivo.functions.StatsStyle
import com.example.destinointeractivo.functions.localizedString
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.sound.BackgroundMusicPlayer
import com.example.destinointeractivo.sound.SoundPlayer
import com.example.destinointeractivo.viewmodel.NavViewModel
import com.example.destinointeractivo.viewmodel.PlayerViewModel
import com.example.destinointeractivo.viewmodel.VibrationViewModel
import com.example.destinointeractivo.viewmodel.VibrationViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Data Classes para la Selección de Armas

// Clase de datos para configurar una opción de arma.

data class WeaponOption(
    val buttonTextResId: Int,
    val damageIncrease: Int,
    val shieldIncrease: Int
)

// Clase de datos para encapsular toda la información necesaria para una pantalla de selección de arma.

data class WeaponSelectionScreenData(
    val messageTextResId: Int, // R.string para el mensaje principal
    val nextLevelRoute: String, // La ruta de la pantalla a la que navegar después de elegir el arma
    val weaponOptions: List<WeaponOption> // Lista de opciones de armas
)

// Función de manejo de botones
fun handleWeaponSelectionButtonClick(
    navController: NavController,
    vibrationViewModel: VibrationViewModel,
    playerViewModel: PlayerViewModel,
    context: Context,
    coroutineScope: CoroutineScope,
    areButtonsEnabled: MutableState<Boolean>,
    rewardAnimationAmountText: MutableState<String>,
    showDamageAnimationState: MutableState<Boolean>,
    showShieldAnimationState: MutableState<Boolean>,
    damageIncrease: Int,
    shieldIncrease: Int,
    nextScreenRoute: String,
    navViewModel: NavViewModel,
    currentScreenRoute: String // *** AÑADIMOS ESTE PARÁMETRO ***
) {
    if (areButtonsEnabled.value) {
        areButtonsEnabled.value = false
        vibrationViewModel.vibrate(context)
        SoundPlayer.playSoundButton(context)

        coroutineScope.launch {
            if (damageIncrease > 0) {
                playerViewModel.increasePlayerDamage(damageIncrease)
                rewardAnimationAmountText.value = "+$damageIncrease"
                showDamageAnimationState.value = true
                delay(240) // Pequeño delay para que se vea la primera animación si hay dos
                showDamageAnimationState.value = false // Oculta la primera animación
            }

            if (shieldIncrease > 0) {
                playerViewModel.increasePlayerDefense(shieldIncrease)
                rewardAnimationAmountText.value = "+$shieldIncrease"
                showShieldAnimationState.value = true
                delay(240) // Pequeño delay para que se vea la segunda animación
                showShieldAnimationState.value = false // Oculta la segunda animación
            }

            delay(300) // Espera un poco más para que las animaciones terminen si hubo varias

            // *** ELIMINAMOS O MODIFICAMOS ESTA LÍNEA, LA MÚSICA NO ES EL PROBLEMA PRINCIPAL ***
            // navViewModel.lastScreen.value = AppScreens.Combat_001.route // Ya la actualizaremos con updateLastLevel

            playerViewModel.updateEnemyTurnCount(0)
            playerViewModel.updateLastLevel(nextScreenRoute) // Esto guarda la ruta del *siguiente* nivel

            delay(200)

            // *** CAMBIO CLAVE AQUÍ: popUpTo para eliminar WeaponSelectionScreen ***
            navController.navigate(nextScreenRoute) {
                // currentScreenRoute es la ruta de la pantalla WeaponSelectionScreen
                popUpTo(currentScreenRoute) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}

@Composable
fun WeaponSelectionScreen(
    navController: NavController,
    navViewModel: NavViewModel
) {
    // Definimos la ruta de esta pantalla aquí
    val THIS_SCREEN_ROUTE = AppScreens.WeaponSelectionScreen.route

    BackHandler { /* Evita retroceso */ }

    // Actualiza lastScreen cuando esta pantalla se compone
    // y asegúrate de que se limpie la música al salir
    DisposableEffect(Unit) {
        navViewModel.lastScreen.value = THIS_SCREEN_ROUTE // Guardamos la ruta de esta pantalla
        onDispose {
        }
    }

    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel(factory = VibrationViewModelFactory(context))
    val playerViewModel: PlayerViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    val playerLanguage by playerViewModel.playerLanguage.collectAsState()

    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))

    val areButtonsEnabled = remember { mutableStateOf(true) }

    val showDamageAnimation = remember { mutableStateOf(false) }
    val showShieldAnimation = remember { mutableStateOf(false) }

    val rewardAnimationAmountText = remember { mutableStateOf("") }


    // Define los datos específicos para esta pantalla de selección de arma
    val weaponSelectionData = remember {
        WeaponSelectionScreenData(
            messageTextResId = R.string.weapon_selection_message,
            nextLevelRoute = AppScreens.Combat_001.route, // Por ejemplo, el primer combate
            weaponOptions = listOf(
                WeaponOption(R.string.weapon_mandoble, 2, 0),
                WeaponOption(R.string.weapon_espada_escudo, 1, 1),
                WeaponOption(R.string.weapon_escudo_pesado, 0, 2)
            )
        )
    }

    Scaffold(
        topBar = {
            val spacerIcons = 4.dp

            val playerLife by playerViewModel.playerLife.collectAsState(initial = 0)
            val playerMaxLife by playerViewModel.playerMaxLife.collectAsState(initial = 0)
            val playerDamage by playerViewModel.playerDamage.collectAsState(initial = 0)
            val playerDefense by playerViewModel.playerDefense.collectAsState(initial = 0)
            val playerPotions by playerViewModel.playerPotions.collectAsState(initial = 0)
            val playerPotionHealAmount by playerViewModel.playerPotionHealAmount.collectAsState(initial = 0)

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
                        Box {
                            StatsStyle(
                                iconRes = R.drawable.corazon,
                                text = "$playerLife/$playerMaxLife",
                                size = 22.dp
                            )
                        }
                        Spacer(modifier = Modifier.width(spacerIcons))
                        Box {
                            StatsStyle(
                                iconRes = R.drawable.espada,
                                text = "$playerDamage",
                                size = 23.dp
                            )
                            if (showDamageAnimation.value) {
                                FloatingText(
                                    text = rewardAnimationAmountText.value,
                                    color = Color.Green,
                                    onAnimationEnd = { showDamageAnimation.value = false }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(spacerIcons))
                        Box {
                            StatsStyle(
                                iconRes = R.drawable.escudo,
                                text = "$playerDefense",
                                size = 30.dp
                            )
                            if (showShieldAnimation.value) {
                                FloatingText(
                                    text = rewardAnimationAmountText.value,
                                    color = Color.Green,
                                    onAnimationEnd = { showShieldAnimation.value = false }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(spacerIcons))
                        Box {
                            StatsStyle(
                                iconRes = R.drawable.potion,
                                text = "$playerPotions",
                                size = 23.dp
                            )
                        }
                        Spacer(modifier = Modifier.width(spacerIcons))
                        Box {
                            StatsStyle(
                                iconRes = R.drawable.healpotion,
                                text = "$playerPotionHealAmount",
                                size = 23.dp
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            vibrationViewModel.vibrate(context)
                            SoundPlayer.playSoundButton(context)
                            navController.navigate(route = AppScreens.Ajustes.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings_icon),
                            contentDescription = "Settings",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.VeryDarkGrey))
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = localizedString(weaponSelectionData.messageTextResId, playerLanguage),
                        fontFamily = fuentePixelBold,
                        fontSize = 24.sp,
                        color = Color.White,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    weaponSelectionData.weaponOptions.forEach { option ->
                        ButtonStyle(
                            text = localizedString(option.buttonTextResId, playerLanguage),
                            fontFamily = fuentePixelBold,
                            enabled = areButtonsEnabled.value,
                            onClick = {
                                handleWeaponSelectionButtonClick(
                                    navController = navController,
                                    vibrationViewModel = vibrationViewModel,
                                    playerViewModel = playerViewModel,
                                    context = context,
                                    coroutineScope = coroutineScope,
                                    areButtonsEnabled = areButtonsEnabled,
                                    rewardAnimationAmountText = rewardAnimationAmountText,
                                    showDamageAnimationState = showDamageAnimation,
                                    showShieldAnimationState = showShieldAnimation,
                                    damageIncrease = option.damageIncrease,
                                    shieldIncrease = option.shieldIncrease,
                                    nextScreenRoute = weaponSelectionData.nextLevelRoute,
                                    navViewModel = navViewModel,
                                    currentScreenRoute = THIS_SCREEN_ROUTE // *** PASAMOS ESTA RUTA ***
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    )
}