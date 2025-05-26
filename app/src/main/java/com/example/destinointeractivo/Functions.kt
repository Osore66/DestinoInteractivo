package com.example.destinointeractivo

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.destinointeractivo.navigation.AppScreens

val tamanyoFuente = 25.sp

//Estilos
@Composable
fun ButtonStyle(
    text: String,
    fontFamily: FontFamily,
    onClick: () -> Unit,
    enabled: Boolean = true,
    fontSize: TextUnit = tamanyoFuente, // Valor por defecto
    // --- NUEVO PARÁMETRO: lineHeight ---
    lineHeight: TextUnit = 30.sp
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.DarkGray
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text,
            fontFamily = fontFamily,
            fontSize = fontSize,
            lineHeight = lineHeight,
            textAlign = TextAlign.Center // <-- APLICADO AQUÍ PARA CENTRAR EL TEXTO

        )
    }
}

@Composable
fun StatsStyle(iconRes: Int, text: String, size: Dp = 24.dp, isFixedWidth: Boolean = false) {
    Row(
        //el modifier es para que uno o varios iconos (junto a su texto) ocupe una medida fija poniendole isFixedWidth = true, pero al final he decidio no usarlo.
        //modifier = if (isFixedWidth) Modifier.width(120.dp) else Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(size)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun localizedString(resId: Int, language: String): String {
    return stringMap[language]?.get(resId)
        ?: stringResource(id = resId) // Fallback a strings.xml
}