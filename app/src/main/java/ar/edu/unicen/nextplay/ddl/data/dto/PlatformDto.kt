package ar.edu.unicen.nextplay.ddl.data.dto

import ar.edu.unicen.nextplay.ddl.models.Platform
import com.google.gson.annotations.SerializedName

data class PlatformDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("year_start")
    val yearStart: Int,
    @SerializedName("year_end")
    val yearEnd: Int
){

    fun toPlatform(): Platform{
        return Platform(
            id = id,
            name = name,
            slug = slug,
            gamesCount = gamesCount,
            imageBackground = imageBackground,
            image = image,
            yearStart = yearStart,
            yearEnd = yearEnd
        )
    }
}
