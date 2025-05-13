package com.example.destinointeractivo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enemies")
data class Enemy(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val currentLife: Int,
    val maxLife: Int,
    val damage: Int,
    val defense: Int,
    val nivel: String,
    val critFreq: Int
)