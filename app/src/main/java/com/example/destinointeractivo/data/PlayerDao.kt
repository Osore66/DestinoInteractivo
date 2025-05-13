package com.example.destinointeractivo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player WHERE id = 1")
    fun getPlayerData(): Flow<Player?>

    @Insert
    suspend fun insert(player: Player)

    @Update
    suspend fun update(player: Player)

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

    @Query("UPDATE player SET lastLevel = :newLastLevel WHERE id = 1")  //Nueva Query
    suspend fun updateLastLevel(newLastLevel: String)

    // Nuevo método para borrar todos los jugadores
    @Query("DELETE FROM player")
    suspend fun deleteAllPlayers()
}