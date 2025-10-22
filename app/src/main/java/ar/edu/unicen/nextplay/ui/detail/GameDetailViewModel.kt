package ar.edu.unicen.nextplay.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.nextplay.ddl.data.NextPlayRepository
import ar.edu.unicen.nextplay.ddl.models.Game
import ar.edu.unicen.nextplay.ddl.models.Platform
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: La funcionalidad de esta pantalla (botones, eventos, etc.) no está completamente implementada.
@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val gamesRepository: NextPlayRepository
): ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error.asStateFlow()

    private val _game: MutableStateFlow<Game?> = MutableStateFlow(null)
    val game: StateFlow<Game?> = _game.asStateFlow()
    init {
    }

    fun getGameDetails(
        gameId: Int
    ){
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _game.value = null

            val game: Game? = gamesRepository.getGameDetails(gameId)

            _loading.value = false
            _error.value = _game == null
            _game.value = game
        }
    }
}