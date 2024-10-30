package com.example.destinointeractivo

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VibrationViewModel(private val context: Context) : ViewModel() {
    var vibracionActiva = mutableStateOf(false)
        private set

    init {
        // Cargar el estado de vibración desde SharedPreferences al iniciar el ViewModel
        val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        vibracionActiva.value = sharedPreferences.getBoolean("vibracionActiva", false)
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
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(30)
            }
        }
    }
}
