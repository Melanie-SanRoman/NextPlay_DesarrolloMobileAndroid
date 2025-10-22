package ar.edu.unicen.nextplay.ui.main

import androidx.lifecycle.ViewModel
import ar.edu.unicen.nextplay.ddl.data.NextPlayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gamesRepository: NextPlayRepository,
): ViewModel() {



}