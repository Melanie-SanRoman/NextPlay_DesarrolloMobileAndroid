package ar.edu.unicen.nextplay.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.nextplay.ddl.data.NextPlayRepository
import ar.edu.unicen.nextplay.ddl.models.Game
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.async

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val gamesRepository: NextPlayRepository,
) : ViewModel() {

    private val _games = MutableStateFlow<List<Game>?>(null)
    val games = _games.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    fun getAllFavGames() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            try {
                val favorites = gamesRepository.getAllFavGames()

                if (!favorites.isNullOrEmpty()) {
                    val gameDetailsJobs = favorites.map { favItem ->
                        async { gamesRepository.getGameDetails(favItem.id) }
                    }
                    val detailedGames = gameDetailsJobs.awaitAll().filterNotNull()
                    if (detailedGames.isEmpty() && favorites.isNotEmpty()) {
                        _error.value = true
                        _games.value = emptyList()
                    } else {
                        val favoriteGames = detailedGames.map { game ->
                            game.copy(isFavorite = true)
                        }
                        _games.value = favoriteGames
                    }
                } else {
                    _games.value = emptyList()
                }

            } catch (e: Exception) {
                _error.value = true
                _games.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    fun toggleFavoriteStatus(game: Game) {
        viewModelScope.launch {
            try {
                gamesRepository.updateFavoriteStatus(game)
                getAllFavGames()
            } catch (e: Exception) {
                _error.value = true
            }
        }
    }
}