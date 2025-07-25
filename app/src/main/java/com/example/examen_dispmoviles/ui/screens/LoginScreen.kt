package com.example.examen_dispmoviles.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examen_dispmoviles.AppViewModelProvider // <-- Importa tu ViewModel Factory
import com.example.examen_dispmoviles.ui.viewmodel.LoginViewModel // <-- Importa tu ViewModel

/**
 * Composable que representa la pantalla de Login/Inicio.
 * @param navController El NavController para manejar la navegación.
 * @param viewModel El ViewModel para gestionar la lógica de login y el estado.
 */
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory) // Usa la factoría para obtener el ViewModel
) {
    // 1. Recolecta el estado de la UI del ViewModel como un State.
    // Cada vez que el uiState en el ViewModel cambie, este composable se redibujará.
    val uiState by viewModel.uiState.collectAsState()

    // 2. Efecto lateral: Reacciona a cambios en loginSuccess.
    // LaunchedEffect se ejecuta cuando las claves cambian (o una vez al inicio).
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            // Si el login fue exitoso, navegamos a la pantalla de lista de notas.
            // popUpTo hace que la pantalla de login se elimine de la pila de atrás,
            // así el usuario no puede volver a ella con el botón de atrás.
            navController.navigate("note_list_screen") {
                popUpTo("login_screen") { inclusive = true }
            }
            viewModel.resetLoginState() // Reinicia el estado para futuras navegaciones
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(32.dp))

        // Campo de usuario
        OutlinedTextField(
            value = uiState.username,
            onValueChange = { viewModel.updateUsername(it) }, // Actualiza el ViewModel con cada cambio
            label = { Text("Usuario") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Usuario") },
            singleLine = true,
            isError = uiState.errorMessage != null && !uiState.loginSuccess
        )
        Spacer(Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.updatePassword(it) }, // Actualiza el ViewModel con cada cambio
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña") },
            visualTransformation = PasswordVisualTransformation(), // Oculta el texto de la contraseña
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            isError = uiState.errorMessage != null && !uiState.loginSuccess
        )
        Spacer(Modifier.height(24.dp))

        // Botón de Login
        Button(
            onClick = { viewModel.login() }, // Llama a la función de login del ViewModel
            enabled = !uiState.isLoading // Deshabilita el botón si está cargando
        ) {
            Text("Entrar")
        }

        // Indicador de carga
        if (uiState.isLoading) {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator() // Muestra un círculo de progreso
        }

        // Mensaje de error
        uiState.errorMessage?.let { message ->
            Spacer(Modifier.height(16.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}