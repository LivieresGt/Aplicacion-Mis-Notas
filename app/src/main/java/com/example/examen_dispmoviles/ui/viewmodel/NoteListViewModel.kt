package com.example.examen_dispmoviles.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen_dispmoviles.data.repository.NotaRepository // importo el noterepository y asi
import com.example.examen_dispmoviles.ui.uistate.NoteListUiState // lo mismo pero el notelistuistate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Lista de Notas.
 * Gestiona la carga y eliminación de notas, y expone el estado de la UI.
 */
class NoteListViewModel(private val repository: NotaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteListUiState())
    val uiState: StateFlow<NoteListUiState> = _uiState.asStateFlow()

    init {
        // Al inicializar el ViewModel, comenzamos a observar las notas del repositorio
        // Cada vez que el repositorio emita una nueva lista (por cambios en la BD),
        // el _uiState se actualizará y la UI se redibujará.
        viewModelScope.launch {
            repository.obtenerTodasNotas()
                .catch { e ->
                    // Aca es por si hay un error gracias Maubet por enseñarme a manejar errores
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Error al cargar notas: ${e.message}"
                    )
                }
                .collect { notes ->
                    _uiState.value = _uiState.value.copy(
                        notes = notes,
                        isLoading = false,
                        errorMessage = null
                    )
                }
        }
    }

    fun deleteNote(notaId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val noteToDelete = repository.obtenerNotaPorId(notaId)
                if (noteToDelete != null) {
                    repository.eliminarNota(noteToDelete)
                    // No necesitamos actualizar _uiState.notes aquí porque el Flow del repositorio
                    // nos lo actualizará automáticamente cuando la eliminación se refleje en la BD.
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Nota no encontrada para eliminar."
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al eliminar nota: ${e.message}"
                )
            }
        }
    }

    // Puedes añadir más funciones aquí si la lista de notas necesita más lógica
    // como filtrar, ordenar, etc.
}