package com.example.destinointeractivo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.destinointeractivo.data.AppDatabase
import com.example.destinointeractivo.data.EnemyDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull

class EnemyViewModel(application: Application) : AndroidViewModel(application) {

    private val enemyDao: EnemyDao
    private val database: AppDatabase

    // StateFlow para observar la vida actual del enemigo
    private val _currentEnemyLife = MutableStateFlow(0)
    val currentEnemyLife: StateFlow<Int> = _currentEnemyLife

    // StateFlow para observar la vida máxima del enemigo
    private val _maxEnemyLife = MutableStateFlow(0)
    val maxEnemyLife: StateFlow<Int> = _maxEnemyLife

    // StateFlow para observar el daño del enemigo
    private val _enemyDamage = MutableStateFlow(0)
    val enemyDamage: StateFlow<Int> = _enemyDamage

    // StateFlow para observar la defensa del enemigo
    private val _enemyDefense = MutableStateFlow(0)
    val enemyDefense: StateFlow<Int> = _enemyDefense

    // 🔥 Nuevo StateFlow para la frecuencia de golpes críticos
    private val _enemyCritFreq = MutableStateFlow(0)
    val enemyCritFreq: StateFlow<Int> = _enemyCritFreq

    init {
        database = AppDatabase.getDatabase(application, viewModelScope)
        enemyDao = database.enemyDao()
    }

    fun loadEnemyData(enemyId: Int) {
        viewModelScope.launch {
            enemyDao.get(enemyId).collect { enemy ->
                if (enemy != null) {
                    _currentEnemyLife.value = enemy.currentLife.coerceIn(0, enemy.maxLife)
                    _maxEnemyLife.value = enemy.maxLife
                    _enemyDamage.value = enemy.damage
                    _enemyDefense.value = enemy.defense
                    _enemyCritFreq.value = enemy.critFreq  // <-- Cargamos critFreq
                }
            }
        }
    }

    fun updateEnemyLife(enemyId: Int, newLife: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentEnemy = enemyDao.get(enemyId).firstOrNull()
            currentEnemy?.let {
                val updatedLife = newLife.coerceIn(0, it.maxLife)
                val updatedEnemy = it.copy(currentLife = updatedLife)
                enemyDao.update(updatedEnemy)
                _currentEnemyLife.value = updatedLife
            }
        }
    }

    // Función para obtener el StateFlow de la vida actual de un enemigo específico
    fun getCurrentEnemyLife(enemyId: Int): StateFlow<Int> {
        return _currentEnemyLife
    }

    // Función para obtener el StateFlow de la vida máxima de un enemigo específico
    fun getMaxEnemyLife(enemyId: Int): StateFlow<Int> {
        return _maxEnemyLife
    }

    // Función para obtener el StateFlow del daño del enemigo específico
    fun getEnemyDamage(enemyId: Int): StateFlow<Int> {
        return _enemyDamage
    }

    // Función para obtener el StateFlow de la defensa del enemigo específico
    fun getEnemyDefense(enemyId: Int): StateFlow<Int> {
        return _enemyDefense
    }

    // 🔥 Nueva función para obtener el StateFlow de critFreq
    fun getEnemyCritFreq(enemyId: Int): StateFlow<Int> {
        return _enemyCritFreq
    }
}