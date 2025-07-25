package com.example.examen_dispmoviles.ui.screens // <-- Tu paquete

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examen_dispmoviles.AppViewModelProvider // <-- Importa tu ViewModel Factory
import com.example.examen_dispmoviles.ui.viewmodel.NoteDetailViewModel // <-- Importa tu NoteDetailViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Composable que representa la pantalla de Detalle de Nota.
 *
 * @param navController El NavController para la navegación.
 * @param noteId El ID de la nota a mostrar. Debe ser un ID válido para cargar la nota.
 * @param viewModel El ViewModel para gestionar la lógica y el estado del detalle de nota.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    navController: NavController,
    noteId: Int?, // Recibimos el ID de la nota
    viewModel: NoteDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // 1. Recolecta el estado de la UI del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // 2. Efecto para cargar la nota cuando la pantalla se muestra o el ID cambia
    LaunchedEffect(key1 = noteId) {
        if (noteId != null && noteId != 0) {
            viewModel.loadNote(noteId)
        } else {
            // Si no hay noteId o es 0, no hay nota para mostrar, podemos volver o mostrar un error.
            // Para este caso, simplemente volvemos.
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Nota") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Botón de atrás
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        floatingActionButton = {
            // Si la nota se cargó correctamente, muestra un botón para editarla
            uiState.note?.let { note ->
                FloatingActionButton(
                    onClick = {
                        // Navegar a la pantalla de añadir/editar nota con el ID de la nota actual
                        navController.navigate("note_edit_screen?noteId=${note.id}")
                    },
                    modifier = Modifier.padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Nota")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Importante aplicar el padding del Scaffold
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage ?: "Error desconocido",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (uiState.noteFound && uiState.note != null) {
                // Muestra los detalles de la nota si se encontró
                val note = uiState.note!! // !! indica que confiamos en que no es null aquí
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = note.titulo,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = note.contenido,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Creada: ${formatTimestamp(note.fechaCreacion)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = if (note.completada) "Estado: Completada" else "Estado: Pendiente",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (note.completada) Color.Green.copy(alpha = 0.7f) else Color.Red.copy(alpha = 0.7f)
                    )
                }
            } else {
                // Caso si no se encuentra la nota o el ID es inválido (aunque el LaunchedEffect ya debería manejarlo)
                Text("Nota no disponible.")
            }
        }
    }
}

// Reutilizamos la función de formateo de fecha que ya tenemos en NoteListScreen
// Si ya la tienes en un archivo de utilidades, no es necesario duplicarla aquí.
// Pero para que este archivo sea autocontenido, la incluimos.
// @Composable
//fun formatTimestamp(timestamp: Long): String {
//    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
//    return sdf.format(Date(timestamp))
//} Comentamos todo esto porque me daba error de duplicado jeje