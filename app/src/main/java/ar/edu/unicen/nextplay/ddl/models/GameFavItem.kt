package ar.edu.unicen.nextplay.ddl.models

class GameFavItem(
    val id: Int,
    val name: String,
    val rating: Double,
    val backgroundImage: String,
    val isFavorite: Boolean = true
)
