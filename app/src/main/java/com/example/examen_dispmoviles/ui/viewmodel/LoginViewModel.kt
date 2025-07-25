package com.example.examen_dispmoviles.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.examen_dispmoviles.ui.uistate.LoginUiState  // Importar el LoginUiState xd (para acordarme)

/**
 * ViewModel para la pantalla de Login/Inicio.
 * Gestiona el estado de la UI relacionado con la autenticación.
 */
class LoginViewModel : ViewModel() {

    // MutableStateFlow es un StateFlow mutable que podemos modificar.
    // asStateFlow() expone una versión inmutable (StateFlow) que solo puede ser leída.
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username, errorMessage = null)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password, errorMessage = null)
    }

    // Función para simular un proceso de login.
    // En una aplicación real, aquí interactuarías con un repositorio de autenticación.
    fun login() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        // Simulación de login exitoso o fallido
        if (_uiState.value.username == "user" && _uiState.value.password == "pass") {
            _uiState.value = _uiState.value.copy(isLoading = false, loginSuccess = true)
        } else {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "Credenciales incorrectas. Intenta 'user' y 'pass'.",
                loginSuccess = false
            )
        }
    }

    // Reinicia el estado del login después de una navegación o para intentar de nuevo
    fun resetLoginState() {
        _uiState.value = LoginUiState()
    }
}