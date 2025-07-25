package com.example.examen_dispmoviles

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examen_dispmoviles.data.dao.NotaDao
import com.example.examen_dispmoviles.data.model.Nota

/**
 * Clase principal de la base de datos de la aplicación.
 * Define las entidades (tablas) que contiene y los Data Access Objects (DAOs) para acceder a ellas.
 * Sigue el patrón Singleton para asegurar una única instancia de la base de datos en toda la aplicación.
 */
@Database(entities = [Nota::class], version = 1, exportSchema = false) // 1. Anotación @Database
abstract class AppDatabase : RoomDatabase() { // 2. Debe ser abstracta y extender RoomDatabase

    // 3. Método abstracto para obtener el DAO de Nota. Room implementará esto por nosotros.
    abstract fun notaDao(): NotaDao

    // 4. Companion object para la lógica Singleton de la base de datos.
    companion object {
        @Volatile // Garantiza que los cambios en la instancia sean inmediatamente visibles para todos los hilos.
        private var INSTANCE: AppDatabase? = null // La única instancia de la base de datos.

        /**
         * Obtiene la instancia única (singleton) de la base de datos.
         * Si no existe, la crea de forma segura para hilos.
         *
         * @param context El contexto de la aplicación para construir la base de datos.
         * @return La instancia de AppDatabase.
         */
        fun getDatabase(context: Context): AppDatabase {
            // Si la instancia ya existe, la devuelve.
            // Si no existe, entra en el bloque sincronizado para crearla de forma segura.
            return INSTANCE ?: synchronized(this) { // Bloquea este objeto para asegurar que solo un hilo crea la instancia.
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Usa el contexto de la aplicación para evitar fugas de memoria.
                    AppDatabase::class.java,    // La clase de la base de datos que estamos definiendo.
                    "my_exam_app_database"      // El nombre del archivo de la base de datos en el dispositivo.
                )
                    // Permite a Room destruir y recrear la base de datos si la versión cambia.
                    // ¡Útil para desarrollo y el examen, pero borra todos los datos existentes!
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance // Asigna la instancia creada a la variable.
                instance // Devuelve la instancia.
            }
        }
    }
}