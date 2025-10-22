package ar.edu.unicen.nextplay.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ar.edu.unicen.nextplay.databinding.FragmentFavoritesBinding
import ar.edu.unicen.nextplay.ui.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.getValue

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var adapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.gamesFavLists.layoutManager = LinearLayoutManager(requireContext())

        subscribeToUI()
        subscribeToViewModel()

        favoritesViewModel.getAllFavGames()
    }

    private fun subscribeToUI() {
        adapter = GameAdapter(
            onFavoriteClick = { game ->
                favoritesViewModel.toggleFavoriteStatus(game)
            }
        )
        binding.gamesFavLists.adapter = adapter
    }

    private fun subscribeToViewModel() {

        favoritesViewModel.loading.onEach { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }.launchIn(lifecycleScope)

        favoritesViewModel.games.onEach { games ->
            adapter.submitList(games ?: emptyList())
        }.launchIn(lifecycleScope)

        favoritesViewModel.error.onEach { error ->
            if (error) {
                binding.errorTx.visibility = View.VISIBLE
            } else {
                binding.errorTx.visibility = View.INVISIBLE
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}