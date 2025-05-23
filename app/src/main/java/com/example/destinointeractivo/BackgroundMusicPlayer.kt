package com.example.destinointeractivo

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object BackgroundMusicPlayer : LifecycleObserver {
    private var mediaPlayer: MediaPlayer? = null
    private var volume = 1f
    private var currentMusicResId = -1
    private var isMuted = false
    private var currentPlaybackPosition = 0
    private var applicationContext: Context? = null

    // Inicializa el reproductor con el contexto de la aplicación
    fun initialize(context: Context) {
        if (applicationContext == null) {
            applicationContext = context.applicationContext
            Log.d("BackgroundMusicPlayer", "Initialized with application context.")
        }
    }

    // Reproducir música de fondo. Cambia la música si es diferente a la actual.
    fun playMusic(musicResId: Int) {
        val context = applicationContext
        if (context == null) {
            Log.e("BackgroundMusicPlayer", "Context not initialized. Cannot play music.")
            return
        }

        // Si es la misma música y ya está sonando, no hacemos nada.
        if (currentMusicResId == musicResId && mediaPlayer?.isPlaying == true) {
            Log.d("BackgroundMusicPlayer", "Music $musicResId is already playing. Skipping.")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("BackgroundMusicPlayer", "Attempting to play music: $musicResId")
            // Detener y liberar el MediaPlayer actual si existe y es una canción diferente
            if (mediaPlayer != null && currentMusicResId != musicResId) {
                Log.d("BackgroundMusicPlayer", "Stopping current music ($currentMusicResId) to play new ($musicResId).")
                stopMusic()
            }

            // Si es la misma canción pero estaba pausada, reanudar
            if (mediaPlayer != null && currentMusicResId == musicResId && !mediaPlayer!!.isPlaying) {
                mediaPlayer?.run {
                    Log.d("BackgroundMusicPlayer", "Resuming music $musicResId from position $currentPlaybackPosition.")
                    seekTo(currentPlaybackPosition)
                    // Asegurarse de que el volumen actual de la app se aplique al reanudar
                    setVolume(volume, volume) // <-- CORREGIDO: Añadido 'mediaPlayer?.'
                    start()
                }
                return@launch // Importante: si reanudamos, no creamos un nuevo MediaPlayer
            }

            // Crear una nueva instancia si es una canción diferente o no había MediaPlayer
            try {
                mediaPlayer = MediaPlayer.create(context, musicResId)?.apply {
                    setOnCompletionListener {
                        Log.d("BackgroundMusicPlayer", "Music $musicResId completed. Looping.")
                        seekTo(0)
                        start() // Loop infinito
                    }
                    // Aplicar el volumen actual al crear y empezar a reproducir
                    setVolume(volume, volume) // <-- CORREGIDO: Añadido 'mediaPlayer?.'
                    isLooping = true
                    start()
                    Log.d("BackgroundMusicPlayer", "Started playing new music: $musicResId.")
                }
                currentMusicResId = musicResId
                currentPlaybackPosition = 0 // Reiniciar posición al cambiar de canción
            } catch (e: Exception) {
                Log.e("BackgroundMusicPlayer", "Error creating MediaPlayer for $musicResId: ${e.message}")
                mediaPlayer = null
                currentMusicResId = -1
                currentPlaybackPosition = 0
            }
        }
    }

    // Detener música y liberar recursos
    fun stopMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            Log.d("BackgroundMusicPlayer", "Stopped and released MediaPlayer.")
        }
        mediaPlayer = null
        currentMusicResId = -1
        currentPlaybackPosition = 0
    }

    // Pausar música y guardar posición actual
    fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                currentPlaybackPosition = it.currentPosition
                it.pause()
                Log.d("BackgroundMusicPlayer", "Music paused at position $currentPlaybackPosition.")
            }
        }
    }

    // Reanudar música desde la última posición
    fun resumeMusic() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.seekTo(currentPlaybackPosition)
                // Asegurarse de que el volumen actual de la app se aplique al reanudar
                it.setVolume(volume, volume) // <-- CORREGIDO: Añadido 'it.'
                it.start()
                Log.d("BackgroundMusicPlayer", "Music resumed from position $currentPlaybackPosition.")
            }
        }
    }

    // Ajustar volumen del reproductor de música de fondo (0f a 10f)
    fun setMusicVolume(newVolume: Float) {
        // Normalizar el volumen de 0-10 a 0.0-1.0 para MediaPlayer
        volume = (newVolume / 10f).coerceIn(0f..1f)
        mediaPlayer?.setVolume(volume, volume)
        Log.d("BackgroundMusicPlayer", "Music volume set to $newVolume (normalized: $volume).")
    }

    // Silenciar música
    fun toggleMute() {
        isMuted = !isMuted
        mediaPlayer?.setVolume(if (isMuted) 0f else volume, if (isMuted) 0f else volume)
        Log.d("BackgroundMusicPlayer", "Mute toggled. isMuted: $isMuted.")
    }

    // Liberar recursos al destruir la app
    fun release() {
        stopMusic()
        applicationContext = null
        Log.d("BackgroundMusicPlayer", "BackgroundMusicPlayer released all resources.")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        Log.d("BackgroundMusicPlayer", "App went to background (ON_STOP). Pausing music.")
        pauseMusic()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        Log.d("BackgroundMusicPlayer", "App came to foreground (ON_START).")
        if (currentMusicResId != -1 && applicationContext != null) {
            Log.d("BackgroundMusicPlayer", "Current music ($currentMusicResId) exists. Resuming.")
            resumeMusic()
        } else if (applicationContext != null) {
            Log.d("BackgroundMusicPlayer", "No current music. Playing default music (R.raw.music_alegre_03).")
            playMusic(R.raw.music_alegre_03)
        } else {
            Log.e("BackgroundMusicPlayer", "Cannot resume/play music: applicationContext is null.")
        }
    }
}