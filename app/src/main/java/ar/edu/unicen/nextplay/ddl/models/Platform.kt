package ar.edu.unicen.nextplay.ddl.models

data class Platform(
    override val id: Int,
    override val name: String,
    val slug: String,
    val gamesCount: Int,
    val imageBackground: String?,
    val image: String?,
    val yearStart: Int,
    val yearEnd: Int,
    val requirements: Requirements? = null
): Chipable
