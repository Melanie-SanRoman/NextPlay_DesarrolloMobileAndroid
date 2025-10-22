package ar.edu.unicen.nextplay.ddl.data

import android.util.Log
import ar.edu.unicen.nextplay.ddl.data.dto.GameDto
import ar.edu.unicen.nextplay.ddl.data.dto.GameResponseDto
import ar.edu.unicen.nextplay.ddl.data.dto.GenreResponseDto
import ar.edu.unicen.nextplay.ddl.data.dto.PlatformResponseDto
import ar.edu.unicen.nextplay.ddl.models.Game
import ar.edu.unicen.nextplay.ddl.models.Genre
import ar.edu.unicen.nextplay.ddl.models.Platform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class NextPlayRemoteDataSource @Inject constructor(
    private val nextPlayApi: NextPlayApi,
    @Named("apiKey")
    private val apiKey: String
) {
    suspend fun getGame(
        id: Int
    ): Game? {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<GameDto> = nextPlayApi.getGame(id, apiKey)
                if (response.isSuccessful){
                    return@withContext response.body()?.toGame()
                }
                else{
                    return@withContext null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getGames(
        platforms: String? = null,
        genres: String? = null,
        ordering: String? = null,
        search: String? = null,
    ): List<Game>? {
        return withContext(Dispatchers.IO) {
            try {
                val cleanPlatforms = platforms?.ifBlank { null }
                val cleanGenres = genres?.ifBlank { null }
                val cleanOrdering = ordering?.ifBlank { null }
                val cleanSearch = search?.ifBlank { null }

                val response: Response<GameResponseDto> = nextPlayApi.getGames(
                    apiKey = apiKey,
                    parent_platforms = cleanPlatforms,
                    genres = cleanGenres,
                    ordering = cleanOrdering,
                    search = cleanSearch
                )

                if (response.isSuccessful) {
                    val gameDto = response.body()
                    gameDto?.results?.map { it.toGame() } ?: emptyList()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getPlatforms(): List<Platform>? {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<PlatformResponseDto> = nextPlayApi.getParentPlatforms(apiKey)
                if (response.isSuccessful) {
                    return@withContext response.body()?.results?.map { it.toPlatform() }
                } else {
                    return@withContext null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getGenres(): List<Genre>? {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<GenreResponseDto> = nextPlayApi.getGenres(apiKey)
                if (response.isSuccessful) {
                    return@withContext response.body()?.results?.map { it.toGenre() }
                } else {
                    return@withContext null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }
}