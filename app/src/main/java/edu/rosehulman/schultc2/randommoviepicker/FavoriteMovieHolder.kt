package edu.rosehulman.schultc2.randommoviepicker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorite_movie_view.view.*

class FavoriteMovieHolder: RecyclerView.ViewHolder {

    val fav_movie_name: TextView = itemView.fav_movie_text_view
    val fav_movie_img: ImageView = itemView.fav_movie_img_view

    constructor(itemView: View) : super(itemView)

    fun bind(movie: FavoriteMovie){
        fav_movie_img.setImageResource(movie.img)
        fav_movie_name.text = movie.name
    }
}
