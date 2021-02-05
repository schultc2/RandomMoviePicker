package edu.rosehulman.schultc2.randommoviepicker

import android.os.Parcelable
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        var age: String? =  " " ?: " ",
        var directors: String = " ",
        var genres: String = " ",
        var disney : Int = 0,
        var hulu : Int = 0,
        var netflix : Int = 0,
        var id : Int = 0,
        var runtime : Int = 0,
        var prime : Int = 0,
        var rating : Float = 0.0F,
        var title : String  = " ",
        var year : Int = 0
        ) : Parcelable {
        @get:Exclude
        @ServerTimestamp
        var lastTouched: Timestamp? = null
        companion object{
                const val LAST_TOUCHED_KEY = "lastTouched"
                fun fromSnapshot(snapshot: DocumentSnapshot): Movie {
                        Log.d(Constants.TAG,snapshot.data.toString())
                        val movie = snapshot.toObject(Movie::class.java)!!

                        return movie
                }
        }
}