package ar.edu.unicen.nextplay.ddl.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unicen.nextplay.ddl.models.GameFavItem

@Entity(
    tableName = "game_favorite_item"
)
class GameFavItemDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name_game")
    val name: String,
    @ColumnInfo(name = "rating_name")
    val rating: Double,
    @ColumnInfo(name = "background_image")
    val backgroundImage: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = true
) {

    fun toGameFavItem(): GameFavItem {
        return GameFavItem(
            id = id,
            name = name,
            rating = rating,
            backgroundImage = backgroundImage,
            isFavorite = isFavorite
        )
    }

    companion object {
        fun GameFavItem.toGameFavItemDto(): GameFavItemDto {
            return GameFavItemDto(
                id = id,
                name = name,
                rating = rating,
                backgroundImage = backgroundImage,
                isFavorite = isFavorite
            )
        }
    }
}