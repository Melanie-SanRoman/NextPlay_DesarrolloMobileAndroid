package ar.edu.unicen.nextplay.ddl.models

data class Game(
    val id: Int,
    val name: String,
    val released: String?,
    val backgroundImage: String?,
    val rating: Double,
    val platforms: List<Platform>,
    var isFavorite: Boolean = false
)
