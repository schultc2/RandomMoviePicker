package edu.rosehulman.schultc2.randommoviepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

private const val ARG_MOVIE = "movie"

/**
 * A placeholder fragment containing a simple view.
 */
class MovieDescFragment : Fragment(), GetMovieTask.MovieConsumer {

    private var movieWrapper: MovieWrapper? = null


    companion object {
        fun newInstance(movie: Movie) =
            MovieDescFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieWrapper?.movie = arguments?.getParcelable(ARG_MOVIE)
        if(movieWrapper != null){
            val urlString = " http://www.omdbapi.com/?t=${movieWrapper?.movie?.title}&plot=full&apikey=7e1d379f"
            GetMovieTask(this).execute(urlString)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Initialize all the views from the MovieDescScreeb
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


        //Display the Streaming service the movie is available on
        if(movieWrapper?.movie?.netflix == 1){
            ssImageView?.setImageResource(R.drawable.netflix_button)
        } else if(movieWrapper?.movie?.disney == 1){
            ssImageView?.setImageResource(R.drawable.disney_button)
        } else if(movieWrapper?.movie?.hulu == 1){
            ssImageView?.setImageResource(R.drawable.hulu_button)
        } else if(movieWrapper?.movie?.prime == 1){
            ssImageView?.setImageResource(R.drawable.prime_button)
        }

        //Display Poster Image
        Picasso.with(context).load(movieWrapper?.poster).into(posterImageView)

        //Display Title View
        titleView?.text = movieWrapper?.movie?.title

        //Display Genres Text
        genresView?.text = movieWrapper?.movie?.genres

        //Display Year Released
        yearView?.text = "Released: ${movieWrapper?.movie?.year.toString()}"

        //Display runtime
        runtimeView?.text = "${movieWrapper?.movie?.runtime.toString()} min"

        //Display Maturity Rating
        var maturityRating: String = ""
        when(movieWrapper?.movie?.age){
            "all" -> {
                maturityRating = "Rated: G"
                true
            }
            "13+" -> {
                maturityRating = "Rated: PG-13"
                true
            }
            "18+" -> {
                maturityRating = "Rated: R"
                true
            }
            "7+" -> {
                maturityRating = "Rated: G"
                true
            }
            "16+" -> {
                maturityRating = "Rated: R"
                true
            }
            else -> {
                maturityRating = "Rated: N/A"
                true
            }
        }

        maturityView?.text = maturityRating

        //Display RatingBar
        ratingBar?.setRating(movieWrapper?.movie?.rating!!.toFloat())

        //Display directors
        directorView?.text = "Director: ${movieWrapper?.movie?.directors.toString()}"

        //Display Actors
        actorsView?.text = movieWrapper?.actors

        //Display Description
        descView?.text = movieWrapper?.plot

        //TODO: Figure out how to get and display a video trailer

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