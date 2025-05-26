package com.example.destinointeractivo.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun FloatingImage(
    imageResId: Int,
    isCritical: Boolean = false,
    durationMillis: Int = 100,
    onAnimationEnd: () -> Unit
) {
    var currentAlphaTarget by remember { mutableStateOf(1f) }
    var currentSizeTarget by remember { mutableStateOf(120.dp) }

    val animatedAlpha by animateFloatAsState(
        targetValue = currentAlphaTarget,
        animationSpec = tween(durationMillis = durationMillis, easing = LinearEasing),
        label = "imageAlphaAnimation"
    )

    val animatedSize by animateDpAsState(
        targetValue = currentSizeTarget, // Usar el target dinámico
        animationSpec = tween(durationMillis = durationMillis, easing = LinearOutSlowInEasing), // Mismo tiempo para el tamaño
        label = "imageSizeAnimation"
    )

    LaunchedEffect(isCritical) {
        // Primero, cambia el tamaño al deseado (normal o crítico)
        currentSizeTarget = if (isCritical) 180.dp else 120.dp

        delay(10) // Un delay muy corto para asegurar que el tamaño se establece antes del desvanecimiento

        currentAlphaTarget = 0f // Inicia el desvanecimiento

        delay(durationMillis.toLong()) // Espera la duración completa de la animación
        onAnimationEnd() // Notifica al padre
    }

    Box(
        modifier = Modifier
            .size(animatedSize) // Usa el tamaño animado aquí
            .alpha(animatedAlpha) // Aplica el alfa animado
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier.matchParentSize()
        )
    }
}