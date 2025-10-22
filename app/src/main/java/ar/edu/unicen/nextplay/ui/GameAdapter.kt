package ar.edu.unicen.nextplay.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.nextplay.R
import ar.edu.unicen.nextplay.databinding.ListItemGameBinding
import ar.edu.unicen.nextplay.ddl.models.Game
import com.bumptech.glide.Glide

class GameAdapter(
    private val onFavoriteClick: (game: Game) -> Unit
) : ListAdapter<Game, GameAdapter.GameViewHolder>(GameDiffCallback) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameAdapter.GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemGameBinding.inflate(layoutInflater, parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: GameAdapter.GameViewHolder, position: Int
    ) {
        val game = getItem(position)
        holder.bind(game)
    }

    inner class GameViewHolder(
        private val binding: ListItemGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val favoriteIc: ImageView = binding.favBtn

        fun bind(game: Game) {
            binding.gameName.text = game.name
            binding.gameRating.text = game.rating.toString()

            Glide.with(itemView.context)
                .load(game.backgroundImage)
                .placeholder(R.drawable.ic_img_placeholder)
                .error(R.drawable.ic_img_broken)
                .into(binding.gameImg)

            favoriteIc.setOnClickListener {
                onFavoriteClick(game)
            }

            if (game.isFavorite) {
                favoriteIc.setImageResource(R.drawable.ic_favorite_fill)
            } else {
                favoriteIc.setImageResource(R.drawable.ic_favorite)
            }
        }
    }

    object GameDiffCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }
}
