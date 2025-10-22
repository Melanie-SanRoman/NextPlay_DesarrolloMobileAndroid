package ar.edu.unicen.nextplay.ddl.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unicen.nextplay.ddl.data.dao.GameFavItemDao
import ar.edu.unicen.nextplay.ddl.data.dto.GameFavItemDto

@Database(
    entities = [GameFavItemDto::class],
    version = 1
)
abstract class GameFavDatabase: RoomDatabase() {
    abstract fun gameFavDao(): GameFavItemDao
}