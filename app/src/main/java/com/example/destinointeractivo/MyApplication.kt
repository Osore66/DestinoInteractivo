package com.example.destinointeractivo

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.destinointeractivo.data.AppDatabase
import com.example.destinointeractivo.data.PlayerDao
import com.example.destinointeractivo.sound.BackgroundMusicPlayer
import com.example.destinointeractivo.sound.SoundPlayer

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApplication : Application() {

    private lateinit var playerDao: PlayerDao

    companion object {
        // Renombramos la propiedad para evitar confusión y usamos un "backing field"
        // para asegurarnos de que solo se asigne una vez y sea lateinit
        private lateinit var _applicationContext: Context
        val applicationContext: Context
            get() = _applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        // Asigna el contexto de la aplicación al backing field
        _applicationContext = this.applicationContext // ¡Aquí está la asignación correcta!

        Log.d("MyApplication", "Application onCreate: Initializing SoundPlayer and BackgroundMusicPlayer.")

        SoundPlayer.init(this)
        BackgroundMusicPlayer.initialize(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(BackgroundMusicPlayer)

        playerDao = AppDatabase.getDatabase(this, GlobalScope).playerDao()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val settings = playerDao.getPlayerSettings()
                if (settings != null) {
                    val musicVolumeInt = settings.musicVolume
                    val effectsVolumeInt = settings.effectsVolume

                    Log.d("MyApplication", "Loaded volumes from DB: Music=$musicVolumeInt, Effects=$effectsVolumeInt")

                    BackgroundMusicPlayer.setMusicVolume(musicVolumeInt)
                    SoundPlayer.setEffectsVolume(effectsVolumeInt.toFloat())
                } else {
                    Log.d("MyApplication", "No player settings found in DB. Using default volumes (5).")
                    BackgroundMusicPlayer.setMusicVolume(5)
                    SoundPlayer.setEffectsVolume(5f)
                }
            } catch (e: Exception) {
                Log.e("MyApplication", "Error loading player settings on startup: ${e.message}")
                BackgroundMusicPlayer.setMusicVolume(5)
                SoundPlayer.setEffectsVolume(5f)
            }
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d("MyApplication", "Application onTerminate: Releasing audio resources.")
        BackgroundMusicPlayer.release()
        SoundPlayer.release()
    }
}