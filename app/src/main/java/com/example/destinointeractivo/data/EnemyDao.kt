package com.example.destinointeractivo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface EnemyDao {
    @Query("SELECT * FROM enemies")
    fun getAll(): Flow<List<Enemy>>

    @Query("SELECT * FROM enemies WHERE id = :id")
    fun get(id: Int): Flow<Enemy?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(enemy: Enemy)

    @Update
    suspend fun update(enemy: Enemy)

    @Delete
    suspend fun delete(enemy: Enemy)

    // Nuevo método para borrar todos los enemigos
    @Query("DELETE FROM enemies")
    suspend fun deleteAllEnemies()
}

