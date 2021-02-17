package edu.rosehulman.schultc2.randommoviepicker

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieListAdapter(var context: Context,
                       var listener: MovieList.OnMovieSelectedListener
                       ): RecyclerView.Adapter<MovieViewHolder>() {
    private val movies = ArrayList<MovieWrapper>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.favorite_movie_view, parent, false)
        return MovieViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun selectMovieAt(adapterPosition: Int){
        val movie = movies[adapterPosition]
        listener?.onMovieSelected(MovieWrapper(movie.movie,null))
    }

    fun setMovies(goodMovies : ArrayList<Movie>){
        for(movie in goodMovies){
            movies.add(MovieWrapper(movie,null))
        }
    }

    fun loadPoster(movieData: MovieData?, holder: MovieViewHolder, position: Int){
        Picasso.with(context).load(movieData?.Poster).into(holder.fav_movie_img)
    }

    fun add(fm : MovieWrapper){
        movies.add(fm)
        notifyItemInserted(0)
    }

    fun getItem(adapterPosition: Int) = movies[adapterPosition]

    fun remove(adapterPosition: Int){
        this.movies.removeAt(adapterPosition)
        notifyItemInserted(adapterPosition)
    }
}