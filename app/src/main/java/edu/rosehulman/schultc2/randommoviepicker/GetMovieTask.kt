package edu.rosehulman.schultc2.randommoviepicker

import android.os.AsyncTask
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.lang.Exception
import java.net.URL

class GetMovieTask(private var movieConsumer: MovieConsumer): AsyncTask<String, Void, MovieData>() {

    override fun doInBackground(vararg urlStrings: String?): MovieData? {
        val url = URL(urlStrings[0])
        return try{
            val s = url.readText()
            Log.d(Constants.BAC,"Url: $url")
            val mapper = ObjectMapper().registerModule(KotlinModule())
            val movie = mapper.readValue<MovieData>(s)
            Log.d(Constants.BAC,"As movie: ${movie.toString()}")
            movie
        } catch (e: Exception) {
            Log.d(Constants.BAC,"EXCEPTION:"+ e.toString())
            null
        }
    }

    override fun onPostExecute(result: MovieData?) {
        super.onPostExecute(result)
        movieConsumer.onMovieLoaded(result)
    }

    interface MovieConsumer {
        fun onMovieLoaded(movie: MovieData?)
    }
}

