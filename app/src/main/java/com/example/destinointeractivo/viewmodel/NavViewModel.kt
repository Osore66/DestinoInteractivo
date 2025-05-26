package com.example.destinointeractivo.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavViewModel : ViewModel() {
    var lastScreen = mutableStateOf("")
}
