package ar.edu.unicen.nextplay.ddl.data.local

import ar.edu.unicen.nextplay.ddl.models.GameFavItem
import javax.inject.Inject

class GameFavRepository @Inject constructor(
    private val localDataSource: GameFavLocalDataSource,
) {
    suspend fun getAll(): List<GameFavItem>? {
        return localDataSource.getAll()
    }

    suspend fun insert(
        item: GameFavItem
    ): Int? {
        return localDataSource.insert(item)
    }

    suspend fun delete(
        item: GameFavItem
    ): Boolean {
        return localDataSource.delete(item)
    }
}