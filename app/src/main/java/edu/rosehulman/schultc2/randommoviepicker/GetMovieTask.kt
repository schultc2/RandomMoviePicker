package edu.rosehulman.schultc2.randommoviepicker

import android.os.AsyncTask
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.lang.Exception
import java.net.URL

class GetMovieTask(private var movieConsumer: MovieConsumer): AsyncTask<String, Void, Movie>() {

    override fun doInBackground(vararg urlStrings: String?): Movie? {
        val url = URL(urlStrings[0])
        return try{
            val s = url.readText()
            Log.d(Constants.TAG,"Url: $url")
            val mapper = ObjectMapper().registerModule(KotlinModule())
            val movie = mapper.readValue<Movie>(s)
            Log.d(Constants.TAG,"As movie: ${movie.toString()}")
            movie
        } catch (e: Exception) {
            Log.d(Constants.TAG,"EXCEPTION:"+ e.toString())
            null
        }
    }

    override fun onPostExecute(result: Movie?) {
        super.onPostExecute(result)
        movieConsumer.onMovieLoaded(result)
    }

    interface MovieConsumer {
        fun onMovieLoaded(movie: Movie?)
    }
}