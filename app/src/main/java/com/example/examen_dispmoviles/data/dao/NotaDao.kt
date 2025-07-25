package com.example.examen_dispmoviles.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.examen_dispmoviles.data.model.Nota // <-- ¡Importa tu clase Nota!
import kotlinx.coroutines.flow.Flow // Importa Flow para observar cambios reactivos

/**
 * Interfaz DAO (Data Access Object) para la entidad Nota.
 * Define los métodos para interactuar con la tabla 'notas' de la base de datos.
 */
@Dao // 1. Anotación @Dao: Le indica a Room que esta interfaz es un DAO.
interface NotaDao {

    /**
     * Inserta una nueva nota en la base de datos.
     * Si una nota con el mismo ID ya existe, la reemplaza.
     * Es una función de suspensión, debe llamarse desde una corrutina.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Si hay un conflicto (ej. mismo ID), reemplaza la nota existente.
    suspend fun insertar(nota: Nota) // 'suspend' indica que es una función de corrutina (puede tardar tiempo).

    /**
     * Actualiza una nota existente en la base de datos.
     * Es una función de suspensión.
     */
    @Update
    suspend fun actualizar(nota: Nota)

    /**
     * Elimina una nota de la base de datos.
     * Es una función de suspensión.
     */
    @Delete
    suspend fun eliminar(nota: Nota)

    /**
     * Obtiene todas las notas de la base de datos, ordenadas por fecha de creación descendente.
     * Los resultados son observados como un Flow, que emite una nueva lista cada vez que
     * los datos cambian, permitiendo que la UI se actualice automáticamente.
     */
    @Query("SELECT * FROM notas ORDER BY fecha_creacion DESC") // Consulta SQL para obtener todas las notas.
    fun obtenerTodasNotas(): Flow<List<Nota>>

    /**
     * Obtiene una nota específica por su ID.
     * Puede devolver null si la nota no se encuentra.
     * Es una función de suspensión.
     */
    @Query("SELECT * FROM notas WHERE id = :notaId") // Consulta SQL para obtener una nota específica por su ID.
    suspend fun obtenerNotaPorId(notaId: Int): Nota? // '?' indica que puede devolver null.
}