package ar.edu.unicen.nextplay.ddl.data

import android.util.Log
import ar.edu.unicen.nextplay.ddl.data.local.GameFavLocalDataSource
import ar.edu.unicen.nextplay.ddl.models.Game
import ar.edu.unicen.nextplay.ddl.models.GameFavItem
import javax.inject.Inject
import ar.edu.unicen.nextplay.ddl.models.Genre
import ar.edu.unicen.nextplay.ddl.models.Platform

class NextPlayRepository @Inject constructor(
    private val remoteDataSource: NextPlayRemoteDataSource,
    private val localDataSource: GameFavLocalDataSource
) {

    suspend fun getGameDetails(id: Int): Game? {
        return try {
            val gameResponse = remoteDataSource.getGame(id)
            gameResponse
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getAllGames(
        platforms: String? = null,
        genres: String? = null,
        ordering: String? = null,
        search: String? = null,
    ): List<Game>? {
        return try {
            val gamesResponse = remoteDataSource.getGames(
                platforms = platforms,
                genres = genres,
                ordering = ordering,
                search = search
            )

            if (gamesResponse != null) {
                val favoriteIdSet = localDataSource.getAllFavoriteIds().toSet()

                return gamesResponse.map { game ->
                    val isFavorite = favoriteIdSet.contains(game.id)
                    if (game.isFavorite == isFavorite) {
                        game
                    } else {
                        game.copy(isFavorite = isFavorite)
                    }
                }
            }
            gamesResponse
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getAllPlatforms(): List<Platform>? {
        return try {
            val platformsResponse = remoteDataSource.getPlatforms()
            platformsResponse
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getAllGenres(): List<Genre>? {
        return try {
            val genresResponse = remoteDataSource.getGenres()
            genresResponse
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateFavoriteStatus(game: Game): Boolean {
        return try{
        val isCurrentlyFavorite = localDataSource.isFavorite(game.id)

        if (isCurrentlyFavorite) {
            localDataSource.delete(
                GameFavItem(
                    id = game.id,
                    name = game.name,
                    rating = game.rating,
                    backgroundImage = game.backgroundImage.toString()
                )
            )
        } else {
            val favItem = GameFavItem(
                id = game.id,
                name = game.name,
                rating = game.rating,
                backgroundImage = game.backgroundImage.toString(),
                isFavorite = true
            )
            localDataSource.insert(favItem)
        }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getAllFavGames(): List<GameFavItem>? {
        return try {
            val gamesFav = localDataSource.getAll()
            gamesFav
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}