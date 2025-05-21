package com.example.destinointeractivo.data

import androidx.room.RoomWarnings

object InitialData {
    @Suppress(RoomWarnings.CURSOR_MISMATCH)
    val defaultPlayer = Player(
        id = 1,
        currentLife = 10,
        maxLife = 10,
        damage = 2,
        defense = 1,
        potions = 2,
        potionHealAmount = 5,
        effectsVolume = 5,
        musicVolume = 5,
        vibrationEnabled = true,
        language = "es",
        lastLevel = "SinMetodos_Combate",
        enemyTurnCount = 0
    )

    val enemyList = listOf(
        Enemy(id = 1, currentLife = 10, maxLife = 10, damage = 2, defense = 1, nivel = "Combate_1", critFreq = 2),
        Enemy(id = 2, currentLife = 15, maxLife = 15, damage = 3, defense = 0, nivel = "Combate_2", critFreq = 3),
        Enemy(id = 3, currentLife = 20, maxLife = 20, damage = 4, defense = 1, nivel = "Combate_3", critFreq = 3),
        Enemy(id = 4, currentLife = 20, maxLife = 20, damage = 4, defense = 1, nivel = "Combate_4", critFreq = 3),
        Enemy(id = 5, currentLife = 8, maxLife = 8, damage = 1, defense = 2, nivel = "Tutorial", critFreq = 2),
        Enemy(id = 6, currentLife = 8, maxLife = 8, damage = 1, defense = 2, nivel = "TutorialDificil", critFreq = 2),
        Enemy(id = 7, currentLife = 20, maxLife = 20, damage = 4, defense = 1, nivel = "Jefe_Final", critFreq = 3)
    )
}