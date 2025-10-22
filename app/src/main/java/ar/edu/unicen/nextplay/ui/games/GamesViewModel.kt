package ar.edu.unicen.nextplay.ui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.nextplay.ddl.data.NextPlayRepository
import ar.edu.unicen.nextplay.ddl.models.Game
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val gamesRepository: NextPlayRepository
) : ViewModel() {
    private val _games = MutableStateFlow<List<Game>?>(emptyList())
    val games = _games.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    // Variables de la clase para mantener el estado de los filtros
    var currentSearch: String? = null
    private var currentPlatforms: List<Int>? = null
    private var currentGenres: List<Int>? = null
    private var currentOrdering: String? = null

    // --- ACCIONES DEL USUARIO ---

    /**
     * Inicia una nueva búsqueda o aplica nuevos filtros.
     * Resetea la paginación y la lista de juegos.
     */
    fun search(
        search: String? = null,
        platforms: List<Int>? = null,
        genres: List<Int>? = null,
        ordering: String? = null
    ) {
        currentSearch = search
        currentPlatforms = platforms
        currentGenres = genres
        currentOrdering = ordering
        _games.value = emptyList()

        loadGames()
    }

    /**
     * Carga la página de juegos usando los filtros actuales.
     */
    fun loadGames() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false

            val platformsString = currentPlatforms?.joinToString(separator = ",")
            val genresString = currentGenres?.joinToString(separator = ",")

            try {
                _games.value = gamesRepository.getAllGames(
                    platforms = platformsString,
                    genres = genresString,
                    ordering = currentOrdering,
                    search = currentSearch
                )
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    fun toggleFavoriteStatus(game: Game) {
        viewModelScope.launch {
            val success = gamesRepository.updateFavoriteStatus(game)
            if (success) {
                _games.value?.let { currentGameList ->
                    val index = currentGameList.indexOfFirst { it.id == game.id }

                    if (index != -1) {
                        val updatedList = currentGameList.toMutableList()
                        val updatedGame = updatedList[index].copy(isFavorite = !updatedList[index].isFavorite)
                        updatedList[index] = updatedGame
                        _games.value = updatedList
                    }
                }
            } else {
                _error.value = true
            }
        }
    }
}
