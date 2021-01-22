package edu.rosehulman.schultc2.randommoviepicker

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteMovieAdapter(var context: Context): RecyclerView.Adapter<FavoriteMovieHolder>() {
    private val movies = ArrayList<FavoriteMovie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.favorite_movie_view, parent, false)
        return FavoriteMovieHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteMovieHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun add(fm : FavoriteMovie){
        movies.add(fm)
        notifyItemInserted(0)
    }

    fun getItem(adapterPosition: Int) = movies[adapterPosition]

    fun remove(adapterPosition: Int){
        this.movies.removeAt(adapterPosition)
        notifyItemInserted(adapterPosition)
    }
}