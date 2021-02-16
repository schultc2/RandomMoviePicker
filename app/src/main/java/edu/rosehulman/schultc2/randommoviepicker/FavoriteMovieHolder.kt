package edu.rosehulman.schultc2.randommoviepicker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favorite_movie_view.view.*

class FavoriteMovieHolder(itemView: View, adapter: FavoriteMovieAdapter): RecyclerView.ViewHolder(itemView) {

    val fav_movie_name: TextView = itemView.fav_movie_text_view
    var fav_movie_img: ImageView = itemView.fav_movie_img_view

    init {
        itemView.setOnClickListener {
            adapter.selectFavoriteAt(adapterPosition)
        }
    }

    fun bind(movie: FavoriteMovie){
        fav_movie_name.text = movie.movie!!.title
    }
}
