package edu.rosehulman.schultc2.randommoviepicker

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment

private const val ARG_MOVIE = "movie"

/**
 * A placeholder fragment containing a simple view.
 */
class MovieDescFragment : Fragment(), GetMovieTask.MovieConsumer {

    private var movieWrapper: MovieWrapper? = null
    private var saved_title: String? = null


    companion object {
        fun newInstance(movie: MovieWrapper) =
            MovieDescFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieWrapper = arguments?.getParcelable(ARG_MOVIE)
        if(movieWrapper != null){
            val urlString = " http://www.omdbapi.com/?t=${movieWrapper?.movie?.title}&plot=full&apikey=7e1d379f"
            GetMovieTask(this).execute(urlString)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.movie_desc_screen, container, false)
        val ssImageView: ImageView? = view?.findViewById(R.id.avail_ss) ?: null
        val posterImageView: ImageView? = view?.findViewById(R.id.movie_poster) ?: null
        val titleView: TextView? = view?.findViewById(R.id.movie_title) ?: null
        val genresView: TextView? = view?.findViewById(R.id.movie_genres) ?: null
        val yearView: TextView? = view?.findViewById(R.id.release_year_text) ?: null
        val runtimeView: TextView? = view?.findViewById(R.id.movie_length_text) ?: null
        val maturityView: TextView? = view?.findViewById(R.id.maturity_rating_text) ?: null
        val ratingBar: RatingBar? = view?.findViewById(R.id.star_rating_bar) ?: null
        val directorView: TextView? = view?.findViewById(R.id.director_text) ?: null
        val actorsView: TextView? = view?.findViewById(R.id.actor_text) ?: null
        val descView: TextView? = view?.findViewById(R.id.desc_text) ?: null
        val trailerView: VideoView? = view?.findViewById(R.id.trailer_view) ?: null

        //textView?.text = " "
        titleView?.text = movieWrapper?.movie?.title
        //imageView?.setImageBitmap(movieWrapper?.image)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        arguments?.takeIf { it.containsKey(ARG_MOVIE) }?.apply {
//            val textView: TextView = view.findViewById(R.id.section_label)
//            textView.text = getInt(ARG_COMIC).toString()
//            view.setBackgroundResource(movieWrapper?.color!!)
//        }
    }

    override fun onMovieLoaded(movie: Movie?) {
        movieWrapper?.movie = movie
//        val textView: TextView? = view?.findViewById(R.id.section_label) ?: null
//        section_label.text = comic?.safe_title
//        saved_title = comic?.safe_title
//        GetImageTask(this).execute(comic!!.img)
    }



}