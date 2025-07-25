package com.example.examen_dispmoviles.data.repository
import com.example.examen_dispmoviles.data.model.Nota
import com.example.examen_dispmoviles.data.dao.NotaDao
import kotlinx.coroutines.flow.Flow

/**
 * Implementación concreta de NotaDataSource que interactúa con la base de datos Room.
 */
class NotaLocalDataSource(private val notaDao: NotaDao) : NotaDataSource {

    override fun obtenerTodasNotas(): Flow<List<Nota>> {
        return notaDao.obtenerTodasNotas()
    }

    override suspend fun insertarNota(nota: Nota) {
        notaDao.insertar(nota)
    }

    override suspend fun actualizarNota(nota: Nota) {
        notaDao.actualizar(nota)
    }

    override suspend fun eliminarNota(nota: Nota) {
        notaDao.eliminar(nota)
    }

    override suspend fun obtenerNotaPorId(id: Int): Nota? {
        return notaDao.obtenerNotaPorId(id)
    }
}