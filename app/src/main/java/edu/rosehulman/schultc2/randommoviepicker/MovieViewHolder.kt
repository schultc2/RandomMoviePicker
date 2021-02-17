package edu.rosehulman.schultc2.randommoviepicker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favorite_movie_view.view.*

class MovieViewHolder(itemView: View, var adapter: MovieListAdapter): RecyclerView.ViewHolder(itemView), GetMovieTask.MovieConsumer {
    val fav_movie_name: TextView = itemView.fav_movie_text_view
    val fav_movie_img: ImageView = itemView.fav_movie_img_view


    init {
        itemView.setOnClickListener {
            adapter.selectMovieAt(adapterPosition)
        }
    }

    fun bind(movieWrapper: MovieWrapper){
        val urlString = "https://www.omdbapi.com/?t=${movieWrapper?.movie?.title}&plot=full&apikey=7e1d379f"
        GetMovieTask(this).execute(urlString)
        fav_movie_name.text = movieWrapper.movie?.title
    }

    override fun onMovieLoaded(movie: MovieData?) {
        adapter.loadPoster(movie, this, adapterPosition)
    }

}