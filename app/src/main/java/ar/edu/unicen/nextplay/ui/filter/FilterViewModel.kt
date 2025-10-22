package ar.edu.unicen.nextplay.ui.filter;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject;
import ar.edu.unicen.nextplay.ddl.data.NextPlayRepository;
import ar.edu.unicen.nextplay.ddl.models.Genre
import ar.edu.unicen.nextplay.ddl.models.Platform
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
public class FilterViewModel @Inject constructor(
    private val gamesRepository: NextPlayRepository
): ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error.asStateFlow()

    private val _platforms: MutableStateFlow<List<Platform>?> = MutableStateFlow(null)
    val platforms: StateFlow<List<Platform>?> = _platforms.asStateFlow()
    init {
        getAllPlatforms()
    }
    private val _genres: MutableStateFlow<List<Genre>?> = MutableStateFlow(null)
    val genres: StateFlow<List<Genre>?> = _genres.asStateFlow()
    init {
        getAllGenres()
    }

    fun getAllPlatforms(){
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _platforms.value = null

            val platforms: List<Platform>? = gamesRepository.getAllPlatforms()

            _loading.value = false
            _error.value = _platforms == null
            _platforms.value = platforms
        }
    }

    fun getAllGenres() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _genres.value = null

            val genres: List<Genre>? = gamesRepository.getAllGenres()

            _loading.value = false
            _error.value = _genres == null
            _genres.value = genres
        }
    }
}