package com.example.examen_dispmoviles // <-- Tu paquete principal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examen_dispmoviles.AppDatabase // <-- ¡Importa tu AppDatabase! (aunque la inicialización global ya no se usa)
import com.example.examen_dispmoviles.ui.screens.LoginScreen // <-- Importa tu LoginScreen
import com.example.examen_dispmoviles.ui.screens.NoteListScreen // <-- Importa tu NoteListScreen
import com.example.examen_dispmoviles.ui.screens.NoteEditScreen // <-- ¡NUEVA MODIFICACIÓN: Importa tu NoteEditScreen!
import com.example.examen_dispmoviles.ui.theme.Examen_DISPMovilesTheme // <-- Tu tema de aplicación
import com.example.examen_dispmoviles.ui.screens.NoteDetailScreen
import com.example.examen_dispmoviles.ui.screens.SettingsScreen

// 1. Aquí es donde obtendremos la instancia de nuestra base de datos
// NOTA: Esta variable global 'database' ya no es necesaria si usas Inyección de Dependencias
// a través de AppViewModelProvider y MyApplication.
// La mantengo comentada por ahora, pero la eliminaremos en un paso futuro para limpiar.
// lateinit var database: AppDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el diseño de borde a borde para una UI moderna

        // 2. La inicialización de la base de datos ahora se hace en MyApplication.
        // Puedes eliminar esta línea, ya que MyApplication lo gestiona:
        // database = AppDatabase.getDatabase(applicationContext)

        setContent {
            Examen_DISPMovilesTheme { // Aplica el tema de tu aplicación
                // Una superficie de fondo que ocupe toda la pantalla
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 3. Configuración del sistema de navegación
                    MyAppNavHost()
                }
            }
        }
    }
}

/**
 * Define todas las rutas de navegación de la aplicación y la navegación entre pantallas.
 */
@Composable
fun MyAppNavHost() {
    val navController = rememberNavController() // Crea y recuerda el controlador de navegación

    NavHost(navController = navController, startDestination = "login_screen") { // Define el gráfico de navegación
        // Rutas de las pantallas
        composable("login_screen") {
            LoginScreen(navController = navController) // Llamada al Composable de Login

            // Estas líneas de navegación temporal ya NO son necesarias.
            // Las habíamos usado antes de tener LoginScreen listo.
            // navController.navigate("note_list_screen") {
            //     popUpTo("login_screen") { inclusive = true }
            // }
        }
        composable("note_list_screen") {
            NoteListScreen(navController = navController) // Llamada al Composable de Lista de Notas
        }
        composable("note_detail_screen/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            NoteDetailScreen(navController = navController, noteId = noteId) // Composable del detalle
        }
        composable("note_edit_screen?noteId={noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            NoteEditScreen(navController = navController, noteId = noteId) // <-- ¡MODIFICACIÓN: Llamada al Composable de Añadir/Editar Nota!
        }
        composable("settings_screen") {
             SettingsScreen(navController = navController) // Composable de configuración
        }
    }
}