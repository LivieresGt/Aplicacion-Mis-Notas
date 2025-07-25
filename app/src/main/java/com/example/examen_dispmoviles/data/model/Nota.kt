package com.example.examen_dispmoviles.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Esta es nuestra clase de datos principal para una Nota.
 * Será mapeada a una tabla en nuestra base de datos SQLite con Room.
 */
@Entity(tableName = "notas") // 1. Anotación @Entity: Le dice a Room que esta clase es una tabla de la base de datos.
data class Nota(
    @PrimaryKey(autoGenerate = true) // 2. @PrimaryKey: Define 'id' como la clave primaria.
    val id: Int = 0, // 'autoGenerate = true' significa que Room asignará un ID único automáticamente (1, 2, 3...).

    @ColumnInfo(name = "titulo") // 3. @ColumnInfo: Especifica el nombre de la columna en la base de datos.
    val titulo: String,

    @ColumnInfo(name = "contenido")
    val contenido: String,

    @ColumnInfo(name = "fecha_creacion")
    val fechaCreacion: Long = System.currentTimeMillis(), // 4. Valor por defecto: El tiempo actual en milisegundos.

    @ColumnInfo(name = "completada")
    val completada: Boolean = false // 5. Un flag para saber si la nota/tarea está completada.
)