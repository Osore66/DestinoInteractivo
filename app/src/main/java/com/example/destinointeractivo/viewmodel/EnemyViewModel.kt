package com.example.destinointeractivo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.destinointeractivo.data.AppDatabase
import com.example.destinointeractivo.data.EnemyDao
import com.example.destinointeractivo.data.InitialData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull

class EnemyViewModel(application: Application) : AndroidViewModel(application) {

    private val enemyDao: EnemyDao
    private val database: AppDatabase

    // StateFlows para observar stats del enemigo
    private val _currentEnemyLife = MutableStateFlow(0)
    val currentEnemyLife: StateFlow<Int> = _currentEnemyLife

    private val _maxEnemyLife = MutableStateFlow(0)
    val maxEnemyLife: StateFlow<Int> = _maxEnemyLife

    private val _enemyDamage = MutableStateFlow(0)
    val enemyDamage: StateFlow<Int> = _enemyDamage

    private val _enemyDefense = MutableStateFlow(0)
    val enemyDefense: StateFlow<Int> = _enemyDefense

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
                    _enemyCritFreq.value = enemy.critFreq
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

    fun getCurrentEnemyLife(enemyId: Int): StateFlow<Int> = _currentEnemyLife
    fun getMaxEnemyLife(enemyId: Int): StateFlow<Int> = _maxEnemyLife
    fun getEnemyDamage(enemyId: Int): StateFlow<Int> = _enemyDamage
    fun getEnemyDefense(enemyId: Int): StateFlow<Int> = _enemyDefense
    fun getEnemyCritFreq(enemyId: Int): StateFlow<Int> = _enemyCritFreq

    suspend fun resetEnemyData() {
        enemyDao.deleteAllEnemies()
        for (enemy in InitialData.enemyList) {
            enemyDao.insert(enemy)
        }
    }
}