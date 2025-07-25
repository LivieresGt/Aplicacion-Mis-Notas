package com.example.examen_dispmoviles.ui.screens // <-- Tu paquete

import androidx.compose.foundation.layout.Arrangement // <-- ¡ESTA ES LA LÍNEA AÑADIDA/VERIFICADA!
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row // <-- Row ya estaba, pero su error era por falta de Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examen_dispmoviles.AppViewModelProvider // <-- Importa tu ViewModel Factory
import com.example.examen_dispmoviles.ui.viewmodel.NoteEditViewModel // <-- Importa tu NoteEditViewModel
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
/**
 * Composable que representa la pantalla de Añadir/Editar Nota.
 *
 * @param navController El NavController para la navegación.
 * @param noteId El ID de la nota a editar. Si es 0 o null, se considera una nueva nota.
 * @param viewModel El ViewModel para gestionar la lógica y el estado de la edición de notas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    navController: NavController,
    noteId: Int?, // Recibimos el ID de la nota, puede ser nulo o 0 para nueva nota
    viewModel: NoteEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // 1. Recolecta el estado de la UI del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope() // Lo que agregue ahora
    // 2. Efecto para inicializar el ViewModel cuando se abre la pantalla
    LaunchedEffect(key1 = noteId) {
        // Llama a initialize para cargar la nota si noteId es válido, o para resetear si es nueva.
        viewModel.initialize(noteId)
    }

    // 3. Efecto para reaccionar al éxito del guardado
    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            // Si la nota se guardó exitosamente, vuelve a la pantalla anterior
            navController.popBackStack()
            viewModel.resetSaveState() // Resetea el flag para futuras operaciones
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.isNewNote) "Nueva Nota" else "Editar Nota") },
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Permite el scroll si el contenido es largo
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Indicador de carga
            if (uiState.isLoading) {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
            }

            // Mensaje de error
            uiState.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
            }

            // Campo de Título
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.updateTitle(it) },
                label = { Text("Título") },
                isError = uiState.errorMessage != null && uiState.errorMessage!!.contains("título"), // Validación simple
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            // Campo de Contenido
            OutlinedTextField(
                value = uiState.content,
                onValueChange = { viewModel.updateContent(it) },
                label = { Text("Contenido") },
                isError = uiState.errorMessage != null && uiState.errorMessage!!.contains("contenido"), // Validación simple
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // Altura fija para el área de texto
            )
            Spacer(Modifier.height(16.dp))

            // Switch para Completada
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Completada:")
                Switch(
                    checked = uiState.isCompleted,
                    onCheckedChange = { viewModel.updateCompleted(it) }
                )
            }
            Spacer(Modifier.height(24.dp))

            // Botón de Guardar Nota
            Button(
                onClick = {
                    // 1. Lanzamos una corrutina en el ámbito del Composable
                    scope.launch {
                        val success = viewModel.saveNote() // 2. Llamamos a la suspend fun del ViewModel
                        // La navegación de vuelta ya se maneja en el LaunchedEffect(uiState.saveSuccess)
                        // del ViewModel, si 'success' es true.
                    }
                },
                enabled = !uiState.isLoading, // Deshabilitar si está cargando
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Nota")
            }
        }
    }
}