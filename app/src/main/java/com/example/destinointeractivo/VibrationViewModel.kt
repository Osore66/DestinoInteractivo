package com.example.destinointeractivo

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VibrationViewModel(private val context: Context) : ViewModel() {
    var vibracionActiva = mutableStateOf(false) // Cambiar a false por defecto
        private set

    init {
        val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            vibracionActiva.value = true
            with(sharedPreferences.edit()) {
                putBoolean("vibracionActiva", true)
                putBoolean("isFirstRun", false)
                apply()
            }
        } else {
            vibracionActiva.value = sharedPreferences.getBoolean("vibracionActiva", false)
        }
    }


    fun setVibracionActiva(estado: Boolean) {
        vibracionActiva.value = estado
        // Guardar el estado en SharedPreferences
        val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("vibracionActiva", estado)
            apply()
        }
    }

    fun vibrate(context: Context) {
        if (vibracionActiva.value) {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(15)
            }
        }
    }
}
