package com.example.examen_dispmoviles.ui.uistate

/**
 * Representa el estado actual de la UI de la pantalla de Configuración.
 * Aquí podríamos añadir opciones de configuración en el futuro si las hubiera.
 */
data class SettingsUiState(
    val appVersion: String = "1.0.0", // Ejemplo: versión de la app
    val showPrivacyPolicy: Boolean = false // Ejemplo: si el usuario debe ver la política de privacidad
)