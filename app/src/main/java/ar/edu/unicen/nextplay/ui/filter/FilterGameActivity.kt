package ar.edu.unicen.nextplay.ui.filter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.nextplay.databinding.ActivityFilterGameBinding
import ar.edu.unicen.nextplay.ddl.models.Chipable
import ar.edu.unicen.nextplay.ui.main.MainActivity
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.jvm.java

@AndroidEntryPoint
class FilterGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterGameBinding
    private val filterViewModel: FilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFilterGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filterViewModel.loading.onEach { isLoading ->
            showLoading(isLoading)
        }.launchIn(lifecycleScope)

        filterViewModel.genres.onEach { genres ->
            binding.genresChipGroup.removeAllViews()

            if (!genres.isNullOrEmpty()) {
                genres.forEach { genre ->
                    val chip = createChip(genre)
                    binding.genresChipGroup.addView(chip)
                }
            }
        }.launchIn(lifecycleScope)

        filterViewModel.platforms.onEach { platforms ->
            binding.platformsChipGroup.removeAllViews()

            if (!platforms.isNullOrEmpty()) {
                platforms.forEach { platform ->
                    val chip = createChip(platform)
                    binding.platformsChipGroup.addView(chip)
                }
            }
        }.launchIn(lifecycleScope)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.applyFiltersFab.setOnClickListener {
            val selectedGenreIds = getSelectedChipIds(binding.genresChipGroup)
            val selectedPlatformIds = getSelectedChipIds(binding.platformsChipGroup)

            val selectedSortOptionId = binding.sortingRadioGroup.checkedRadioButtonId
            val selectedSortOptionText = when (selectedSortOptionId) {
                binding.sortAlphabetical.id -> "name"
                binding.sortReleaseDate.id -> "released"
                binding.sortRating.id -> "ratingDESC"
                else -> null
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putIntegerArrayListExtra("EXTRA_GENRE_IDS", ArrayList(selectedGenreIds))
            intent.putIntegerArrayListExtra("EXTRA_PLATFORM_IDS", ArrayList(selectedPlatformIds))
            intent.putExtra("EXTRA_SORT_OPTION", selectedSortOptionText)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    /**
     * Muestra u oculta los indicadores de progreso (ProgressBar).
     * @param isLoading True para mostrar los indicadores, false para ocultarlos.
     */
    private fun showLoading(isLoading: Boolean) {
        val visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE

        binding.progressBarGenres.visibility = visibility
        binding.progressBarPlatforms.visibility = visibility

        val contentVisibility = if (isLoading) android.view.View.GONE else android.view.View.VISIBLE
        binding.platformsChipGroup.visibility = contentVisibility
        binding.genresChipGroup.visibility = contentVisibility
    }

    /**
     * Función genérica para crear y configurar un Chip a partir de cualquier objeto
     * que implemente la interfaz Chipable.
     * @param T El tipo de dato que implementa Chipable (ej. Platform, Genre).
     * @param item El objeto de datos (ej. una instancia de Platform).
     * @return Un Chip configurado para ser añadido al ChipGroup.
     */
    private fun <T : Chipable> createChip(item: T): Chip {
        val chip = Chip(this)

        chip.text = item.name
        chip.isCheckable = true
        chip.tag = item.id

        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "${buttonView.text} seleccionado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "${buttonView.text} deseleccionado", Toast.LENGTH_SHORT).show()
            }
        }
        return chip
    }

    /**
     * Recorre un ChipGroup y devuelve una lista de los IDs de los chips seleccionados.
     * Utiliza el 'tag' de cada Chip, donde previamente guardamos el ID del modelo (Platform/Genre).
     * @param chipGroup El ChipGroup del cual extraer los IDs.
     * @return Una List<Int> con los IDs de los chips seleccionados.
     */
    private fun getSelectedChipIds(chipGroup: com.google.android.material.chip.ChipGroup): List<Int> {
        return chipGroup.checkedChipIds.mapNotNull { chipId ->
            val chip = chipGroup.findViewById<Chip>(chipId)
            chip.tag as? Int
        }
    }
}