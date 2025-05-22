package com.example.destinointeractivo

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.util.Log // Importa Log para depuración
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object SoundPlayer {
    private const val MAX_STREAMS = 5
    private var soundPool: SoundPool? = null

    // IDs de cada sonido
    private var buttonSoundId = 0
    private var secondSoundId = 0

    // Estados de carga
    private val mutex = Mutex()
    private var lastPlayTimeButton = 0L
    private var lastPlayTimeSecond = 0L
    private var isButtonLoading = false
    private var isSecondLoading = false

    private const val MIN_PLAY_INTERVAL = 300L

    // Variable para el volumen de los efectos
    private var effectsVolume = 1f // Valor por defecto (0.0-1.0)

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
            secondSoundId = soundPool?.load(context, R.raw.sound_newgame, 1) ?: -1

            soundPool?.setOnLoadCompleteListener { _, sampleId, _ ->
                when (sampleId) {
                    buttonSoundId -> {
                        isButtonLoading = false
                        Log.d("SoundPlayer", "Button sound loaded.")
                    }
                    secondSoundId -> {
                        isSecondLoading = false
                        Log.d("SoundPlayer", "Second sound loaded.")
                    }
                }
            }
            Log.d("SoundPlayer", "SoundPool initialized and sounds loading.")
        }
    }

    // Ajustar volumen de los efectos (0f a 10f)
    fun setEffectsVolume(newVolume: Float) {
        // Normalizar el volumen de 0-10 a 0.0-1.0 para SoundPool
        effectsVolume = (newVolume / 10f).coerceIn(0f..1f)
        Log.d("SoundPlayer", "Effects volume set to $newVolume (normalized: $effectsVolume).")
    }

    // Reproducir el primer sonido
    fun playSoundButton(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastPlayTimeButton < MIN_PLAY_INTERVAL || isButtonLoading) {
                    Log.d("SoundPlayer", "Skipping button sound due to interval or loading.")
                    return@withLock
                }

                lastPlayTimeButton = currentTime
                isButtonLoading = true

                val pool = soundPool ?: run {
                    Log.e("SoundPlayer", "SoundPool is not initialized. Cannot play button sound.")
                    isButtonLoading = false
                    return@withLock
                }

                if (buttonSoundId != 0 && buttonSoundId != -1) {
                    // Usar effectsVolume para la reproducción
                    pool.play(buttonSoundId, effectsVolume, effectsVolume, 1, 0, 1f)
                    Log.d("SoundPlayer", "Played button sound with volume: $effectsVolume.")
                } else {
                    Log.e("SoundPlayer", "Button sound ID is invalid: $buttonSoundId.")
                }
                isButtonLoading = false
            }
        }
    }

    // Reproducir el segundo sonido
    fun playSoundNewgame(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastPlayTimeSecond < MIN_PLAY_INTERVAL || isSecondLoading) {
                    Log.d("SoundPlayer", "Skipping newgame sound due to interval or loading.")
                    return@withLock
                }

                lastPlayTimeSecond = currentTime
                isSecondLoading = true

                val pool = soundPool ?: run {
                    Log.e("SoundPlayer", "SoundPool is not initialized. Cannot play newgame sound.")
                    isSecondLoading = false
                    return@withLock
                }

                if (secondSoundId != 0 && secondSoundId != -1) {
                    // Usar effectsVolume para la reproducción
                    pool.play(secondSoundId, effectsVolume, effectsVolume, 1, 0, 1f)
                    Log.d("SoundPlayer", "Played newgame sound with volume: $effectsVolume.")
                } else {
                    Log.e("SoundPlayer", "Newgame sound ID is invalid: $secondSoundId.")
                }
                isSecondLoading = false
            }
        }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
        Log.d("SoundPlayer", "SoundPool released.")
    }
}