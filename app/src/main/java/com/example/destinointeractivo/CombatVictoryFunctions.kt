package com.example.destinointeractivo

import android.content.Context
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState // Asegúrate de tener esta importación
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.viewmodel.PlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Enum para los tipos de estadísticas que pueden ser aumentadas.
 */
enum class StatType {
    DAMAGE,
    DEFENSE,
    POTION,
    POTION_HEAL_AMOUNT
}

/**
 * Clase de datos para configurar una opción de recompensa en la pantalla de victoria.
 */
data class VictoryRewardOption(
    val buttonTextResId: Int, // R.string para el texto del botón
    val statIncreaseAmount: Int, // Cuánto sube la estadística
    val statType: StatType // Tipo de estadística afectada
)

/**
 * Clase de datos para encapsular toda la información necesaria para una pantalla de victoria.
 */
data class VictoryScreenData(
    val messageTextResId: Int, // R.string para el mensaje principal de victoria
    val nextLevelRoute: String, // La ruta de la pantalla a la que navegar después de elegir la recompensa
    val rewardOptions: List<VictoryRewardOption> // Lista de opciones de recompensa
)

/**
 * Maneja la lógica común al hacer clic en un botón de recompensa de victoria.
 *
 * @param navController NavController para la navegación.
 * @param vibrationViewModel ViewModel para la vibración del dispositivo.
 * @param playerViewModel ViewModel para la lógica del jugador.
 * @param context Contexto de la aplicación.
 * @param coroutineScope CoroutineScope para lanzar coroutines.
 * @param areButtonsEnabled Estado mutable para habilitar/deshabilitar botones.
 * @param rewardAnimationAmountText Estado mutable para el texto de la animación flotante.
 * @param showRewardAnimationState **MutableState<Boolean>** de la animación flotante (para el stat específico).
 * @param amount Cantidad a aumentar en la estadística.
 * @param statType Tipo de estadística a aumentar (DAMAGE, DEFENSE, POTION, POTION_HEAL_AMOUNT).
 * @param nextScreenRoute Ruta a la siguiente pantalla después de la recompensa.
 */
fun handleVictoryRewardButtonClick(
    navController: NavController,
    vibrationViewModel: VibrationViewModel,
    playerViewModel: PlayerViewModel,
    context: Context,
    coroutineScope: CoroutineScope,
    areButtonsEnabled: MutableState<Boolean>,
    rewardAnimationAmountText: MutableState<String>,
    showRewardAnimationState: MutableState<Boolean>,
    amount: Int,
    statType: StatType,
    nextScreenRoute: String
) {
    if (areButtonsEnabled.value) {
        areButtonsEnabled.value = false
        vibrationViewModel.vibrate(context)
        SoundPlayer.playSoundButton(context)

        coroutineScope.launch {
            // Actualiza la estadística del jugador
            when (statType) {
                StatType.DAMAGE -> playerViewModel.increasePlayerDamage(amount)
                StatType.DEFENSE -> playerViewModel.increasePlayerDefense(amount)
                StatType.POTION -> playerViewModel.increasePlayerPotions(amount)
                StatType.POTION_HEAL_AMOUNT -> playerViewModel.increasePlayerPotionHealAmount(amount)
            }

            // Prepara y activa la animación
            rewardAnimationAmountText.value = "+$amount"
            showRewardAnimationState.value = true

            delay(500)

            // Navegación
            playerViewModel.updateEnemyTurnCount(0)
            playerViewModel.updateLastLevel(nextScreenRoute)
            navController.navigate(nextScreenRoute)
        }
    }
}

/**
 * Composable genérico para mostrar una pantalla de victoria con opciones de recompensa.
 *
 * @param navController El NavController para la navegación.
 * @param navViewModel El NavViewModel para la gestión de la navegación.
 * @param victoryData Los datos específicos para configurar esta pantalla de victoria.
 */
@Composable
fun VictoryScreenLayout(
    navController: NavController,
    navViewModel: NavViewModel,
    victoryData: VictoryScreenData
) {
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel(factory = VibrationViewModelFactory(context))
    val playerViewModel: PlayerViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()


    val playerLanguage by playerViewModel.playerLanguage.collectAsState()

    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))

    val areButtonsEnabled = remember { mutableStateOf(true) }


    val showDamageRewardAnimation = remember { mutableStateOf(false) }
    val showShieldRewardAnimation = remember { mutableStateOf(false) }
    val showPotionRewardAnimation = remember { mutableStateOf(false) }
    val showHealRewardAnimation = remember { mutableStateOf(false) }

    val rewardAnimationAmountText = remember { mutableStateOf("") }


    DisposableEffect(Unit) {
        BackgroundMusicPlayer.playMusic(R.raw.music_win)
        onDispose { }
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
                            if (showDamageRewardAnimation.value) {
                                FloatingText(
                                    text = rewardAnimationAmountText.value,
                                    color = Color.Green,
                                    onAnimationEnd = { showDamageRewardAnimation.value = false }
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
                            if (showShieldRewardAnimation.value) {
                                FloatingText(
                                    text = rewardAnimationAmountText.value,
                                    color = Color.Green,
                                    onAnimationEnd = { showShieldRewardAnimation.value = false }
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
                            if (showPotionRewardAnimation.value) {
                                FloatingText(
                                    text = rewardAnimationAmountText.value,
                                    color = Color.Green,
                                    onAnimationEnd = { showPotionRewardAnimation.value = false }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(spacerIcons))
                        Box {
                            StatsStyle(
                                iconRes = R.drawable.healpotion,
                                text = "$playerPotionHealAmount",
                                size = 23.dp
                            )
                            if (showHealRewardAnimation.value) {
                                FloatingText(
                                    text = rewardAnimationAmountText.value,
                                    color = Color.Green,
                                    onAnimationEnd = { showHealRewardAnimation.value = false }
                                )
                            }
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
                        text = localizedString(victoryData.messageTextResId, playerLanguage),
                        fontFamily = fuentePixelBold,
                        fontSize = 30.sp,
                        color = Color.White,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    victoryData.rewardOptions.forEach { option ->
                        ButtonStyle(
                            text = localizedString(option.buttonTextResId, playerLanguage),
                            fontFamily = fuentePixelBold,
                            enabled = areButtonsEnabled.value,
                            onClick = {
                                handleVictoryRewardButtonClick(
                                    navController = navController,
                                    vibrationViewModel = vibrationViewModel,
                                    playerViewModel = playerViewModel,
                                    context = context,
                                    coroutineScope = coroutineScope,
                                    areButtonsEnabled = areButtonsEnabled,
                                    rewardAnimationAmountText = rewardAnimationAmountText,
                                    showRewardAnimationState = when (option.statType) {
                                        StatType.DAMAGE -> showDamageRewardAnimation
                                        StatType.DEFENSE -> showShieldRewardAnimation
                                        StatType.POTION -> showPotionRewardAnimation
                                        StatType.POTION_HEAL_AMOUNT -> showHealRewardAnimation
                                    },
                                    amount = option.statIncreaseAmount,
                                    statType = option.statType,
                                    nextScreenRoute = victoryData.nextLevelRoute
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