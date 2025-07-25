package com.example.examen_dispmoviles // <-- ¡Tu paquete principal!

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.examen_dispmoviles.AppDatabase // <-- Importa tu AppDatabase
import com.example.examen_dispmoviles.data.repository.NotaLocalDataSource // <-- Importa tu NotaLocalDataSource
import com.example.examen_dispmoviles.data.repository.NotaRepository // <-- Importa tu NotaRepository
import com.example.examen_dispmoviles.ui.viewmodel.LoginViewModel
import com.example.examen_dispmoviles.ui.viewmodel.NoteDetailViewModel
import com.example.examen_dispmoviles.ui.viewmodel.NoteEditViewModel
import com.example.examen_dispmoviles.ui.viewmodel.NoteListViewModel
import com.example.examen_dispmoviles.ui.viewmodel.SettingsViewModel

/**
 * Proporciona una ViewModelProvider.Factory para crear ViewModels con dependencias personalizadas.
 * Esta factoría sabe cómo construir cada ViewModel de nuestra aplicación.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Inicializador para NoteListViewModel
        initializer {
            NoteListViewModel(
                repository = application.container.notaRepository // Obtiene el repositorio de la AppContainer
            )
        }
        // Inicializador para NoteDetailViewModel
        initializer {
            NoteDetailViewModel(
                repository = application.container.notaRepository
            )
        }
        // Inicializador para NoteEditViewModel
        initializer {
            NoteEditViewModel(
                repository = application.container.notaRepository
            )
        }
        // Inicializador para LoginViewModel (no necesita repositorio de notas, pero podría tener otras dependencias)
        initializer {
            LoginViewModel()
        }
        // Inicializador para SettingsViewModel (no necesita repositorio de notas)
        initializer {
            SettingsViewModel()
        }
    }
}

/**
 * Interfaz para el contenedor de dependencias de la aplicación.
 * Define las dependencias que estarán disponibles globalmente.
 */
interface AppContainer {
    val notaRepository: NotaRepository
}

/**
 * Implementación predeterminada de AppContainer.
 * Proporciona instancias concretas de las dependencias.
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
    override val notaRepository: NotaRepository by lazy {
        // Inicializa el repositorio de forma lazy (solo cuando se solicite por primera vez)
        // Construye la cadena de dependencias: Database -> DAO -> DataSource -> Repository
        val database = AppDatabase.getDatabase(context)
        val notaDao = database.notaDao()
        val localDataSource = NotaLocalDataSource(notaDao)
        NotaRepository(localDataSource)
    }
}

/**
 * Clase Application personalizada.
 * Almacena la instancia del AppContainer para que las ViewModels puedan acceder a las dependencias.
 * Debe declararse en el AndroidManifest.xml.
 */
class MyApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Inicializa el contenedor de dependencias cuando la aplicación se crea.
        container = DefaultAppContainer(this)
    }
}


/**
 * Extensión para facilitar la obtención del contexto de la aplicación desde CreationExtras.
 */
fun CreationExtras.getApplicationContext(): Application =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application

/**
 * Extensión para obtener la instancia de MyApplication desde CreationExtras,
 * lo que permite acceder al AppContainer.
 */
val CreationExtras.application: MyApplication
    get() = getApplicationContext() as MyApplication