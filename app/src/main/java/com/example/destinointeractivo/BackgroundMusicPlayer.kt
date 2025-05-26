// BackgroundMusicPlayer.kt
package com.example.destinointeractivo

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.RawRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

object BackgroundMusicPlayer : DefaultLifecycleObserver {

    private var mediaPlayer: MediaPlayer? = null
    private var currentMusicResId: Int = -1
    private var currentVolume: Int = 5 // Guardar el volumen actual como Int (0-10)
    private var wasPlayingBeforePause: Boolean = false // Para saber si estaba sonando antes de un onStop

    private const val MAX_VOLUME_DB_VALUE = 10 // Valor máximo de volumen en la DB (0-10)

    fun initialize(context: Context) {
        if (mediaPlayer == null) {
            try {
                // Initialize with a default track to avoid NullPointerException on first volume set
                mediaPlayer = MediaPlayer.create(context, R.raw.music_alegre_03) // Puedes usar cualquier pista inicial
                mediaPlayer?.isLooping = true
                Log.d("BGMusicPlayer", "MediaPlayer initialized.")
                // Apply the last known volume if it exists, otherwise use default
                setMusicVolume(currentVolume)
            } catch (e: Exception) {
                Log.e("BGMusicPlayer", "Error initializing MediaPlayer: ${e.message}")
                mediaPlayer = null // Asegurarse de que sea null si falla la creación
            }
        }
    }

    // Acepta un Int de 0 a 10 y lo convierte a Float de 0.0 a 1.0 para MediaPlayer
    fun setMusicVolume(volume: Int) {
        currentVolume = volume.coerceIn(0, MAX_VOLUME_DB_VALUE) // Asegura que esté en el rango
        val volumeFloat = currentVolume / MAX_VOLUME_DB_VALUE.toFloat() // Convertir a 0.0-1.0
        try {
            mediaPlayer?.setVolume(volumeFloat, volumeFloat)
            Log.d("BGMusicPlayer", "Music volume set to DB value: $volume (Float: $volumeFloat)")
        } catch (e: IllegalStateException) {
            Log.e("BGMusicPlayer", "IllegalStateException setting volume: ${e.message}. MediaPlayer state invalid.")
            // Considerar re-inicializar o manejar el error aquí si el estado es crítico
        } catch (e: Exception) {
            Log.e("BGMusicPlayer", "Error setting music volume: ${e.message}")
        }
    }

    fun playMusic(@RawRes resId: Int) {
        try {
            // Si la música ya está sonando y es la misma, no hacer nada
            if (mediaPlayer?.isPlaying == true && currentMusicResId == resId) {
                Log.d("BGMusicPlayer", "Music is already playing and is the same. Not restarting.")
                return
            }

            // Si es una música diferente o no está sonando, reinicia
            if (currentMusicResId != resId || mediaPlayer?.isPlaying == false) {
                mediaPlayer?.stop()
                mediaPlayer?.release() // Liberar el antiguo para cargar el nuevo
                mediaPlayer = null // Importante para que initialize cree uno nuevo
                Log.d("BGMusicPlayer", "Stopping and releasing old MediaPlayer for new music.")

                // Crear y preparar el nuevo MediaPlayer
                // Context needs to be application context from MyApplication.
                // Assuming initialize() already provides it or we pass it here.
                // For simplicity, using a static context from the init.
                // BEST PRACTICE: Pass context to playMusic or ensure it's always available.
                // For this Singleton, rely on `initialize` being called from MyApplication.
                // If this is called *before* init, it will crash.
                // A safer approach for a singleton would be to pass the application context
                // to playMusic as well, or have a getApplicationContext() method.
                // For now, let's assume initialize is called first.

                // Re-initialize only if it's null (e.g., after release)
                if (mediaPlayer == null) {
                    // Need to ensure context is not null. It should be from MyApplication's init.
                    // If playMusic is called without initialize, this will fail.
                    // A better way is to pass context into playMusic or get it from a global source.
                    // For now, assuming initialize is called first.
                    // If you face context related issues, consider passing context here:
                    // MediaPlayer.create(context, resId)
                    // For the sake of not adding new params, let's assume it's created if null.
                    // This implies `initialize` already created it.
                    // If it was null because of an error, it needs a valid context to create.
                }

                // If mediaPlayer is null (e.g., after previous release), try to create.
                // If it was already playing a different song and released, we need to create it again.
                // The `initialize` method is for *initial* setup, not for recreating.
                // Let's adapt playMusic to create directly if needed.

                // Usa la nueva forma de acceder al contexto
                mediaPlayer = MediaPlayer.create(MyApplication.applicationContext, resId)
                mediaPlayer?.isLooping = true
                currentMusicResId = resId
                Log.d("BGMusicPlayer", "New MediaPlayer created for resId: $resId")

                setMusicVolume(currentVolume)
                mediaPlayer?.start()
                Log.d("BGMusicPlayer", "Music playback started for resId: $resId")
            }
        } catch (e: Exception) {
            Log.e("BGMusicPlayer", "Error playing music (resId: $resId): ${e.message}")
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            Log.e("BGMusicPlayer", "Error playing music (resId: $resId): ${e.message}")
        }
    }

    fun pauseMusic() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                wasPlayingBeforePause = true
                Log.d("BGMusicPlayer", "Music paused.")
            } else {
                wasPlayingBeforePause = false
            }
        } catch (e: IllegalStateException) {
            Log.e("BGMusicPlayer", "IllegalStateException pausing music: ${e.message}. MediaPlayer state invalid.")
        } catch (e: Exception) {
            Log.e("BGMusicPlayer", "Error pausing music: ${e.message}")
        }
    }

    fun resumeMusic() {
        try {
            if (mediaPlayer?.isPlaying == false && wasPlayingBeforePause) {
                mediaPlayer?.start()
                Log.d("BGMusicPlayer", "Music resumed.")
            } else if (mediaPlayer == null) {
                Log.d("BGMusicPlayer", "MediaPlayer is null on resume, attempting to play default music.")
                // If somehow it's null, try to play the default music again.
                // This might indicate an issue with initialization/release.
                playMusic(R.raw.music_alegre_03) // Default track
            }
        } catch (e: IllegalStateException) {
            Log.e("BGMusicPlayer", "IllegalStateException resuming music: ${e.message}. MediaPlayer state invalid.")
        } catch (e: Exception) {
            Log.e("BGMusicPlayer", "Error resuming music: ${e.message}")
        }
    }

    fun release() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            currentMusicResId = -1
            Log.d("BGMusicPlayer", "MediaPlayer released.")
        } catch (e: IllegalStateException) {
            Log.e("BGMusicPlayer", "IllegalStateException releasing MediaPlayer: ${e.message}. Already in an invalid state.")
        } catch (e: Exception) {
            Log.e("BGMusicPlayer", "Error releasing MediaPlayer: ${e.message}")
        }
    }

    // Implementación de DefaultLifecycleObserver
    override fun onStop(owner: LifecycleOwner) {
        Log.d("BGMusicPlayer", "Lifecycle onStop called. Pausing music.")
        pauseMusic()
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.d("BGMusicPlayer", "Lifecycle onStart called. Resuming music.")
        resumeMusic()
    }

    // Para obtener el contexto de la aplicación para MediaPlayer.create
    // Necesitas una clase `App` o similar para almacenar el contexto global.
    // O puedes pasar el contexto a `playMusic`
    // (Ver el snippet de App.kt más abajo)
}