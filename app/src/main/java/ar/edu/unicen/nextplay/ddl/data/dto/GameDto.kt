package ar.edu.unicen.nextplay.ddl.data.dto

import ar.edu.unicen.nextplay.ddl.models.Game
import com.google.gson.annotations.SerializedName
import kotlin.collections.map

data class GameDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("released")
    val released: String?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("platforms")
    val platforms: List<PlatformWrapperDto>?
) {
    fun toGame(): Game {
        return Game(
            id = id,
            name = name,
            released = released,
            backgroundImage = backgroundImage,
            rating = rating,
            platforms = platforms?.map { it.toPlatform() } ?: emptyList()
        )
    }
}
