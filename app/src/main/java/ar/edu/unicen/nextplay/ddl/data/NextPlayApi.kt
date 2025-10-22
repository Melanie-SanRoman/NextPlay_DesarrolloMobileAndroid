package ar.edu.unicen.nextplay.ddl.data

import ar.edu.unicen.nextplay.ddl.data.dto.GameDto
import ar.edu.unicen.nextplay.ddl.data.dto.GameResponseDto
import ar.edu.unicen.nextplay.ddl.data.dto.GenreResponseDto
import ar.edu.unicen.nextplay.ddl.data.dto.PlatformResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NextPlayApi {

    @GET("games/{id}")
    suspend fun getGame(
        @Path("id")
        id: Int,
        @Query("key")
        apiKey: String
    ): Response<GameDto>

    @GET("games")
    suspend fun getGames(
        @Query("key")
        apiKey: String,
        // 1. FILTRO POR PLATAFORMAS
        @Query("parent_platforms")
        parent_platforms: String? = null,
        // 2. FILTRO POR GÉNEROS
        @Query("genres")
        genres: String? = null,
        // 3. ORDENAMIENTO
        @Query("ordering")
        ordering: String? = null,
        // 4. BÚSQUEDA POR NOMBRE
        @Query("search")
        search: String? = null
    ): Response<GameResponseDto>

    @GET("platforms")
    suspend fun getPlatforms(
        @Query("key")
        apiKey: String
    ): Response<PlatformResponseDto>

    @GET("genres")
    suspend fun getGenres(
        @Query("key")
        apiKey: String
    ): Response<GenreResponseDto>

    @GET("platforms/lists/parents")
    suspend fun getParentPlatforms(
        @Query("key")
        apiKey: String
    ): Response<PlatformResponseDto>

}