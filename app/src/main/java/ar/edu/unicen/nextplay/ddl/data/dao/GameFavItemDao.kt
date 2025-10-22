package ar.edu.unicen.nextplay.ddl.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ar.edu.unicen.nextplay.ddl.data.dto.GameFavItemDto

@Dao
interface GameFavItemDao {

    @Insert
    suspend fun insert(item: GameFavItemDto): Long

    @Delete
    suspend fun delete(item: GameFavItemDto): Int

    @Query("SELECT * FROM game_favorite_item")
    suspend fun getAll(): List<GameFavItemDto>

    @Query("SELECT * FROM game_favorite_item WHERE id = :id")
    suspend fun getById(id: Int): GameFavItemDto?

}