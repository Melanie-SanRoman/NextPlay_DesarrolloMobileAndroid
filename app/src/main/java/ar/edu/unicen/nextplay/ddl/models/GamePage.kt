package ar.edu.unicen.nextplay.ddl.models

data class GamePage(
    val games: List<Game>,
    val hasNextPage: Boolean
)