package edu.rosehulman.schultc2.randommoviepicker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_desc_screen.*
import kotlinx.android.synthetic.main.movie_desc_screen.view.*


private const val ARG_MOVIE = "movie"
private const val ARG_UID = "uid"
private const val ARG_FAV = "isFav"

/**
 * A placeholder fragment containing a simple view.
 */
class MovieDescFragment() : Fragment(), GetMovieTask.MovieConsumer, GetTrailerTask.TrailerConsumer{

    private var movieWrapper: MovieWrapper? = null
    private var uid: String? = null
    private var favorited : Boolean? = false


    companion object {
        fun newInstance(movie: MovieWrapper, uid: String, isFav: Boolean) =
            MovieDescFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                    putString(ARG_UID,uid)
                    putBoolean(ARG_FAV,isFav)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieWrapper = arguments?.getParcelable(ARG_MOVIE)
        uid = arguments?.getString(ARG_UID)
        favorited = arguments?.getBoolean(ARG_FAV)
        Log.d(Constants.TAG, "Chosen Movie: ${movieWrapper?.movie?.title}")
        if(movieWrapper != null){
            val urlString = "https://www.omdbapi.com/?t=${movieWrapper?.movie?.title}&plot=full&apikey=7e1d379f"
            GetMovieTask(this).execute(urlString)
            val trailerURL = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q=${movieWrapper?.movie?.title}%20Trailer&key=AIzaSyDXj7i96SWYIq_8BBP5ORoAko9NQBNo6WM"
            GetTrailerTask(this).execute(trailerURL)
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Initialize all the views from the MovieDescScreeb
        val root = inflater.inflate(R.layout.movie_desc_screen, container, false)

        //Display the Streaming service the movie is available on
        if(movieWrapper?.movie?.netflix == 1){
            root.avail_ss.setImageResource(R.drawable.netflix_button)
        } else if(movieWrapper?.movie?.disney == 1){
            root.avail_ss.setImageResource(R.drawable.disney_button)
        } else if(movieWrapper?.movie?.hulu == 1){
            root.avail_ss.setImageResource(R.drawable.hulu_button)
        } else if(movieWrapper?.movie?.prime == 1){
            root.avail_ss.setImageResource(R.drawable.prime_button)
        }


        //Display Title View
        root.movie_title.text = movieWrapper?.movie?.title

        //Display Genres Text
        root.movie_genres.text = movieWrapper?.movie?.genres

        //Display Year Released
        root.release_year_text.text = "Released: ${movieWrapper?.movie?.year.toString()}"

        //Display runtime
        root.movie_length_text.text = "${movieWrapper?.movie?.runtime.toString()} min"

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

        root.maturity_rating_text.text = maturityRating

        //Display RatingBar
        root.star_rating_bar.setRating(movieWrapper?.movie?.rating!!.toFloat() / 2)

        //Display directors
        root.director_text.text = "Director: ${movieWrapper?.movie?.directors.toString()}"

        val favButton: ImageButton? = root.fav_button

        if(!favorited!!){
            favButton?.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else{
            favButton?.setImageResource(R.drawable.ic_baseline_favorited_24)
        }

        val movieQuotesRef = FirebaseFirestore
                .getInstance()
                .collection(Constants.USERS_COLLECTION)
                .document(uid!!)
                .collection(Constants.FAVORITES_COLLECTION)

        favButton!!.setOnClickListener {
            if(!favorited!!){
                favButton.setImageResource(R.drawable.ic_baseline_favorited_24)
                movieQuotesRef.add(FavoriteMovie(movieWrapper?.movie!!,movieWrapper?.movieData!!.Poster,movieWrapper?.movie!!.id))
            } else{
                favButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                movieQuotesRef.addSnapshotListener { query, error ->
                    query?.documents?.forEach { doc : DocumentSnapshot ->
                        val currId = doc.get("refId").toString()
                        if(currId == movieWrapper!!.movie!!.id.toString()){
                            Log.d(Constants.TAG, "Remove Fav Movie: ${movieWrapper?.movie?.title}")
                            doc.reference.delete()
                        }
                    }
                }
            }
            favorited = !favorited!!
        }


        return root
    }




    override fun onMovieLoaded(movieData: MovieData?) {
          val posterImageView: ImageView? = view?.findViewById(R.id.movie_poster) ?: null
          val actorsView: TextView? = view?.findViewById(R.id.actor_text) ?: null
          val descView: TextView? = view?.findViewById(R.id.desc_text) ?: null
          //movie?.movieView = movieData
            movieWrapper?.movieData = movieData
         //Display Actors
            actorsView?.text = movieData?.Actors

         //Display Description
            descView?.text = movieData?.Plot
          //Display Poster Image
          Picasso.with(context).load(movieData?.Poster).into(posterImageView)
    }

    override fun onTrailerLoaded(trailer: TrailerData?) {
//        val trailerVideoView: YouTubePlayerView? = view?.findViewById(R.id.trailer_view) ?: null

        //videoId = trailer!!.items[0].id.videoID
//        var youtubePlayerFragment : YouTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance()
//        youtubePlayerFragment.initialize("AIzaSyDXj7i96SWYIq_8BBP5ORoAko9NQBNo6WM", this)

        val youTubePlayerView: YouTubePlayerView? = view?.findViewById(R.id.trailer_view) ?: null
        lifecycle.addObserver(youTubePlayerView!!)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = trailer!!.items[0].id.videoId
                youTubePlayer.loadVideo(videoId, 1F)
            }
        })
//        trailerVideoView?.initialize("AIzaSyDXj7i96SWYIq_8BBP5ORoAko9NQBNo6WM", YoutubeActivity(trailer!!.items[0].id.videoID))
    }



}