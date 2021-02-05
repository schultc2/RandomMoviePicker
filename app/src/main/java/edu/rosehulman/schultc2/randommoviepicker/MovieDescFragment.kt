package edu.rosehulman.schultc2.randommoviepicker

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val ARG_MOVIE = "movie"

/**
 * A placeholder fragment containing a simple view.
 */
class MovieDescFragment : Fragment(), GetMovieTask.MovieConsumer, {

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


        //textView?.text = " "
        textView?.text = movieWrapper?.movie?.safe_titlec
        imageView?.setImageBitmap(movieWrapper?.image)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(ARG_COMIC) }?.apply {
            val textView: TextView = view.findViewById(R.id.section_label)
            textView.text = getInt(ARG_COMIC).toString()
            view.setBackgroundResource(movieWrapper?.color!!)
        }
    }

    override fun onComicLoaded(comic: Comic?) {
        comicWrapper?.comic = comic
        val textView: TextView? = view?.findViewById(R.id.section_label) ?: null
        section_label.text = comic?.safe_title
        saved_title = comic?.safe_title
        GetImageTask(this).execute(comic!!.img)
    }

    override fun onMovieLoaded(movie: Movie?) {
        comicWrapper?.comic = comic
        val textView: TextView? = view?.findViewById(R.id.section_label) ?: null
        section_label.text = comic?.safe_title
        saved_title = comic?.safe_title
        GetImageTask(this).execute(comic!!.img)
    }


}