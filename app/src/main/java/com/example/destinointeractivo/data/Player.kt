package com.example.destinointeractivo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player(
    @PrimaryKey(autoGenerate = false) val id: Int = 1,
    val currentLife: Int,
    val maxLife: Int,
    val damage: Int,
    val defense: Int,
    val potions: Int,
    val potionHealAmount: Int,
    val effectsVolume: Int,
    val musicVolume: Int,
    val vibrationEnabled: Boolean,
    val language: String,
    val lastLevel: String
) {
    init {
        require(currentLife <= maxLife) { "La vida actual no puede ser mayor que la vida máxima." }
        require(effectsVolume in 0..10) { "El volumen de efectos debe estar entre 0 y 10." }
        require(musicVolume in 0..10) { "El volumen de la música debe estar entre 0 y 10." }
        require(language == "es" || language == "en") { "El idioma debe ser 'es' o 'en'." }
    }
}