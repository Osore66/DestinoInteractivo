package com.example.destinointeractivo.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.util.Log
import com.example.destinointeractivo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object SoundPlayer {
    private var soundPool: SoundPool? = null
    private var effectsVolume = 1f // Valor por defecto (0.0-1.0)

    private const val MAX_STREAMS = 5

    // IDs de cada sonido
    private var buttonSoundId = 0
    private var newGameSoundId = 0
    private var attackSoundId = 0
    private var potionSoundId = 0
    private var shieldSoundId = 0
    private var shieldCritSoundId = 0
    private var hitSoundId = 0
    private var hitCritSoundId = 0


    // Estados de carga
    private val mutex = Mutex()
    private var lastPlayTimeButton = 0L
    private var lastPlayTimeNewGame = 0L
    private var lastPlayTimeAttack = 0L
    private var lastPlayTimePotion = 0L
    private var lastPlayTimeShield = 0L
    private var lastPlayTimeShieldCrit = 0L
    private var lastPlayTimeHit = 0L
    private var lastPlayTimeHitCrit = 0L

    private var isButtonLoading = false
    private var isNewGameLoading = false
    private var isAttackLoading = false
    private var isPotionLoading = false
    private var isShieldLoading = false
    private var isShieldCritLoading = false
    private var isHitLoading = false
    private var isHitCritLoading = false


    private const val MIN_PLAY_INTERVAL = 300L

    // Variable para el volumen de los efectos

    fun init(context: Context) {
        if (soundPool == null) {
            soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

                SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(MAX_STREAMS)
                    .build()
            } else {
                @Suppress("DEPRECATION")
                SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0)
            }

            buttonSoundId = soundPool?.load(context, R.raw.sound_button, 1) ?: -1
            newGameSoundId = soundPool?.load(context, R.raw.sound_newgame, 1) ?: -1
            attackSoundId = soundPool?.load(context, R.raw.sound_attack, 1) ?: -1
            potionSoundId = soundPool?.load(context, R.raw.sound_potion, 1) ?: -1
            shieldSoundId = soundPool?.load(context, R.raw.sound_shield, 1) ?: -1
            shieldCritSoundId = soundPool?.load(context, R.raw.sound_shieldcrit, 1) ?: -1
            hitSoundId = soundPool?.load(context, R.raw.sound_hit, 1) ?: -1
            hitCritSoundId = soundPool?.load(context, R.raw.sound_hit_crit, 1) ?: -1


            soundPool?.setOnLoadCompleteListener { _, sampleId, _ ->
                when (sampleId) {
                    buttonSoundId -> {
                        isButtonLoading = false
                    }
                    newGameSoundId -> {
                        isNewGameLoading = false
                    }
                    attackSoundId -> {
                        isAttackLoading = false
                    }
                    potionSoundId -> {
                        isPotionLoading = false
                    }
                    shieldSoundId -> {
                        isShieldLoading = false
                    }
                    shieldCritSoundId -> {
                        isShieldCritLoading = false
                    }
                    hitSoundId -> {
                        isHitLoading = false
                    }
                    hitCritSoundId -> {
                        isHitCritLoading = false
                    }
                }
            }
        }
    }

    // Ajustar volumen de los efectos (0f a 10f)
    fun setEffectsVolume(newVolume: Float) {
        // Normalizar el volumen de 0-10 a 0.0-1.0 para SoundPool
        effectsVolume = (newVolume / 10f).coerceIn(0f..1f)
    }

    // Reproducir el sonido del botón
    fun playSoundButton(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastPlayTimeButton < MIN_PLAY_INTERVAL || isButtonLoading) {
                    return@withLock
                }

                lastPlayTimeButton = currentTime
                isButtonLoading = true

                val pool = soundPool ?: run {
                    isButtonLoading = false
                    return@withLock
                }

                if (buttonSoundId != 0 && buttonSoundId != -1) {
                    // Usar effectsVolume para la reproducción
                    pool.play(buttonSoundId, effectsVolume, effectsVolume, 1, 0, 1f)
                } else {
                }
                isButtonLoading = false
            }
        }
    }

    // Reproducir el sonido de nueva partida
    fun playSoundNewgame(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastPlayTimeNewGame < MIN_PLAY_INTERVAL || isNewGameLoading) {
                    return@withLock
                }

                lastPlayTimeNewGame = currentTime
                isNewGameLoading = true

                val pool = soundPool ?: run {
                    isNewGameLoading = false
                    return@withLock
                }

                if (newGameSoundId != 0 && newGameSoundId != -1) {
                    // Usar effectsVolume para la reproducción
                    pool.play(newGameSoundId, effectsVolume, effectsVolume, 1, 0, 1f)
                } else {
                }
                isNewGameLoading = false
            }
        }
    }

    // Reproducir el sonido de ataque
    fun playSoundAttack(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastPlayTimeAttack < MIN_PLAY_INTERVAL || isAttackLoading) {
                    return@withLock
                }

                lastPlayTimeAttack = currentTime
                isAttackLoading = true

                val pool = soundPool ?: run {
                    isAttackLoading = false
                    return@withLock
                }

                if (attackSoundId != 0 && attackSoundId != -1) {
                    pool.play(attackSoundId, effectsVolume, effectsVolume, 1, 0, 1f)
                } else {
                }
                isAttackLoading = false
            }
        }
    }

    // Reproducir el sonido de poción
    fun playSoundPotion(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastPlayTimePotion < MIN_PLAY_INTERVAL || isPotionLoading) {
                    return@withLock
                }

                lastPlayTimePotion = currentTime
                isPotionLoading = true

                val pool = soundPool ?: run {
                    isPotionLoading = false
                    return@withLock
                }

                if (potionSoundId != 0 && potionSoundId != -1) {
                    pool.play(potionSoundId, effectsVolume, effectsVolume, 1, 0, 1f)
                } else {
                }
                isPotionLoading = false
            }
        }
    }

    // Reproducir el sonido de escudo
    fun playSoundShield(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastPlayTimeShield < MIN_PLAY_INTERVAL || isShieldLoading) {
                    return@withLock
                }

                lastPlayTimeShield = currentTime
                isShieldLoading = true

                val pool = soundPool ?: run {
                    isShieldLoading = false
                    return@withLock
                }

                if (shieldSoundId != 0 && shieldSoundId != -1) {
                    pool.play(shieldSoundId, effectsVolume, effectsVolume, 1, 0, 1f)
                } else {
                }
                isShieldLoading = false
            }
        }
    }

    // Reproducir el sonido de escudo crítico
    fun playSoundShieldCrit(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastPlayTimeShieldCrit < MIN_PLAY_INTERVAL || isShieldCritLoading) {
                    return@withLock
                }

                lastPlayTimeShieldCrit = currentTime
                isShieldCritLoading = true

                val pool = soundPool ?: run {
                    isShieldCritLoading = false
                    return@withLock
                }

                if (shieldCritSoundId != 0 && shieldCritSoundId != -1) {
                    pool.play(shieldCritSoundId, effectsVolume, effectsVolume, 1, 0, 1f)
                } else {
                }
                isShieldCritLoading = false
            }
        }
    }

    // Reproducir el sonido de golpe
    fun playSoundHit(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastPlayTimeHit < MIN_PLAY_INTERVAL || isHitLoading) {
                    return@withLock
                }

                lastPlayTimeHit = currentTime
                isHitLoading = true

                val pool = soundPool ?: run {
                    isHitLoading = false
                    return@withLock
                }

                if (hitSoundId != 0 && hitSoundId != -1) {
                    pool.play(hitSoundId, effectsVolume, effectsVolume, 1, 0, 1f)
                } else {
                }
                isHitLoading = false
            }
        }
    }

    // Reproducir el sonido de golpe crítico
    fun playSoundHitCrit(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastPlayTimeHitCrit < MIN_PLAY_INTERVAL || isHitCritLoading) {
                    return@withLock
                }

                lastPlayTimeHitCrit = currentTime
                isHitCritLoading = true

                val pool = soundPool ?: run {
                    isHitCritLoading = false
                    return@withLock
                }

                if (hitCritSoundId != 0 && hitCritSoundId != -1) {
                    pool.play(hitCritSoundId, effectsVolume, effectsVolume, 1, 0, 1f)
                } else {
                }
                isHitCritLoading = false
            }
        }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}