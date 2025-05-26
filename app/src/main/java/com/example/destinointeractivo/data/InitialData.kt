package com.example.destinointeractivo.data

import androidx.room.RoomWarnings

object InitialData {
    @Suppress(RoomWarnings.CURSOR_MISMATCH)
    val defaultPlayer = Player(
        id = 1,
        currentLife = 50,
        maxLife = 50,
        damage = 5,
        defense = 1,
        potions = 2,
        potionHealAmount = 15,
        effectsVolume = 5,
        musicVolume = 5,
        vibrationEnabled = true,
        language = "es",
        lastLevel = "WeaponSelectionScreen",
        enemyTurnCount = 0
    )

    val enemyList = listOf(
        Enemy(id = 1, currentLife = 25, maxLife = 25, damage = 4, defense = 0, nivel = "Combat_001", critFreq = 4),
        Enemy(id = 2, currentLife = 30, maxLife = 30, damage = 4, defense = 1, nivel = "Combat_002", critFreq = 4),
        Enemy(id = 3, currentLife = 35, maxLife = 35, damage = 5, defense = 1, nivel = "Combat_003", critFreq = 4),
        Enemy(id = 4, currentLife = 40, maxLife = 40, damage = 5, defense = 2, nivel = "Combat_004", critFreq = 3),
        Enemy(id = 5, currentLife = 50, maxLife = 50, damage = 7, defense = 3, nivel = "Combat_005", critFreq = 3),
        Enemy(id = 6, currentLife = 8, maxLife = 8, damage = 1, defense = 2, nivel = "Combat_006", critFreq = 2),
        Enemy(id = 7, currentLife = 20, maxLife = 20, damage = 4, defense = 1, nivel = "Combat_007", critFreq = 3)
    )
}