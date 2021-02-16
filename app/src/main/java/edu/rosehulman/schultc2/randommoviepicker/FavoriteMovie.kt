package edu.rosehulman.schultc2.randommoviepicker

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.RawValue

data class FavoriteMovie(var movie: @RawValue Movie? = null,
                         var Poster: String? = "https://i.pinimg.com/originals/96/a0/0d/96a00d42b0ff8f80b7cdf2926a211e47.jpg",
                        var refId: Int? = null){
    @get:Exclude
    var id = ""
    @ServerTimestamp
    var lastTouched: Timestamp? = null

    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"
        fun fromSnapshot(snapshot: DocumentSnapshot): FavoriteMovie {
            Log.d(Constants.TAG, snapshot.data.toString())
            val movie = snapshot.toObject(FavoriteMovie::class.java)!!
            movie.id = snapshot.id
            return movie
        }
    }
}