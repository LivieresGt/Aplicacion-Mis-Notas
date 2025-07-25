package com.example.examen_dispmoviles.ui.uistate

import com.example.examen_dispmoviles.data.model.Nota

/**
 * Representa el estado actual de la UI de la pantalla de Lista de Notas.
 */
data class NoteListUiState(
    val notes: List<Nota> = emptyList(), // Lista de notas a mostrar
    val isLoading: Boolean = false, // Indica si se están cargando las notas
    val errorMessage: String? = null // Mensaje de error (si hay)
)