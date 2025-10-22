package ar.edu.unicen.nextplay.ddl.models

class Genre(
    override val id: Int,
    override val name: String,
    val slug: String,
    val gamesCount: Int,
    val imageBackground: String
): Chipable
