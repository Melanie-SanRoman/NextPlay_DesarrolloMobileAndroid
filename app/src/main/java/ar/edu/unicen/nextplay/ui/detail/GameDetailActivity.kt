package ar.edu.unicen.nextplay.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.nextplay.R
import ar.edu.unicen.nextplay.databinding.GameDetailBinding
import ar.edu.unicen.nextplay.ddl.models.Game
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// TODO: La funcionalidad de esta pantalla (botones, eventos, etc.) no está completamente implementada.
@AndroidEntryPoint
class GameDetailActivity: AppCompatActivity() {

    private lateinit var binding: GameDetailBinding
    private val gameDetailViewModel: GameDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = GameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val gameId = intent.getIntExtra("EXTRA_GAME_ID", -1)
        if (gameId != -1) {
            gameDetailViewModel.getGameDetails(gameId)
        } else {
        }
        subscribeToUiState()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.collapsingToolbar.title = ""
    }

    private fun subscribeToUiState() {
        gameDetailViewModel.loading.onEach { loading ->
//            if (loading) {
//                binding.progressBar.visibility = android.view.View.VISIBLE
//            } else {
//                binding.progressBar.visibility = android.view.View.INVISIBLE
//            }
        }.launchIn(lifecycleScope)

        gameDetailViewModel.error.onEach { error ->
//            if (error) {
//                binding.errorTx.visibility = android.view.View.VISIBLE
//            } else {
//                binding.errorTx.visibility = android.view.View.INVISIBLE
//            }
        }.launchIn(lifecycleScope)
    }

    private fun populateUi(game: Game) {
        Glide.with(this)
            .load(game.backgroundImage)
            .placeholder(R.drawable.ic_img_placeholder)
            .error(R.drawable.ic_img_broken)
            .into(binding.gameDetailImage)

        binding.collapsingToolbar.title = game.name
        binding.gameDetailTitleHeader.text = game.name
        binding.gameDetailRating.text = game.rating.toString()
        binding.gameDetailReleaseDate.text = game.released
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
