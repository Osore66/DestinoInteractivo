package com.example.destinointeractivo

import android.app.Application
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.destinointeractivo.data.AppDatabase
import com.example.destinointeractivo.data.PlayerDao
// No necesitas PlayerViewModel si solo accedes a datos del DAO
// import com.example.destinointeractivo.viewmodel.PlayerViewModel

import kotlinx.coroutines.GlobalScope // ¡Necesitas esta importación!
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch // ¡Necesitas esta importación!

class MyApplication : Application() {

    private lateinit var playerDao: PlayerDao

    override fun onCreate() {
        super.onCreate()
        Log.d("MyApplication", "Application onCreate: Initializing SoundPlayer and BackgroundMusicPlayer.")

        SoundPlayer.init(this)
        BackgroundMusicPlayer.initialize(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(BackgroundMusicPlayer)

        // CAMBIO CLAVE AQUÍ: Pasando GlobalScope
        playerDao = AppDatabase.getDatabase(this, GlobalScope).playerDao()

        // El resto de tu corrutina para cargar volúmenes
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val settings = playerDao.getPlayerSettings()
                if (settings != null) {
                    val musicVolume = settings.musicVolume.toFloat()
                    val effectsVolume = settings.effectsVolume.toFloat()

                    Log.d("MyApplication", "Loaded volumes from DB: Music=$musicVolume, Effects=$effectsVolume")

                    BackgroundMusicPlayer.setMusicVolume(musicVolume)
                    SoundPlayer.setEffectsVolume(effectsVolume)
                } else {
                    Log.d("MyApplication", "No player settings found in DB. Using default volumes.")
                    BackgroundMusicPlayer.setMusicVolume(10f)
                    SoundPlayer.setEffectsVolume(10f)
                }
            } catch (e: Exception) {
                Log.e("MyApplication", "Error loading player settings on startup: ${e.message}")
                BackgroundMusicPlayer.setMusicVolume(10f)
                SoundPlayer.setEffectsVolume(10f)
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