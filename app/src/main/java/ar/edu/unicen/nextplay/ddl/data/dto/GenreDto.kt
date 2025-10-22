package ar.edu.unicen.nextplay.ddl.data.dto

import ar.edu.unicen.nextplay.ddl.models.Genre
import com.google.gson.annotations.SerializedName

class GenreDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String
) {

    fun toGenre(): Genre {
        return Genre(
            id = id,
            name = name,
            slug = slug,
            gamesCount = gamesCount,
            imageBackground = imageBackground
        )
    }
}
