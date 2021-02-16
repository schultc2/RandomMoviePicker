//package edu.rosehulman.schultc2.randommoviepicker
//
//import android.view.View
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.squareup.picasso.Picasso
//import kotlinx.android.synthetic.main.favorite_movie_view.view.*
//
//class MovieViewHolder(itemView: View, adapter: MovieListAdapter): RecyclerView.ViewHolder {
//    val fav_movie_name: TextView = itemView.fav_movie_text_view
//    val fav_movie_img: ImageView = itemView.fav_movie_img_view
//
//
//    init {
//        itemView.setOnClickListener {
//            adapter.selectMovieAt(adapterPosition)
//        }
//    }
//
//    fun bind(movieWrapper: MovieWrapper){
//        fav_movie_img.setImageResource(R.drawable.default_poster)
//        fav_movie_name.text = movieWrapper.movie?.title
//    }
//
//}