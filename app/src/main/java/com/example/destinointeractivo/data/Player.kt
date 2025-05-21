package com.example.destinointeractivo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player(
    @PrimaryKey val id: Int = 1,
    var currentLife: Int,
    var maxLife: Int,
    var damage: Int,
    var defense: Int,
    var potions: Int,
    var potionHealAmount: Int,
    var effectsVolume: Int,
    var musicVolume: Int,
    var vibrationEnabled: Boolean,
    var language: String,
    var lastLevel: String,
    var enemyTurnCount: Int = 0,
) {
    init {
        require(currentLife <= maxLife) { "La vida actual no puede ser mayor que la vida máxima." }
        require(effectsVolume in 0..10) { "El volumen de efectos debe estar entre 0 y 10." }
        require(musicVolume in 0..10) { "El volumen de la música debe estar entre 0 y 10." }
        require(language == "es" || language == "en") { "El idioma debe ser 'es' o 'en'." }
    }
}

