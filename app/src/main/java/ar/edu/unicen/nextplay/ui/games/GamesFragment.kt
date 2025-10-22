package ar.edu.unicen.nextplay.ui.games

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ar.edu.unicen.nextplay.databinding.FragmentGamesBinding
import ar.edu.unicen.nextplay.ui.filter.FilterGameActivity
import ar.edu.unicen.nextplay.ui.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GameAdapter
    private val gamesViewModel: GamesViewModel by viewModels()
    private val filterLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            handleFilterResult(data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.gamesLists.layoutManager = LinearLayoutManager(requireContext())

        subscribeToUI()
        subscribeToViewModel()

        gamesViewModel.loadGames()
    }

    private fun subscribeToUI() {
        adapter = GameAdapter(
            onFavoriteClick = { game ->
                gamesViewModel.toggleFavoriteStatus(game)
            }
        )
        binding.gamesLists.adapter = adapter
    }

    private fun subscribeToViewModel() {

        // BUSCAR POR NOMBRE DE JUEGO
        binding.nameGameTx.addTextChangedListener{
            binding.searchBtn.isEnabled = it.toString().isNotBlank()
        }

        binding.searchBtn.setOnClickListener {
            val searchQuery = binding.nameGameTx.text.toString()
            gamesViewModel.search(searchQuery)
        }

        // VER FILTROS
        binding.tuneBtn.setOnClickListener {
            val intent = Intent(requireContext(), FilterGameActivity::class.java)
            filterLauncher.launch(intent)
        }

        gamesViewModel.loading.onEach { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }.launchIn(lifecycleScope)

        gamesViewModel.games.onEach { games ->
            adapter.submitList(games)
        }.launchIn(lifecycleScope)

        gamesViewModel.error.onEach { error ->
            if (error) {
                binding.errorTx.visibility = View.VISIBLE
            } else {
                binding.errorTx.visibility = View.INVISIBLE
            }
        }.launchIn(lifecycleScope)
    }

    private fun handleFilterResult(data: Intent?) {
        val genreIds = data?.getIntegerArrayListExtra("EXTRA_GENRE_IDS")
        val platformIds = data?.getIntegerArrayListExtra("EXTRA_PLATFORM_IDS")
        val sortOption = data?.getStringExtra("EXTRA_SORT_OPTION")

        Toast.makeText(requireContext(), "Filtros recibidos. Recargando...", Toast.LENGTH_SHORT).show()

        gamesViewModel.search(
            platforms = platformIds,
            genres = genreIds,
            ordering = sortOption
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}