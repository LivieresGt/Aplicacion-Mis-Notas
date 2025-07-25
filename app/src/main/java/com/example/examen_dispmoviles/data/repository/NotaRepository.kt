package com.example.examen_dispmoviles.data.repository

import com.example.examen_dispmoviles.data.model.Nota
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio para manejar las operaciones de datos de las Notas.
 * Abstrae la fuente de datos (DataSource) del resto de la aplicación (ViewModels).
 * Es la "única fuente de verdad" para los datos.
 */
class NotaRepository(private val localDataSource: NotaDataSource) {

    fun obtenerTodasNotas(): Flow<List<Nota>> {
        return localDataSource.obtenerTodasNotas()
    }

    suspend fun insertarNota(nota: Nota) {
        localDataSource.insertarNota(nota)
    }

    suspend fun actualizarNota(nota: Nota) {
        localDataSource.actualizarNota(nota)
    }

    suspend fun eliminarNota(nota: Nota) {
        localDataSource.eliminarNota(nota)
    }

    suspend fun obtenerNotaPorId(id: Int): Nota? {
        return localDataSource.obtenerNotaPorId(id)
    }
}