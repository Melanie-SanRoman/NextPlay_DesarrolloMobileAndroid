package ar.edu.unicen.nextplay.ddl.data.local

import ar.edu.unicen.nextplay.ddl.data.dao.GameFavItemDao
import ar.edu.unicen.nextplay.ddl.data.dto.GameFavItemDto
import ar.edu.unicen.nextplay.ddl.data.dto.GameFavItemDto.Companion.toGameFavItemDto
import ar.edu.unicen.nextplay.ddl.models.GameFavItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameFavLocalDataSource @Inject constructor(
    private val gameFavDao: GameFavItemDao
) {

    suspend fun getAll(): List<GameFavItem>?{
        return withContext(Dispatchers.IO){
            try {
                val itemsDto = gameFavDao.getAll()
                val items = itemsDto.map { it.toGameFavItem() }
                return@withContext items
            } catch (e: Exception){
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun insert(
        item: GameFavItem
    ): Int? {
        return withContext(Dispatchers.IO){
            try {
                val itemDto: GameFavItemDto = item.toGameFavItemDto()
                val generatedId = gameFavDao.insert(itemDto)
                return@withContext generatedId.toInt()
            } catch (e: Exception){
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun delete(
        item: GameFavItem
    ): Boolean {
        return withContext(Dispatchers.IO){
            try{
                val itemDto: GameFavItemDto = item.toGameFavItemDto()
                val deleteRows = gameFavDao.delete(itemDto)
                return@withContext deleteRows == 1
            } catch (e: Exception){
                e.printStackTrace()
                return@withContext false
            }
        }
    }

    suspend fun isFavorite(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val itemDto = gameFavDao.getById(id)
                return@withContext itemDto != null
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext false
            }
        }
    }

    suspend fun getAllFavoriteIds(): List<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val itemsDto = gameFavDao.getAll()
                return@withContext itemsDto.map { it.id }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext emptyList()
            }
        }
    }
}
