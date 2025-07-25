package com.example.examen_dispmoviles.data.repository

import com.example.examen_dispmoviles.data.model.Nota
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz que define las operaciones de acceso a datos para las Notas.
 * Actúa como una abstracción de la fuente de datos (ya sea local o remota).
 */
interface NotaDataSource {
    fun obtenerTodasNotas(): Flow<List<Nota>>
    suspend fun insertarNota(nota: Nota)
    suspend fun actualizarNota(nota: Nota)
    suspend fun eliminarNota(nota: Nota)
    suspend fun obtenerNotaPorId(id: Int): Nota?
}