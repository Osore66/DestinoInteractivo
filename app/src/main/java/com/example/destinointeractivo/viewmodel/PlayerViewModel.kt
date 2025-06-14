package com.example.destinointeractivo.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.destinointeractivo.data.AppDatabase
import com.example.destinointeractivo.data.Player
import com.example.destinointeractivo.data.PlayerDao
import com.example.destinointeractivo.data.PlayerSettings
import com.example.destinointeractivo.data.InitialData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull
import java.util.Locale

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val playerDao: PlayerDao
    private val database: AppDatabase

    private val _playerLife = MutableStateFlow(0)
    val playerLife: StateFlow<Int> = _playerLife

    private val _playerMaxLife = MutableStateFlow(0)
    val playerMaxLife: StateFlow<Int> = _playerMaxLife

    private val _playerDamage = MutableStateFlow(0)
    val playerDamage: StateFlow<Int> = _playerDamage

    private val _playerDefense = MutableStateFlow(0)
    val playerDefense: StateFlow<Int> = _playerDefense

    private val _playerPotions = MutableStateFlow(0)
    val playerPotions: StateFlow<Int> = _playerPotions

    private val _playerPotionHealAmount = MutableStateFlow(0)
    val playerPotionHealAmount: StateFlow<Int> = _playerPotionHealAmount

    private val _playerEffectsVolume = MutableStateFlow(0)
    val playerEffectsVolume: StateFlow<Int> = _playerEffectsVolume
    private val _playerMusicVolume = MutableStateFlow(0)
    val playerMusicVolume: StateFlow<Int> = _playerMusicVolume
    private val _playerVibrationEnabled = MutableStateFlow(false)
    val playerVibrationEnabled: StateFlow<Boolean> = _playerVibrationEnabled
    private val _playerLanguage = MutableStateFlow("")
    val playerLanguage: StateFlow<String> = _playerLanguage
    private val _enemyTurnCount = MutableStateFlow(0)
    val enemyTurnCount: StateFlow<Int> = _enemyTurnCount
    private val _lastLevel = MutableStateFlow("")
    val lastLevel: StateFlow<String> = _lastLevel


    init {
        database = AppDatabase.getDatabase(application, viewModelScope)
        playerDao = database.playerDao()
        loadPlayerData()
    }

    fun loadPlayerData() {
        viewModelScope.launch {
            playerDao.getPlayerData().collect { player ->
                if (player != null) {
                    _playerLife.value = player.currentLife
                    _playerMaxLife.value = player.maxLife
                    _playerDamage.value = player.damage
                    _playerDefense.value = player.defense
                    _playerPotions.value = player.potions
                    _playerPotionHealAmount.value = player.potionHealAmount
                    _playerEffectsVolume.value = player.effectsVolume
                    _playerMusicVolume.value = player.musicVolume
                    _playerVibrationEnabled.value = player.vibrationEnabled
                    _playerLanguage.value = player.language
                    _enemyTurnCount.value = player.enemyTurnCount
                    _lastLevel.value = player.lastLevel
                }
            }
        }
    }

    fun updatePlayerLife(newLife: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updateCurrentLife(newLife)
            _playerLife.value = newLife
        }
    }

    fun updatePlayerPotions(newPotions: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updatePotions(newPotions)
            _playerPotions.value = newPotions
        }
    }

    fun updatePlayerPotionHealAmount(newPotionHealAmount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updatePotions(newPotionHealAmount)
            _playerPotionHealAmount.value = newPotionHealAmount
        }
    }

    fun updatePlayerEffectsVolume(newVolume: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updateEffectsVolume(newVolume)
            _playerEffectsVolume.value = newVolume
        }
    }

    fun updatePlayerMusicVolume(newVolume: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updateMusicVolume(newVolume)
            _playerMusicVolume.value = newVolume
        }
    }

    fun updatePlayerVibration(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updateVibration(enabled)
            _playerVibrationEnabled.value = enabled
        }
    }

    fun updatePlayerLanguage(newLanguage: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updateLanguage(newLanguage)
            _playerLanguage.value = newLanguage
        }
    }

    fun updateEnemyTurnCount(newCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updateEnemyTurnCount(newCount)
            _enemyTurnCount.value = newCount
        }
    }

    fun updateLastLevel(newLevel: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updateLastLevel(newLevel)
            _lastLevel.value = newLevel
        }
    }

    suspend fun getPlayerSettings(): PlayerSettings {
        return playerDao.getPlayerSettings()
    }

    suspend fun getPlayerData(): Player? {
        return playerDao.getPlayerData().firstOrNull()
    }

    suspend fun resetPlayerData(settings: PlayerSettings) {
        val defaultPlayer = InitialData.defaultPlayer.copy(
            effectsVolume = settings.effectsVolume,
            musicVolume = settings.musicVolume,
            vibrationEnabled = settings.vibrationEnabled,
            language = settings.language,
        )
        playerDao.deleteAllPlayers()
        playerDao.insert(defaultPlayer)
    }

    suspend fun resetPlayerDataWithDefaultSettings() {
        val defaultPlayer = InitialData.defaultPlayer.copy(
            effectsVolume = 5,
            musicVolume = 5,
            vibrationEnabled = true,
            language = "es",
        )
        playerDao.deleteAllPlayers()
        playerDao.insert(defaultPlayer)
    }

    suspend fun loadDefaultPlayer() {
        playerDao.deleteAllPlayers()
        playerDao.insert(InitialData.defaultPlayer.copy(
            effectsVolume = 5,
            musicVolume = 5,
            vibrationEnabled = true,
            language = "es",
        ))
    }

    fun updateAppLanguage(context: Context, newLanguage: String) {
        val locale = Locale(newLanguage)
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updateLanguage(newLanguage)
            _playerLanguage.value = newLanguage
        }
    }

    // Método para cambiar el idioma y actualizar la configuración sin reiniciar
    fun setAppLanguage(context: Context, newLanguage: String) {
        val locale = Locale(newLanguage)
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Guardar el idioma en la base de datos
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updateLanguage(newLanguage)
        }
    }

    fun increasePlayerDamage(amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentDamage = playerDamage.value
            val newDamage = currentDamage + amount
            playerDao.updateDamage(newDamage)
            _playerDamage.value = newDamage
        }
    }

    fun increasePlayerDefense(amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentDefense = playerDefense.value
            val newDefense = currentDefense + amount
            playerDao.updateDefense(newDefense)
            _playerDefense.value = newDefense
        }
    }

    fun increasePlayerPotions(amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentPotions = playerPotions.value
            val newPotions = currentPotions + amount
            playerDao.updatePotions(newPotions)
            _playerPotions.value = newPotions
        }
    }

    fun increasePlayerPotionHealAmount(amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentHealAmount = playerPotionHealAmount.value
            val newHealAmount = currentHealAmount + amount
            playerDao.updatePotionHealAmount(newHealAmount)
            _playerPotionHealAmount.value = newHealAmount
        }
    }
}