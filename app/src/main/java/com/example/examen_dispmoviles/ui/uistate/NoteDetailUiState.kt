package com.example.examen_dispmoviles.ui.uistate

import com.example.examen_dispmoviles.data.model.Nota

/**
 * Representa el estado actual de la UI de la pantalla de Detalle de Nota.
 */
data class NoteDetailUiState(
    val note: Nota? = null, // La nota específica a mostrar (puede ser null si no se encuentra)
    val isLoading: Boolean = false, // Indica si se está cargando la nota
    val errorMessage: String? = null, // Mensaje de error (si hay)
    val noteFound: Boolean = false // Indica si la nota fue encontrada exitosamente
)