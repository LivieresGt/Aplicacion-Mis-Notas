package com.example.examen_dispmoviles.ui.screens // <-- Tu paquete

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examen_dispmoviles.AppViewModelProvider // <-- Importa tu ViewModel Factory
import com.example.examen_dispmoviles.data.model.Nota // <-- Importa tu Nota
import com.example.examen_dispmoviles.ui.viewmodel.NoteListViewModel // <-- Importa tu ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.filled.Settings

/**
 * Composable que representa la pantalla de Lista de Notas.
 * @param navController El NavController para manejar la navegación.
 * @param viewModel El ViewModel para gestionar la lógica de la lista de notas y el estado.
 */
@OptIn(ExperimentalMaterial3Api::class) // Anotación necesaria para usar TopAppBar y Scaffold
@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // 1. Recolecta el estado de la UI del ViewModel.
    // Los cambios en uiState.value.notes harán que la lista se actualice automáticamente.
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Notas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = { // <-- ¡Añade este bloque 'actions'!
                    IconButton(onClick = { navController.navigate("settings_screen") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Configuración")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // 2. Navegar a la pantalla de añadir/editar nota sin ID (para una nueva nota)
                    navController.navigate("note_edit_screen?noteId=${0}")
                },
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Nota")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Importante aplicar el padding del Scaffold
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (uiState.isLoading) {
                // 3. Muestra un indicador de carga si los datos están siendo cargados.
                CircularProgressIndicator()
            } else if (uiState.errorMessage != null) {
                // 4. Muestra un mensaje de error si algo salió mal.
                Text(
                    text = uiState.errorMessage ?: "Error desconocido",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (uiState.notes.isEmpty()) {
                // 5. Mensaje si no hay notas.
                Text(text = "No tienes notas aún. ¡Crea una!", modifier = Modifier.padding(16.dp))
            } else {
                // 6. Si hay notas, las muestra en un LazyColumn.
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.notes) { note ->
                        NoteItem(
                            note = note,
                            onNoteClick = {
                                // Navegar al detalle de la nota
                                navController.navigate("note_detail_screen/${note.id}")
                            },
                            onDeleteClick = {
                                // Llamar al ViewModel para eliminar la nota
                                viewModel.deleteNote(note.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable que representa un solo elemento de nota en la lista.
 * @param note La Nota a mostrar.
 * @param onNoteClick Lambda que se invoca cuando se hace clic en la nota.
 * @param onDeleteClick Lambda que se invoca cuando se hace clic en el botón de eliminar.
 */
@Composable
fun NoteItem(
    note: Nota,
    onNoteClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNoteClick(note.id) }, // Hace que la tarjeta sea clickeable
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f) // Ocupa el espacio disponible
                )
                // Botón de eliminar
                IconButton(onClick = { onDeleteClick(note.id) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar nota", tint = Color.Gray)
                }
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = note.contenido,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis // Muestra puntos suspensivos si el texto es muy largo
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Creada: ${formatTimestamp(note.fechaCreacion)}", // Formatea la fecha
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

/**
 * Función de utilidad para formatear una marca de tiempo a un formato de fecha legible.
 */
@Composable
fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}