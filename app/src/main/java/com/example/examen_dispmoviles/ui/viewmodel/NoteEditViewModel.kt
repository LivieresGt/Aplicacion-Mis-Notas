package com.example.examen_dispmoviles.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen_dispmoviles.data.model.Nota // <-- Importa tu Nota
import com.example.examen_dispmoviles.data.repository.NotaRepository // <-- Importa tu NotaRepository
import com.example.examen_dispmoviles.ui.uistate.NoteEditUiState // <-- Importa tu NoteEditUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Añadir/Editar Nota.
 * Gestiona la carga de una nota para edición, la entrada de datos, validación y el guardado.
 */
class NoteEditViewModel(private val repository: NotaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteEditUiState())
    val uiState: StateFlow<NoteEditUiState> = _uiState.asStateFlow()

    /**
     * Inicializa el ViewModel para una nueva nota o para editar una existente.
     * Se llama cuando la pantalla de edición se abre.
     * @param noteId ID de la nota a editar (0 o null para una nueva nota).
     */
    fun initialize(noteId: Int?) {
        // Si ya estamos cargando o editando la misma nota, no hacemos nada.
        // Esto previene cargas duplicadas en cambios de configuración.
        if (_uiState.value.isLoading && noteId == _uiState.value.id) {
            return
        }

        if (noteId != null && noteId != 0) {
            // Cargar nota existente para edición
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null,
                    saveSuccess = false, // Resetear estado de guardado
                    isNewNote = false
                )
                try {
                    val existingNote = repository.obtenerNotaPorId(noteId)
                    if (existingNote != null) {
                        _uiState.value = _uiState.value.copy(
                            id = existingNote.id,
                            title = existingNote.titulo,
                            content = existingNote.contenido,
                            isCompleted = existingNote.completada,
                            fechaCreacion = existingNote.fechaCreacion, // <-- ¡Línea corregida!
                            isLoading = false,
                            errorMessage = null,
                            isNewNote = false
                        )
                    } else {
                        // Si la nota no se encuentra (ej. fue eliminada por otro lado)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Nota no encontrada para editar.",
                            isNewNote = true // Si no se encuentra, tratamos como nueva
                        )
                    }
                } catch (e: Exception) {
                    // Manejo de error de persistencia
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Error al cargar nota para edición: ${e.message}",
                        isNewNote = true // Si hay error al cargar, también tratamos como nueva
                    )
                }
            }
        } else {
            // Inicializar para una nueva nota
            _uiState.value = NoteEditUiState(isNewNote = true)
        }
    }

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title, errorMessage = null)
    }

    fun updateContent(content: String) {
        _uiState.value = _uiState.value.copy(content = content, errorMessage = null)
    }

    fun updateCompleted(isCompleted: Boolean) {
        _uiState.value = _uiState.value.copy(isCompleted = isCompleted)
    }

    /**
     * Valida la entrada de datos y guarda la nota (inserta o actualiza) en la base de datos.
     * Muestra indicadores de carga y mensajes de error/éxito en el UIState.
     * @return True si la operación fue exitosa, false en caso contrario (ej. validación fallida, error de BD).
     */
    suspend fun saveNote(): Boolean {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null, saveSuccess = false)

        // Validaciones (Cumpliendo el requisito: "manejar correctamente la entrada de datos y validar")
        if (_uiState.value.title.isBlank()) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "El título no puede estar vacío."
            )
            return false
        }
        if (_uiState.value.content.length < 5) { // Ejemplo: contenido mínimo de 5 caracteres
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "El contenido debe tener al menos 5 caracteres."
            )
            return false
        }

        try {
            // Crear el objeto Nota a partir del estado actual del UIState
            val noteToSave = Nota(
                id = _uiState.value.id,
                titulo = _uiState.value.title.trim(), // Elimina espacios en blanco al inicio/final
                contenido = _uiState.value.content.trim(),
                completada = _uiState.value.isCompleted,
                // Si es una nueva nota, asigna la fecha actual.
                // Si es una nota existente, mantiene la fecha original de creación del UiState.
                fechaCreacion = if (_uiState.value.isNewNote) System.currentTimeMillis() else _uiState.value.fechaCreacion
            )

            // Determina si se inserta o se actualiza
            if (_uiState.value.isNewNote) {
                repository.insertarNota(noteToSave)
            } else {
                repository.actualizarNota(noteToSave)
            }

            // Actualiza el UIState para reflejar el éxito
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = null,
                saveSuccess = true
            )
            return true
        } catch (e: Exception) {
            // Manejo de excepciones de persistencia (ej. error de base de datos)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "Error al guardar nota: ${e.message}",
                saveSuccess = false
            )
            return false
        }
    }

    /**
     * Reinicia los flags de éxito/error de guardado para la siguiente operación.
     */
    fun resetSaveState() {
        _uiState.value = _uiState.value.copy(saveSuccess = false, errorMessage = null)
    }
}