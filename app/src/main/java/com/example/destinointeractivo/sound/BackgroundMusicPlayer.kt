// BackgroundMusicPlayer.kt
package com.example.destinointeractivo.sound

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.RawRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.destinointeractivo.MyApplication
import com.example.destinointeractivo.R

object BackgroundMusicPlayer : DefaultLifecycleObserver {

    private var mediaPlayer: MediaPlayer? = null
    private var currentMusicResId: Int = -1
    private var currentVolume: Int = 5
    private var wasPlayingBeforePause: Boolean = false // Para saber si estaba sonando antes de un onStop

    private const val MAX_VOLUME_DB_VALUE = 10 // Valor máximo de volumen en la DB (0-10)

    fun initialize(context: Context) {
        if (mediaPlayer == null) {
            try {
                mediaPlayer = MediaPlayer.create(context, R.raw.music_alegre_03)
                mediaPlayer?.isLooping = true
                Log.d("BGMusicPlayer", "MediaPlayer initialized.")
                setMusicVolume(currentVolume)
            } catch (e: Exception) {
                Log.e("BGMusicPlayer", "Error initializing MediaPlayer: ${e.message}")
                mediaPlayer = null
            }
        }
    }

    fun setMusicVolume(volume: Int) {
        currentVolume = volume.coerceIn(0, MAX_VOLUME_DB_VALUE) // Asegura que esté en el rango
        val volumeFloat = currentVolume / MAX_VOLUME_DB_VALUE.toFloat() // Convertir a 0.0-1.0
        try {
            mediaPlayer?.setVolume(volumeFloat, volumeFloat)
            Log.d("BGMusicPlayer", "Music volume set to DB value: $volume (Float: $volumeFloat)")
        } catch (e: IllegalStateException) {
            Log.e("BGMusicPlayer", "IllegalStateException setting volume: ${e.message}. MediaPlayer state invalid.")
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

                if (mediaPlayer == null) {

                }

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
                playMusic(R.raw.music_alegre_03) // Música por defecto
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

    override fun onStop(owner: LifecycleOwner) {
        Log.d("BGMusicPlayer", "Lifecycle onStop called. Pausing music.")
        pauseMusic()
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.d("BGMusicPlayer", "Lifecycle onStart called. Resuming music.")
        resumeMusic()
    }
}