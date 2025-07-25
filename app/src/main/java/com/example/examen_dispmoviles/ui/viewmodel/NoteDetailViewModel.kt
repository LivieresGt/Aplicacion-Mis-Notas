package com.example.examen_dispmoviles.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen_dispmoviles.data.repository.NotaRepository
import com.example.examen_dispmoviles.ui.uistate.NoteDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Detalle de Nota.
 * Carga una nota específica y expone su estado de UI.
 */
class NoteDetailViewModel(private val repository: NotaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteDetailUiState())
    val uiState: StateFlow<NoteDetailUiState> = _uiState.asStateFlow()

    /**
     * Carga una nota por su ID.
     * @param noteId El ID de la nota a cargar.
     */
    fun loadNote(noteId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null, noteFound = false)
            try {
                val note = repository.obtenerNotaPorId(noteId)
                _uiState.value = _uiState.value.copy(
                    note = note,
                    isLoading = false,
                    noteFound = (note != null), // True si la nota fue encontrada
                    errorMessage = if (note == null) "Nota no encontrada." else null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar nota: ${e.message}",
                    noteFound = false
                )
            }
        }
    }
}