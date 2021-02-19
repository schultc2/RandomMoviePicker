package edu.rosehulman.schultc2.randommoviepicker

import android.os.AsyncTask
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.lang.Exception
import java.net.URL

class GetTrailerTask(private var trailerConsumer: TrailerConsumer): AsyncTask<String, Void, TrailerData>() {

    override fun doInBackground(vararg urlStrings: String?): TrailerData? {
        val url = URL(urlStrings[0])
        return try{
            val s = url.readText()
            Log.d(Constants.BAC,"Url: $url")
            val mapper = ObjectMapper().registerModule(KotlinModule())
            val trailer = mapper.readValue<TrailerData>(s)
            Log.d(Constants.BAC,"As trailer: ${trailer.toString()}")
            trailer
        } catch (e: Exception) {
            Log.d(Constants.BAC,"EXCEPTION:"+ e.toString())
            null
        }
    }
    override fun onPostExecute(result: TrailerData?) {
        super.onPostExecute(result)
        trailerConsumer.onTrailerLoaded(result)
    }

    interface TrailerConsumer {
        fun onTrailerLoaded(trailer: TrailerData?)
    }
}