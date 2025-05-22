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
import com.example.destinointeractivo.BackgroundMusicPlayer
import com.example.destinointeractivo.ButtonStyle
import com.example.destinointeractivo.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.SoundPlayer
import com.example.destinointeractivo.StatsStyle
import com.example.destinointeractivo.VibrationViewModel
import com.example.destinointeractivo.localizedString
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.viewmodel.EnemyViewModel
import com.example.destinointeractivo.viewmodel.PlayerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SinMetodos_Combate(
    navController: NavController,
    navViewModel: NavViewModel,
) {
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel { VibrationViewModel(context) }
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))

    navViewModel.lastScreen.value = AppScreens.SinMetodos_Combate.route

    val enemyViewModel: EnemyViewModel = viewModel()
    val playerViewModel: PlayerViewModel = viewModel()

    val enemyId = 1

    DisposableEffect(Unit) {
        // Reproducir la música de combate al entrar a esta pantalla
        BackgroundMusicPlayer.playMusic(R.raw.music_battlemusic_01)
        onDispose {
            // No hacemos nada aquí. La música seguirá sonando si el usuario navega a otra pantalla.
            // La pausa global la maneja BackgroundMusicPlayer cuando la app se va a segundo plano.
        }
    }

    LaunchedEffect(enemyId) {
        enemyViewModel.loadEnemyData(enemyId)
        playerViewModel.loadPlayerData()

    }

    // Observar datos del enemigo
    val currentEnemyLife by enemyViewModel.currentEnemyLife.collectAsState(initial = 0)
    val maxEnemyLife by enemyViewModel.maxEnemyLife.collectAsState(initial = 0)
    val enemyDamage by enemyViewModel.enemyDamage.collectAsState(initial = 0)
    val enemyDefense by enemyViewModel.enemyDefense.collectAsState(initial = 0)
    val enemyCritFreq by enemyViewModel.enemyCritFreq.collectAsState(initial = 0)

    // Observar datos del jugador
    val playerLife by playerViewModel.playerLife.collectAsState(initial = 0)
    val playerDamage by playerViewModel.playerDamage.collectAsState(initial = 0)
    val playerDefense by playerViewModel.playerDefense.collectAsState(initial = 0)
    val playerPotions by playerViewModel.playerPotions.collectAsState(initial = 0)
    val playerMaxLife by playerViewModel.playerMaxLife.collectAsState(initial = 0)
    val playerPotionHealAmount by playerViewModel.playerPotionHealAmount.collectAsState(initial = 0)
    val enemyTurnCount by playerViewModel.enemyTurnCount.collectAsState(initial = 0)

    val coroutineScope = rememberCoroutineScope()
    //var enemyTurnCount by remember { mutableIntStateOf(0) }
    var areButtonsEnabled by remember { mutableStateOf(true) } // Cooldown global
    val playerLanguage by playerViewModel.playerLanguage.collectAsState()


    fun onEnemyAttack() {
        if (currentEnemyLife > 0) {
            // Incrementar desde el ViewModel
            val currentTurnCount = playerViewModel.enemyTurnCount.value + 1
            playerViewModel.updateEnemyTurnCount(currentTurnCount)

            val isCritical = enemyCritFreq > 0 && (currentTurnCount % enemyCritFreq == 0)
            var damageToPlayer = enemyDamage - playerDefense

            if (isCritical) {
                damageToPlayer *= 2
            }

            val newPlayerLife = (playerLife - damageToPlayer).coerceAtLeast(0)
            playerViewModel.updatePlayerLife(newPlayerLife)

            // Vibración simple o doble
            if (!isCritical) {
                vibrationViewModel.vibrate(context)
            } else {
                coroutineScope.launch {
                    vibrationViewModel.vibrate(context)
                    delay(100)
                    vibrationViewModel.vibrate(context)
                }
            }

            // Comprobamos si el jugador ha sido derrotado
            if (newPlayerLife <= 0) {
                // Actualiza lastLevel a "DerrotaScreen"
                playerViewModel.updateLastLevel(AppScreens.DerrotaScreen.route)
                // Navegar a DerrotaScreen si has perdido
                navController.navigate(route = AppScreens.DerrotaScreen.route)
            }
        }
    }

    fun onPlayerAttack() {
        if (currentEnemyLife > 0 && areButtonsEnabled) {
            areButtonsEnabled = false
            val damageToEnemy = playerDamage - enemyDefense
            val newEnemyLife = (currentEnemyLife - damageToEnemy).coerceAtLeast(0)
            enemyViewModel.updateEnemyLife(enemyId, newEnemyLife)

            coroutineScope.launch {
                delay(300)
                if (newEnemyLife <= 0) {
                    // Navegar a VictoriaScreen
                    playerViewModel.updateEnemyTurnCount(0)

                    // Actualiza lastLevel a "VictoriaScreen"
                    playerViewModel.updateLastLevel(AppScreens.VictoriaScreen.route)

                    // Navega a VictoriaScreen
                    navController.navigate(route = AppScreens.VictoriaScreen.route)
                } else {
                    onEnemyAttack()
                }
                delay(300) // Total 600ms
                areButtonsEnabled = true
            }
        }
    }

    fun onDefend() {
        if (areButtonsEnabled) {
            areButtonsEnabled = false
            coroutineScope.launch {
                val currentTurnCount = playerViewModel.enemyTurnCount.value + 1
                playerViewModel.updateEnemyTurnCount(currentTurnCount)
                delay(600) // Cooldown global
                areButtonsEnabled = true
            }
        }
    }

    fun onUsePotion() {
        if (playerPotions > 0 && areButtonsEnabled) {
            areButtonsEnabled = false
            val newPotions = playerPotions - 1
            val newPlayerLife = (playerLife + playerPotionHealAmount).coerceAtMost(playerMaxLife)
            playerViewModel.updatePlayerLife(newPlayerLife)
            playerViewModel.updatePlayerPotions(newPotions)

            coroutineScope.launch {
                delay(300)
                onEnemyAttack()
                delay(300) // Total 600ms
                areButtonsEnabled = true
            }
        }
    }

    // Estructura principal de la pantalla
    Scaffold(
        topBar = {
            val spacerIcons = 4.dp
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
                            text = "$playerLife/$playerMaxLife",
                            size = 25.dp
                        )
                        Spacer(modifier = Modifier.width(spacerIcons))
                        StatsStyle(iconRes = R.drawable.espada, text = "$playerDamage", size = 23.dp)
                        Spacer(modifier = Modifier.width(spacerIcons))
                        StatsStyle(iconRes = R.drawable.escudo, text = "$playerDefense", size = 30.dp)
                        Spacer(modifier = Modifier.width(spacerIcons))
                        //Cantidad pociones
                        StatsStyle(iconRes = R.drawable.potion, text = "$playerPotions", size = 23.dp)
                        Spacer(modifier = Modifier.width(spacerIcons))
                        //Cantidad curación
                        StatsStyle(iconRes = R.drawable.healpotion, text = "$playerPotionHealAmount", size = 23.dp)
                    }
                    IconButton(onClick = {
                        vibrationViewModel.vibrate(context)
                        SoundPlayer.playSoundButton(context)
                        navController.navigate(route = AppScreens.Ajustes.route)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings_icon),
                            contentDescription = "Cerrar",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        },
        content = { innerPadding ->
            BackHandler { }
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.enemigofondo1),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
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
                                    StatsStyle(
                                        iconRes = R.drawable.corazon,
                                        text = "$currentEnemyLife/$maxEnemyLife",
                                        size = 25.dp
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    StatsStyle(iconRes = R.drawable.espada, text = "$enemyDamage", size = 23.dp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    StatsStyle(iconRes = R.drawable.escudo, text = "$enemyDefense", size = 30.dp)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            /*
                            Image(
                                painter = painterResource(id = R.drawable.slime),
                                contentDescription = "Slime",
                                modifier = Modifier
                                    .size(80.dp)
                                    .align(Alignment.CenterHorizontally)
                            )

                             */

                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = localizedString(R.string.ES_001_combate, playerLanguage),
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = fuentePixelBold,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp)
                        ) {
                            ButtonStyle(text = localizedString(R.string.combate_atacar, playerLanguage), fontFamily = fuentePixelBold, enabled = areButtonsEnabled, onClick = {
                                vibrationViewModel.vibrate(context)
                                SoundPlayer.playSoundButton(context) // 🔊 Sonido del botón
                                onPlayerAttack()
                            })
                            Spacer(modifier = Modifier.height(8.dp))
                            ButtonStyle(text = localizedString(R.string.combate_defender, playerLanguage), fontFamily = fuentePixelBold, enabled = areButtonsEnabled, onClick = {
                                vibrationViewModel.vibrate(context)
                                SoundPlayer.playSoundButton(context) // 🔊 Sonido del botón
                                onDefend()
                            })
                            Spacer(modifier = Modifier.height(8.dp))
                            ButtonStyle(text = localizedString(R.string.combate_pocion, playerLanguage), fontFamily = fuentePixelBold, enabled = areButtonsEnabled, onClick = {
                                vibrationViewModel.vibrate(context)
                                SoundPlayer.playSoundButton(context) // 🔊 Sonido del botón
                                onUsePotion()
                            })
                        }
                    }
                }
            }
        }
    )
}

