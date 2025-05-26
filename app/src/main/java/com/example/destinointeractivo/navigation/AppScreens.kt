package com.example.destinointeractivo.navigation

sealed class AppScreens(val route: String) {
    object MainScreen: AppScreens("MainScreen")
    object Ajustes: AppScreens("Ajustes")
    object Combat_001: AppScreens("Combat_001")
    object Combat_002: AppScreens("Combat_002")
    object Combat_003: AppScreens("Combat_003")
    object Combat_004: AppScreens("Combat_004")
    object Combat_005: AppScreens("Combat_005")
    object VictoriaScreen: AppScreens("VictoriaScreen")
    object DerrotaScreen: AppScreens("DerrotaScreen")
    object Combat_001_Victory: AppScreens("Combat_001_Victory")
    object Combat_002_Victory: AppScreens("Combat_002_Victory")
    object Combat_003_Victory: AppScreens("Combat_003_Victory")
    object Combat_004_Victory: AppScreens("Combat_004_Victory")

    object WeaponSelectionScreen: AppScreens("WeaponSelectionScreen")



}