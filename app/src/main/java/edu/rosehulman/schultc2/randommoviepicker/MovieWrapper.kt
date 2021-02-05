package edu.rosehulman.schultc2.randommoviepicker

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class MovieWrapper(var poster: String = "https://i.pinimg.com/originals/96/a0/0d/96a00d42b0ff8f80b7cdf2926a211e47.jpg",
                        var plot: String = "Description not loaded",
                        var actors: String = "Actors not loaded",
                        var movie: @RawValue Movie?) : Parcelable{
}