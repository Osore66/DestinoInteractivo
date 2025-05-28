package com.example.destinointeractivo.functions

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.destinointeractivo.sound.BackgroundMusicPlayer
import com.example.destinointeractivo.animations.FloatingImage
import com.example.destinointeractivo.animations.FloatingText
import com.example.destinointeractivo.viewmodel.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.sound.SoundPlayer
import com.example.destinointeractivo.viewmodel.VibrationViewModel
import com.example.destinointeractivo.navigation.AppScreens
import com.example.destinointeractivo.viewmodel.EnemyViewModel
import com.example.destinointeractivo.viewmodel.PlayerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// --- Data class para encapsular los datos de combate ---
data class CombatScreenData(
    val enemyId: Int,
    val backgroundMusicResId: Int,
    val backgroundPainterResId: Int,
    val enemyPainterResId: Int? = null,
    val enemyImageSize: Dp? = null,
    val startDialogueResId: Int? = null,
    // --- Parámetros de navegación ---
    val currentScreenRoute: String, // La ruta de la pantalla de combate actual
    val nextScreenRouteOnWin: String,
    val settingsScreenRoute: String = AppScreens.Ajustes.route
)
@Composable
fun PlayerStatusBar(
    navController: NavController,
    vibrationViewModel: VibrationViewModel,
    playerViewModel: PlayerViewModel = viewModel(), // Permite inyectar o usar el ViewModel por defecto
    context: Context = LocalContext.current // Permite inyectar el contexto o usar el local
) {
    val spacerIcons = 4.dp

    // Observar datos del jugador desde el ViewModel
    val playerLife by playerViewModel.playerLife.collectAsState(initial = 0)
    val playerDamage by playerViewModel.playerDamage.collectAsState(initial = 0)
    val playerDefense by playerViewModel.playerDefense.collectAsState(initial = 0)
    val playerPotions by playerViewModel.playerPotions.collectAsState(initial = 0)
    val playerMaxLife by playerViewModel.playerMaxLife.collectAsState(initial = 0)
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
            // Stats del jugador (vida, defensa, pociones)
            Row(verticalAlignment = Alignment.CenterVertically) {
                StatsStyle(
                    iconRes = R.drawable.corazon,
                    text = "$playerLife/$playerMaxLife",
                    size = 22.dp
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

            // Botón de ajustes (engranaje)
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

        // Espaciado inferior
        Spacer(modifier = Modifier.height(10.dp))
    }
}
@Composable
fun CombatScreenLayout(
    navController: NavController,
    navViewModel: NavViewModel,
    combatData: CombatScreenData
) {
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel { VibrationViewModel(context) }
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))

    val enemyViewModel: EnemyViewModel = viewModel()
    val playerViewModel: PlayerViewModel = viewModel()

    // No modificamos la música aquí
    DisposableEffect(Unit) {
        BackgroundMusicPlayer.playMusic(combatData.backgroundMusicResId)
        onDispose { }
    }

    // Actualiza lastScreen cuando esta pantalla se compone
    DisposableEffect(Unit) {
        navViewModel.lastScreen.value = combatData.currentScreenRoute // Guardamos la ruta de esta pantalla de combate
        onDispose { }
    }

    LaunchedEffect(combatData.enemyId) {
        enemyViewModel.loadEnemyData(combatData.enemyId)
        playerViewModel.loadPlayerData()
    }


    val currentEnemyLife by enemyViewModel.currentEnemyLife.collectAsState(initial = 0)
    val maxEnemyLife by enemyViewModel.maxEnemyLife.collectAsState(initial = 0)
    val enemyDamage by enemyViewModel.enemyDamage.collectAsState(initial = 0)
    val enemyDefense by enemyViewModel.enemyDefense.collectAsState(initial = 0)
    val enemyCritFreq by enemyViewModel.enemyCritFreq.collectAsState(initial = 0)

    val playerLife by playerViewModel.playerLife.collectAsState(initial = 0)
    val playerDamage by playerViewModel.playerDamage.collectAsState(initial = 0)
    val playerDefense by playerViewModel.playerDefense.collectAsState(initial = 0)
    val playerPotions by playerViewModel.playerPotions.collectAsState(initial = 0)
    val playerMaxLife by playerViewModel.playerMaxLife.collectAsState(initial = 0)
    val playerPotionHealAmount by playerViewModel.playerPotionHealAmount.collectAsState(initial = 0)
    val enemyTurnCount by playerViewModel.enemyTurnCount.collectAsState(initial = 0)

    val coroutineScope = rememberCoroutineScope()
    var areButtonsEnabled by remember { mutableStateOf(true) }
    val playerLanguage by playerViewModel.playerLanguage.collectAsState()

    var showPlayerDamageAnimation by remember { mutableStateOf(false) }
    var playerDamageText by remember { mutableStateOf("") }
    var playerDamageColor by remember { mutableStateOf(Color.Red) }

    var showEnemyDamageAnimation by remember { mutableStateOf(false) }
    var enemyDamageText by remember { mutableStateOf("") }
    var enemyDamageColor by remember { mutableStateOf(Color.Red) }

    var showPlayerHealAnimation by remember { mutableStateOf(false) }
    var playerHealText by remember { mutableStateOf("") }

    var showPlayerDefenseTextAnimation by remember { mutableStateOf(false) }
    var playerDefenseText by remember { mutableStateOf("") }

    var showPlayerPotionsAnimation by remember { mutableStateOf(false) }
    var playerPotionsText by remember { mutableStateOf("") }
    var playerPotionsTextColor by remember { mutableStateOf(Color.Green) }

    var showPlayerDamageStatAnimation by remember { mutableStateOf(false) }
    var playerDamageStatText by remember { mutableStateOf("") }
    var playerDamageStatColor by remember { mutableStateOf(Color.Green) }

    var showPlayerDefenseStatAnimation by remember { mutableStateOf(false) }
    var playerDefenseStatText by remember { mutableStateOf("") }
    var playerDefenseStatColor by remember { mutableStateOf(Color.Green) }

    var showPlayerPotionHealStatAnimation by remember { mutableStateOf(false) }
    var playerPotionHealStatText by remember { mutableStateOf("") }
    var playerPotionHealStatColor by remember { mutableStateOf(Color.Green) }

    var showDefenseImageAnimation by remember { mutableStateOf(false) }
    var isDefenseCritical by remember { mutableStateOf(false) }


    fun onEnemyAttackInternal() {
        if (currentEnemyLife > 0) {
            val currentTurnCount = playerViewModel.enemyTurnCount.value + 1
            playerViewModel.updateEnemyTurnCount(currentTurnCount)

            val isCritical = enemyCritFreq > 0 && (currentTurnCount % enemyCritFreq == 0)

            // Aplicar crítico antes de la defensa ---
            var baseDamage = enemyDamage // Daño base del enemigo
            if (isCritical) {
                baseDamage *= 2 // Aplica el multiplicador crítico al daño base
            }
            var damageToPlayer = baseDamage - playerDefense // Resta la defensa después del crítico

            if (damageToPlayer < 0) damageToPlayer = 0 // Asegura que el daño no sea negativo


            val newPlayerLife = (playerLife - damageToPlayer).coerceAtLeast(0)
            playerViewModel.updatePlayerLife(newPlayerLife)

            playerDamageText = "-$damageToPlayer"
            playerDamageColor = if (isCritical) Color.Yellow else Color.Red
            showPlayerDamageAnimation = true

            if (!isCritical) {
                SoundPlayer.playSoundHit(context)
                vibrationViewModel.vibrate(context)
            } else {
                coroutineScope.launch {
                    SoundPlayer.playSoundHitCrit(context)
                    vibrationViewModel.vibrate(context)
                }
            }

            if (newPlayerLife <= 0) {
                coroutineScope.launch {
                    playerViewModel.updateLastLevel(AppScreens.DerrotaScreen.route)
                    delay(100)
                    // *** CAMBIO CLAVE AQUÍ: popUpTo al navegar a la pantalla de derrota ***
                    navController.navigate(route = AppScreens.DerrotaScreen.route) {
                        popUpTo(combatData.currentScreenRoute) { inclusive = true } // Eliminar la pantalla de combate actual
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    fun onPlayerAttackInternal() {
        if (currentEnemyLife > 0 && areButtonsEnabled) {
            areButtonsEnabled = false
            val damageToEnemy = playerDamage - enemyDefense
            val finalDamageToEnemy = if (damageToEnemy < 0) 0 else damageToEnemy
            val newEnemyLife = (currentEnemyLife - finalDamageToEnemy).coerceAtLeast(0)
            enemyViewModel.updateEnemyLife(combatData.enemyId, newEnemyLife)

            enemyDamageText = "-$finalDamageToEnemy"
            enemyDamageColor = Color.Red
            showEnemyDamageAnimation = true

            coroutineScope.launch {
                SoundPlayer.playSoundAttack(context)
                delay(300)
                if (newEnemyLife <= 0) {
                    playerViewModel.updateEnemyTurnCount(0)
                    playerViewModel.updateLastLevel(combatData.nextScreenRouteOnWin)
                    delay(100)
                    // *** CAMBIO CLAVE AQUÍ: popUpTo al navegar a la pantalla de victoria/siguiente nivel ***
                    navController.navigate(route = combatData.nextScreenRouteOnWin) {
                        popUpTo(combatData.currentScreenRoute) { inclusive = true } // Eliminar la pantalla de combate actual
                        launchSingleTop = true
                    }
                } else {
                    onEnemyAttackInternal()
                }
                delay(200)
                areButtonsEnabled = true
            }
        }
    }

    fun onDefendInternal() {
        if (areButtonsEnabled) {
            areButtonsEnabled = false
            coroutineScope.launch {
                val currentTurnCount = playerViewModel.enemyTurnCount.value + 1
                playerViewModel.updateEnemyTurnCount(currentTurnCount)

                val isCriticalAttackIncomingLocal = enemyCritFreq > 0 && (currentTurnCount % enemyCritFreq == 0)

                isDefenseCritical = isCriticalAttackIncomingLocal

                if (isCriticalAttackIncomingLocal) {
                    SoundPlayer.playSoundShieldCrit(context)
                    vibrationViewModel.vibrate(context)
                } else {
                    SoundPlayer.playSoundShield(context)
                    vibrationViewModel.vibrate(context)
                }
                showDefenseImageAnimation = true

                delay(600)
                areButtonsEnabled = true
            }
        }
    }

    fun onUsePotionInternal() {
        if (playerPotions > 0 && areButtonsEnabled) {
            areButtonsEnabled = false
            val oldPotions = playerPotions
            val oldPlayerLife = playerLife

            val newPotions = playerPotions - 1
            val newPlayerLife = (playerLife + playerPotionHealAmount).coerceAtMost(playerMaxLife)
            playerViewModel.updatePlayerLife(newPlayerLife)
            playerViewModel.updatePlayerPotions(newPotions)

            val actualHeal = newPlayerLife - oldPlayerLife
            if (actualHeal > 0) {
                playerHealText = "+$actualHeal"
                showPlayerHealAnimation = true
            }

            val potionChange = newPotions - oldPotions
            if (potionChange != 0) {
                playerPotionsText = if (potionChange < 0) "$potionChange" else "+$potionChange"
                playerPotionsTextColor = if (potionChange < 0) Color.Red else Color.Green
                showPlayerPotionsAnimation = true
            }

            coroutineScope.launch {
                SoundPlayer.playSoundPotion(context)
                delay(300)
                onEnemyAttackInternal()
                delay(300)
                areButtonsEnabled = true
            }
        }
    }

    Scaffold(

        //Barra de estado Player
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
                    Box(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            StatsStyle(
                                iconRes = R.drawable.corazon,
                                text = "$playerLife/$playerMaxLife",
                                size = 22.dp
                            )
                            Spacer(modifier = Modifier.width(spacerIcons))
                            Box {
                                StatsStyle(iconRes = R.drawable.espada, text = "$playerDamage", size = 23.dp)
                                if (showPlayerDamageStatAnimation) {
                                    FloatingText(
                                        text = playerDamageStatText,
                                        color = playerDamageStatColor
                                    ) { showPlayerDamageStatAnimation = false }
                                }
                            }
                            Spacer(modifier = Modifier.width(spacerIcons))
                            Box {
                                StatsStyle(iconRes = R.drawable.escudo, text = "$playerDefense", size = 30.dp)
                                if (showPlayerDefenseStatAnimation) {
                                    FloatingText(
                                        text = playerDefenseStatText,
                                        color = playerDefenseStatColor
                                    ) { showPlayerDefenseStatAnimation = false }
                                }
                            }
                            Spacer(modifier = Modifier.width(spacerIcons))
                            Box {
                                StatsStyle(iconRes = R.drawable.potion, text = "$playerPotions", size = 23.dp)
                                if (showPlayerPotionsAnimation) {
                                    FloatingText(
                                        text = playerPotionsText,
                                        color = playerPotionsTextColor
                                    ) { showPlayerPotionsAnimation = false }
                                }
                            }
                            Spacer(modifier = Modifier.width(spacerIcons))
                            Box {
                                StatsStyle(iconRes = R.drawable.healpotion, text = "$playerPotionHealAmount", size = 23.dp)
                                if (showPlayerPotionHealStatAnimation) {
                                    FloatingText(
                                        text = playerPotionHealStatText,
                                        color = playerPotionHealStatColor
                                    ) { showPlayerPotionHealStatAnimation = false }
                                }
                            }
                        }
                        if (showPlayerDamageAnimation) {
                            FloatingText(
                                text = playerDamageText,
                                color = playerDamageColor
                            ) { showPlayerDamageAnimation = false }
                        }
                        if (showPlayerHealAnimation) {
                            FloatingText(
                                text = playerHealText,
                                color = Color.Green
                            ) { showPlayerHealAnimation = false }
                        }
                        if (showPlayerDefenseTextAnimation) {
                            FloatingText(
                                text = playerDefenseText,
                                color = Color.Cyan
                            ) { showPlayerDefenseTextAnimation = false }
                        }
                    }

                    IconButton(onClick = {
                        vibrationViewModel.vibrate(context)
                        SoundPlayer.playSoundButton(context)
                        navController.navigate(route = combatData.settingsScreenRoute)
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
                            painter = painterResource(id = combatData.backgroundPainterResId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        //Barra estado enemigo
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
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
                                        size = 22.dp
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    StatsStyle(iconRes = R.drawable.espada, text = "$enemyDamage", size = 23.dp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    StatsStyle(iconRes = R.drawable.escudo, text = "$enemyDefense", size = 30.dp)
                                }
                                if (showEnemyDamageAnimation) {
                                    Box(
                                        modifier = Modifier.matchParentSize(),
                                        contentAlignment = Alignment.TopStart
                                    ) {
                                        FloatingText(
                                            text = enemyDamageText,
                                            color = enemyDamageColor
                                        ) { showEnemyDamageAnimation = false }
                                    }
                                }
                            }

                            //Imagen enemigo
                            combatData.enemyPainterResId?.let { enemyResId ->
                                combatData.enemyImageSize?.let { enemySize ->
                                    Image(
                                        painter = painterResource(id = enemyResId),
                                        contentDescription = "Enemy Image",
                                        modifier = Modifier
                                            .size(enemySize)
                                    )
                                }
                            }
                        }

                        if (showDefenseImageAnimation) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                FloatingImage(
                                    imageResId = R.drawable.escudo,
                                    isCritical = isDefenseCritical,
                                    durationMillis = 600
                                ) { showDefenseImageAnimation = false }
                            }
                        }
                    }

                    //Texto y botones
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        //Texto
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = localizedString(combatData.startDialogueResId ?: R.string.combate_001, playerLanguage),
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = fuentePixelBold,
                                textAlign = TextAlign.Center
                            )
                        }

                        //Botones
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp)
                        ) {
                            ButtonStyle(text = localizedString(R.string.combate_atacar, playerLanguage), fontFamily = fuentePixelBold, enabled = areButtonsEnabled, onClick = {
                                vibrationViewModel.vibrate(context)
                                onPlayerAttackInternal()
                            })
                            Spacer(modifier = Modifier.height(8.dp))
                            ButtonStyle(text = localizedString(R.string.combate_defender, playerLanguage), fontFamily = fuentePixelBold, enabled = areButtonsEnabled, onClick = {
                                vibrationViewModel.vibrate(context)
                                onDefendInternal()
                            })
                            Spacer(modifier = Modifier.height(8.dp))
                            ButtonStyle(text = localizedString(R.string.combate_pocion, playerLanguage), fontFamily = fuentePixelBold, enabled = areButtonsEnabled, onClick = {
                                vibrationViewModel.vibrate(context)
                                onUsePotionInternal()
                            })
                        }
                    }
                }
            }
        }
    )
}