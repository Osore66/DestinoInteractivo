package com.example.destinointeractivo

import androidx.compose.ui.res.stringResource
import com.example.destinointeractivo.R

// Mapeo dinámico de strings por idioma
val stringMap = mapOf(
    "es" to mapOf<Int, String>(
        // Botones del menú principal
        R.string.btn_continuar to "Continuar",
        R.string.btn_nueva_partida to "Nueva Partida",
        R.string.btn_ajustes to "Ajustes",

        // Pantalla de Ajustes
        R.string.ajustes_idioma to "Idioma",
        R.string.ajustes_vibracion to "Vibración",
        R.string.ajustes_volumen_efectos to "Volumen efectos",
        R.string.ajustes_volumen_musica to "Volumen música",
        R.string.btn_volver_al_menu to "Volver al Menú",
        R.string.ajustes_salir to "Salir al menú",
        R.string.ajustes_idioma_espanol to "Español",
        R.string.ajustes_idioma_ingles to "Inglés",

        // Pantalla de Combate
        R.string.combate_atacar to "Atacar",
        R.string.combate_defender to "Defender",
        R.string.combate_pocion to "Poción",
        R.string.ES_001_combate to "Te topas con un travieso enemigo que te impide avanzar, no parece muy peligroso, ¿qué decides hacer? Ten en cuenta que puedes morir y tener que empezar de nuevo.",
        R.string.combate_reiniciar to "Volver a empezar",

        // Mensajes de Victoria/Derrota
        R.string.victoria_mensaje to "¡Has ganado!",
        R.string.derrota_mensaje to "¡Has sido derrotado!",

        // Recompensas de Combate (Victoria 001)
        R.string.victoria_001_message to "Has ganado el combate, elige una recompensa:",
        R.string.victoria_001_damage to "+1 Daño",
        R.string.victoria_001_shield to "+1 Escudo",
        R.string.victoria_001_potion to "+1 Poción",
        R.string.victoria_001_heal to "+15 Cura Poción",
        R.string.victoria_002_message to "Has ganado el combate, elige una recompensa:",
        R.string.victoria_002_damage to "+2 Daño",
        R.string.victoria_002_shield to "+1 Escudo",
        R.string.victoria_002_potion to "+1 Poción",
        R.string.victoria_002_heal to "+15 Cura Poción",
        R.string.victoria_003_message to "Has ganado el combate, elige una recompensa:",
        R.string.victoria_003_damage to "+1 Daño",
        R.string.victoria_003_shield to "+1 Escudo",
        R.string.victoria_003_potion to "+1 Poción",
        R.string.victoria_003_heal to "+15 Cura Poción",
        R.string.victoria_004_message to "Has ganado el combate, elige una recompensa:",
        R.string.victoria_004_damage to "+2 Daño",
        R.string.victoria_004_shield to "+1 Escudo",
        R.string.victoria_004_potion to "+1 Poción",
        R.string.victoria_004_heal to "+15 Cura Poción",

        // Pantalla de Selección de Arma
        R.string.weapon_selection_message to "Bienvenido, aventurero, elige el arma que prefieras:",
        R.string.weapon_mandoble to "Mandoble:\n+2 Daño",
        R.string.weapon_espada_escudo to "Espada y escudo:\n+1 Daño, +1 Escudo",
        R.string.weapon_escudo_pesado to "Escudo pesado:\n+2 Escudo"
    ),

    "en" to mapOf<Int, String>(
        // Main Menu Buttons
        R.string.btn_continuar to "Continue",
        R.string.btn_nueva_partida to "New Game",
        R.string.btn_ajustes to "Settings",

        // Settings Screen
        R.string.ajustes_idioma to "Language",
        R.string.ajustes_vibracion to "Vibration",
        R.string.ajustes_volumen_efectos to "Effects volume",
        R.string.ajustes_volumen_musica to "Music volume",
        R.string.btn_volver_al_menu to "Back to Menu",
        R.string.ajustes_salir to "Exit to menu",
        R.string.ajustes_idioma_espanol to "Spanish",
        R.string.ajustes_idioma_ingles to "English",

        // Combat Screen
        R.string.combate_atacar to "Attack",
        R.string.combate_defender to "Defend",
        R.string.combate_pocion to "Potion",
        R.string.ES_001_combate to "You come across a mischievous enemy blocking your way. It doesn't seem too dangerous. What do you decide to do? Keep in mind that you can die and have to start over.",
        R.string.combate_reiniciar to "Try again",

        // Victory/Defeat Messages
        R.string.victoria_mensaje to "You Win!",
        R.string.derrota_mensaje to "You have been defeated!",

        // Combat Rewards (Victory 001)
        R.string.victoria_001_message to "You have won the combat, choose a reward:",
        R.string.victoria_001_damage to "+1 Damage",
        R.string.victoria_001_shield to "+1 Shield",
        R.string.victoria_001_potion to "+1 Potion",
        R.string.victoria_001_heal to "+15 Potion Heal",
        R.string.victoria_002_message to "You have won the combat, choose a reward:",
        R.string.victoria_002_damage to "+2 Damage",
        R.string.victoria_002_shield to "+1 Shield",
        R.string.victoria_002_potion to "+1 Potion",
        R.string.victoria_002_heal to "+15 Potion Heal",
        R.string.victoria_003_message to "Has ganado el combate, elige una recompensa:",
        R.string.victoria_003_damage to "+1 Daño",
        R.string.victoria_003_shield to "+1 Escudo",
        R.string.victoria_003_potion to "+1 Poción",
        R.string.victoria_003_heal to "+15 Cura Poción",
        R.string.victoria_004_message to "You have won the combat, choose a reward:",
        R.string.victoria_004_damage to "+2 Damage",
        R.string.victoria_004_shield to "+1 Shield",
        R.string.victoria_004_potion to "+1 Potion",
        R.string.victoria_004_heal to "+15 Potion Heal",

        // Weapon Selection Screen
        R.string.weapon_selection_message to "Welcome, adventurer. Choose the weapon you prefer:",
        R.string.weapon_mandoble to "Greatsword:\n+2 Damage",
        R.string.weapon_espada_escudo to "Sword and shield:\n+1 Damage, +1 Shield",
        R.string.weapon_escudo_pesado to "Heavy shield:\n+2 Shield"
    )
)

