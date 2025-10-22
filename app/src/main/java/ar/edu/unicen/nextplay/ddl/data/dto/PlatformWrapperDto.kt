package ar.edu.unicen.nextplay.ddl.data.dto

import ar.edu.unicen.nextplay.ddl.models.Platform
import com.google.gson.annotations.SerializedName

data class PlatformWrapperDto(
    @SerializedName("platform")
    val platform: PlatformDto,
    @SerializedName("requirements")
    val requirements: RequirementsDto?
) {
    fun toPlatform(): Platform {
        return Platform(
            id = platform.id,
            name = platform.name,
            slug = platform.slug,
            gamesCount = platform.gamesCount,
            imageBackground = platform.imageBackground,
            image = platform.image,
            yearStart = platform.yearStart,
            yearEnd = platform.yearEnd,
            requirements = requirements?.toRequirements()
        )
    }
}