package com.example.destinointeractivo

import androidx.compose.ui.res.stringResource
import com.example.destinointeractivo.R

// Mapeo dinámico de strings por idioma
val stringMap = mapOf(
    "es" to mapOf<Int, String>(
        R.string.btn_continuar to "Continuar",
        R.string.btn_nueva_partida to "Nueva Partida",
        R.string.btn_ajustes to "Ajustes",
        R.string.ajustes_idioma to "Idioma",
        R.string.ajustes_vibracion to "Vibración",
        R.string.ajustes_volumen_efectos to "Volumen efectos",
        R.string.ajustes_volumen_musica to "Volumen música",
        R.string.btn_volver_al_menu to "Volver al Menú",
        R.string.victoria_mensaje to "¡Has ganado!",
        R.string.derrota_mensaje to "¡Has sido derrotado!",
        R.string.ajustes_salir to "Salir al menú",
        R.string.ajustes_idioma_espanol to "Español",
        R.string.ajustes_idioma_ingles to "Inglés",
        R.string.combate_atacar to "Atacar",
        R.string.combate_defender to "Defender",
        R.string.combate_pocion to "Poción",
        R.string.ES_001_combate to "Te topas con un travieso enemigo que te impide avanzar, no parece muy peligroso, ¿qué decides hacer? Ten en cuenta que puedes morir y tener que empezar de nuevo.",
        R.string.combate_reiniciar to "Volver a empezar",

    ),
    "en" to mapOf<Int, String>(
        R.string.btn_continuar to "Continue",
        R.string.btn_nueva_partida to "New Game",
        R.string.btn_ajustes to "Settings",
        R.string.ajustes_idioma to "Language",
        R.string.ajustes_vibracion to "Vibration",
        R.string.ajustes_volumen_efectos to "Effects volume",
        R.string.ajustes_volumen_musica to "Music volume",
        R.string.btn_volver_al_menu to "Back to Menu",
        R.string.victoria_mensaje to "You Win!",
        R.string.derrota_mensaje to "You have been defeated!",
        R.string.ajustes_salir to "Exit to menu",
        R.string.ajustes_idioma_espanol to "Spanish",
        R.string.ajustes_idioma_ingles to "English",
        R.string.combate_atacar to "Attack",
        R.string.combate_defender to "Defend",
        R.string.combate_pocion to "Potion",
        R.string.ES_001_combate to "You come across a mischievous enemy blocking your way. It doesn't seem too dangerous. What do you decide to do? Keep in mind that you can die and have to start over.",
        R.string.combate_reiniciar to "Try again",
    )
)
