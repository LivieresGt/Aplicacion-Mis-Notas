package com.example.examen_dispmoviles.ui.screens // <-- Tu paquete

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examen_dispmoviles.AppViewModelProvider // <-- Importa tu ViewModel Factory
import com.example.examen_dispmoviles.ui.viewmodel.SettingsViewModel // <-- Importa tu SettingsViewModel

/**
 * Composable que representa la pantalla de Configuración.
 * @param navController El NavController para la navegación.
 * @param viewModel El ViewModel para gestionar la lógica y el estado de la configuración.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // 1. Recolecta el estado de la UI del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Información de la Aplicación",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Versión de la App: ${uiState.appVersion}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(16.dp))

            // Opción de configuración: Política de Privacidad
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mostrar informacion de la aplicacion",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f) // Ocupa el espacio disponible
                )
                Switch(
                    checked = uiState.showPrivacyPolicy,
                    onCheckedChange = { viewModel.togglePrivacyPolicyVisibility() }
                )
            }
            if (uiState.showPrivacyPolicy) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Esta aplicacion fue desarrollada por Karen Gimenez - Ronald Alonso - Javier Livieres (Especial agradecimiento a Gemini IA por ayudar a resolver errores). ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Spacer(Modifier.height(16.dp))

            // Puedes añadir más opciones de configuración aquí
        }
    }
}