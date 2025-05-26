package com.example.destinointeractivo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    // --- CRUD ---
    @Insert
    suspend fun insert(player: Player)

    @Update
    suspend fun update(player: Player)

    @Query("DELETE FROM player")
    suspend fun deleteAllPlayers()

    // --- Lectura ---
    @Query("SELECT * FROM player WHERE id = 1")
    fun getPlayerData(): Flow<Player?>

    // --- Actualizaciones específicas ---
    @Query("UPDATE player SET currentLife = :newLife WHERE id = 1")
    suspend fun updateCurrentLife(newLife: Int)

    @Query("UPDATE player SET potions = :newPotions WHERE id = 1")
    suspend fun updatePotions(newPotions: Int)

    @Query("UPDATE player SET effectsVolume = :newVolume WHERE id = 1")
    suspend fun updateEffectsVolume(newVolume: Int)

    @Query("UPDATE player SET musicVolume = :newVolume WHERE id = 1")
    suspend fun updateMusicVolume(newVolume: Int)

    @Query("UPDATE player SET vibrationEnabled = :enabled WHERE id = 1")
    suspend fun updateVibration(enabled: Boolean)

    @Query("UPDATE player SET language = :newLanguage WHERE id = 1")
    suspend fun updateLanguage(newLanguage: String)

    @Query("UPDATE player SET lastLevel = :newLastLevel WHERE id = 1")
    suspend fun updateLastLevel(newLastLevel: String)

    @Query("UPDATE player SET enemyTurnCount = :turnCount WHERE id = 1")
    suspend fun updateEnemyTurnCount(turnCount: Int)

    @Query("UPDATE player SET potionHealAmount = :newHealAmount WHERE id = 1")
    suspend fun updatePotionHealAmount(newHealAmount: Int)

    // --- Consultas auxiliares ---
    @Query("SELECT potionHealAmount FROM player WHERE id = 1")
    suspend fun getPotionHealAmount(): Int

    @Query("SELECT effectsVolume, musicVolume, vibrationEnabled, language FROM player WHERE id = 1")
    suspend fun getPlayerSettings(): PlayerSettings

    // --- Nuevos métodos para incrementar estadísticas ---
    @Query("UPDATE player SET damage = :newDamage WHERE id = 1")
    suspend fun updateDamage(newDamage: Int)

    @Query("UPDATE player SET defense = :newDefense WHERE id = 1")
    suspend fun updateDefense(newDefense: Int)
}