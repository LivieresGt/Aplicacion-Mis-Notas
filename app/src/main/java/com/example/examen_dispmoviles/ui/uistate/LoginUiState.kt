package com.example.examen_dispmoviles.ui.uistate

/**
 * Representa el estado actual de la UI de la pantalla de Login/Inicio.
 */
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null, // Mensaje de error a mostrar (si hay)
    val loginSuccess: Boolean = false // Indica si el login fue exitoso
)