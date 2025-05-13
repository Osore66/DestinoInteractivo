package com.example.destinointeractivo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.destinointeractivo.data.AppDatabase
import com.example.destinointeractivo.data.PlayerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val playerDao: PlayerDao
    private val database: AppDatabase

    // StateFlow para observar la vida del jugador
    val playerLife: StateFlow<Int>
    private val _playerLife = MutableStateFlow(0)

    // StateFlow para observar la vida máxima del jugador
    val playerMaxLife: StateFlow<Int>  // Agrega esto
    private val _playerMaxLife = MutableStateFlow(0) // Agrega esto

    // StateFlow para observar el daño del jugador
    val playerDamage: StateFlow<Int>
    private val _playerDamage = MutableStateFlow(0)

    // StateFlow para observar la defensa del jugador
    val playerDefense: StateFlow<Int>
    private val _playerDefense = MutableStateFlow(0)

    // StateFlow para observar las pociones del jugador
    val playerPotions: StateFlow<Int>
    private val _playerPotions = MutableStateFlow(0)

    private val _playerEffectsVolume = MutableStateFlow(0)
    val playerEffectsVolume: StateFlow<Int> = _playerEffectsVolume

    private val _playerMusicVolume = MutableStateFlow(0)
    val playerMusicVolume: StateFlow<Int> = _playerMusicVolume

    private val _playerVibrationEnabled = MutableStateFlow(false)
    val playerVibrationEnabled: StateFlow<Boolean> = _playerVibrationEnabled

    private val _playerLanguage = MutableStateFlow("")
    val playerLanguage: StateFlow<String> = _playerLanguage


    init {
        database = AppDatabase.getDatabase(application, viewModelScope)
        playerDao = database.playerDao()
        playerLife = _playerLife
        playerMaxLife = _playerMaxLife // Inicializa el nuevo StateFlow
        playerDamage = _playerDamage
        playerDefense = _playerDefense
        playerPotions = _playerPotions
        loadPlayerData() // Cargamos los datos iniciales al inicializar el ViewModel
    }

    fun loadPlayerData() {
        viewModelScope.launch {
            playerDao.getPlayerData().collect { player ->
                if (player != null) {
                    _playerLife.value = player.currentLife
                    _playerMaxLife.value = player.maxLife // Actualiza el valor de maxLife
                    _playerDamage.value = player.damage
                    _playerDefense.value = player.defense
                    _playerPotions.value = player.potions
                    _playerEffectsVolume.value = player.effectsVolume
                    _playerMusicVolume.value = player.musicVolume
                    _playerVibrationEnabled.value = player.vibrationEnabled
                    _playerLanguage.value = player.language
                }
            }
        }
    }


    fun updatePlayerLife(newLife: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updateCurrentLife(newLife)
            _playerLife.value = newLife
            // No actualizamos el StateFlow aquí, la base de datos emite el cambio y el Flow lo recoge.
        }
    }

    fun updatePlayerPotions(newPotions: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDao.updatePotions(newPotions)
            _playerPotions.value = newPotions
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
}

