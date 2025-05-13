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
import com.example.destinointeractivo.ButtonStyle
import com.example.destinointeractivo.NavViewModel
import com.example.destinointeractivo.R
import com.example.destinointeractivo.StatsStyle
import com.example.destinointeractivo.VibrationViewModel
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
    // Contexto local
    val context = LocalContext.current
    val vibrationViewModel: VibrationViewModel = viewModel { VibrationViewModel(context) }
    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))

    // Guardar la última pantalla en el NavViewModel
    navViewModel.lastScreen.value = AppScreens.SinMetodos_Combate.route

    // Obtener los ViewModels
    val enemyViewModel: EnemyViewModel = viewModel()
    val playerViewModel: PlayerViewModel = viewModel() // Inicializa el PlayerViewModel

    // *** Aquí decidimos el ID del enemigo para esta pantalla ***
    val enemyId = 1 // *** ¡CAMBIA ESTE VALOR SEGÚN LA PANTALLA! ***

    // Cargar los datos del enemigo y del jugador al entrar en la pantalla
    LaunchedEffect(enemyId) {
        enemyViewModel.loadEnemyData(enemyId)
        playerViewModel.loadPlayerData() // Carga los datos del jugador
    }

    // Observar los datos del enemigo
    val currentEnemyLife by enemyViewModel.currentEnemyLife.collectAsState(initial = 0)
    val maxEnemyLife by enemyViewModel.maxEnemyLife.collectAsState(initial = 0)
    val enemyDamage by enemyViewModel.enemyDamage.collectAsState(initial = 0)
    val enemyDefense by enemyViewModel.enemyDefense.collectAsState(initial = 0)
    val enemyCritFreq by enemyViewModel.enemyCritFreq.collectAsState(initial = 0)

    // Observar los datos del jugador
    val playerLife by playerViewModel.playerLife.collectAsState(initial = 0)
    val playerDamage by playerViewModel.playerDamage.collectAsState(initial = 0)
    val playerDefense by playerViewModel.playerDefense.collectAsState(initial = 0)
    val playerPotions by playerViewModel.playerPotions.collectAsState(initial = 0)
    val playerMaxLife by playerViewModel.playerMaxLife.collectAsState(initial = 0)

    // Variable para el texto de combate
    val combateText = remember {
        mutableStateOf(
            "Te encuentras con un travieso slime que te impide avanzar, no parece muy peligroso, ¿qué decides hacer? Ten en cuenta que puedes morir y tener que empezar de nuevo."
        )
    }

    val coroutineScope = rememberCoroutineScope()
    var enemyTurnCount by remember { mutableIntStateOf(0) }
    var isAttackButtonEnabled by remember { mutableStateOf(true) }
    var isDefendButtonEnabled by remember { mutableStateOf(true) }

    fun onEnemyAttack() {
        if (currentEnemyLife > 0) {
            // Incrementamos el contador de turnos
            enemyTurnCount++

            // Comprobamos si es un turno crítico
            val isCritical = enemyTurnCount % enemyCritFreq == 0

            // Calculamos el daño base
            var damageToPlayer = enemyDamage - playerDefense

            // Aplicamos daño doble si es ataque crítico
            if (isCritical) {
                damageToPlayer *= 2
            } else {
            }

            // Actualizamos la vida del jugador
            val newPlayerLife = (playerLife - damageToPlayer).coerceAtLeast(0)
            playerViewModel.updatePlayerLife(newPlayerLife)

            // Hacemos vibrar el dispositivo
            vibrationViewModel.vibrate(context)
            vibrationViewModel.vibrate(context)
        }
    }

    // Función para manejar el ataque del jugador
    fun onPlayerAttack() {
        if (currentEnemyLife > 0 && isAttackButtonEnabled) {
            isAttackButtonEnabled = false
            val damageToEnemy = playerDamage - enemyDefense
            val newEnemyLife = (currentEnemyLife - damageToEnemy).coerceAtLeast(0)
            enemyViewModel.updateEnemyLife(enemyId, newEnemyLife)
            coroutineScope.launch {
                delay(300)
                if (currentEnemyLife > 0) {
                    vibrationViewModel.vibrate(context)
                    onEnemyAttack()
                }
                delay(300) // Cooldown de 1.5 segundos
                isAttackButtonEnabled = true
            }
        }
    }

    fun onDefend() {
        if (isDefendButtonEnabled) {
            isDefendButtonEnabled = false

            coroutineScope.launch {
                // Solo avanzamos el turno, sin ataque ni daño
                enemyTurnCount++

                delay(600) // Mismo delay que Atacar
                isDefendButtonEnabled = true
            }
        }
    }

    // Estructura principal de la pantalla
    Scaffold(
        topBar = {
            val spacerIcons = 14.dp
            // Barra de estado reutilizable
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
                        // *** Muestra la vida del jugador desde la base de datos ***
                        StatsStyle(
                            iconRes = R.drawable.corazon,
                            text = "$playerLife/$playerMaxLife", // Modificado para mostrar vida actual/máxima
                            size = 25.dp
                        )
                        Spacer(modifier = Modifier.width(spacerIcons))
                        StatsStyle(iconRes = R.drawable.espada, text = "$playerDamage", size = 23.dp)
                        Spacer(modifier = Modifier.width(spacerIcons))
                        StatsStyle(iconRes = R.drawable.escudo, text = "$playerDefense", size = 30.dp)
                        Spacer(modifier = Modifier.width(spacerIcons))
                        StatsStyle(iconRes = R.drawable.potion, text = "$playerPotions", size = 23.dp)
                    }
                    IconButton(onClick = {
                        vibrationViewModel.vibrate(context)
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        // Fondo
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
                                    // *** VIDA ENEMIGO ***
                                    StatsStyle(
                                        iconRes = R.drawable.corazon,
                                        text = "$currentEnemyLife/$maxEnemyLife",
                                        size = 25.dp
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    // *** DAÑO ENEMIGO ***
                                    StatsStyle(iconRes = R.drawable.espada, text = "$enemyDamage", size = 23.dp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    // ESCUDO ENEMIGO
                                    StatsStyle(iconRes = R.drawable.escudo, text = "1", size = 30.dp)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Imagen del enemigo
                            /*
                            Image(
                                painter = painterResource(R.drawable.enemigo1),
                                contentDescription = "Slime",
                                modifier = Modifier
                                    .size(180.dp)
                                    .align(Alignment.CenterHorizontally)

                            )
                             */
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Texto de combate y botones de acción
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
                                .weight(1f), // El texto ocupa el espacio restante
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = combateText.value,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = fuentePixelBold,
                                textAlign = TextAlign.Center
                            )
                        }

                        // Botones de acción (Se quedan en la parte inferior)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp)
                        ) {
                            ButtonStyle(text = "Atacar", fontFamily = fuentePixelBold, enabled = isAttackButtonEnabled) {
                                vibrationViewModel.vibrate(context)
                                onPlayerAttack()
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            ButtonStyle(text = "Defender", fontFamily = fuentePixelBold, enabled = isDefendButtonEnabled) {
                                vibrationViewModel.vibrate(context)
                                onDefend()
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            ButtonStyle(text = "Poción", fontFamily = fuentePixelBold) {
                                vibrationViewModel.vibrate(context)
                            }
                        }
                    }
                }
            }
        }
    )
}