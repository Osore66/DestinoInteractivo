package com.example.destinointeractivo

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.destinointeractivo.R
import kotlinx.coroutines.delay

@Composable
fun FloatingText(
    text: String,
    color: Color,
    onAnimationEnd: () -> Unit
) {
    // Definimos el estado inicial de la animación y el estado final
    var targetOffsetY by remember { mutableStateOf(0f) } // Comienza en 0 (posición actual)
    var targetAlpha by remember { mutableStateOf(1f) }   // Comienza en 1 (totalmente visible)

    val animationDuration = 500 // Duración más corta: 500 milisegundos

    val offsetY by animateFloatAsState(
        targetValue = targetOffsetY,
        animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing),
        label = "offsetYAnimation"
    )
    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing),
        label = "alphaAnimation"
    )

    val fuentePixelBold = FontFamily(Font(R.font.pixelgeorgiabold))

    LaunchedEffect(Unit) {
        // En cuanto el composable entra en el árbol, disparamos el cambio del targetValue
        // Esto hará que la animación se dispare desde 0f/1f hacia el nuevo target
        targetOffsetY = -70f // Recorre menos espacio vertical hacia arriba
        targetAlpha = 0f // Se desvanece
        delay(animationDuration.toLong()) // Espera a que termine la animación
        onAnimationEnd() // Notifica al padre que la animación ha terminado
    }

    Text(
        text = text,
        color = color.copy(alpha = alpha),
        fontSize = 24.sp,
        fontFamily = fuentePixelBold,
        modifier = Modifier.offset(y = offsetY.dp)
    )
}