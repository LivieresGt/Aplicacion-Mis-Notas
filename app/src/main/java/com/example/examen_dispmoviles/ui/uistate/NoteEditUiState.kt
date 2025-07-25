package com.example.examen_dispmoviles.ui.uistate

/**
 * Representa el estado actual de la UI de la pantalla de Añadir/Editar Nota.
 */
data class NoteEditUiState(
    val id: Int = 0, // ID de la nota (0 para nueva, >0 para editar)
    val title: String = "",
    val content: String = "",
    val isCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null, // Mensaje de error de validación/persistencia
    val saveSuccess: Boolean = false, // Indica si la nota se guardó exitosamente
    val isNewNote: Boolean = true, // True si es una nueva nota, false si es edición
    val fechaCreacion: Long = 0L // <-- ¡Añade esta línea!
)