package com.example.examen_dispmoviles.ui.viewmodel // <-- Tu paquete

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.examen_dispmoviles.ui.uistate.SettingsUiState // <-- Importa tu SettingsUiState

/**
 * ViewModel para la pantalla de Configuración.
 * Actualmente, solo expone información estática, pero podría manejar ajustes.
 */
class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    // Puedes añadir funciones aquí para cambiar configuraciones, si las hubiera.
    // Por ejemplo, para demostrar interactividad simple en la pantalla de Settings.
    fun togglePrivacyPolicyVisibility() {
        _uiState.value = _uiState.value.copy(showPrivacyPolicy = !_uiState.value.showPrivacyPolicy)
    }

    // Un ejemplo de función que podría actualizar alguna información.
    fun updateAppVersion(newVersion: String) {
        _uiState.value = _uiState.value.copy(appVersion = newVersion)
    }
}