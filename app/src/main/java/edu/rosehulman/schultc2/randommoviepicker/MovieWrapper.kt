package edu.rosehulman.schultc2.randommoviepicker

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class MovieWrapper(var movie: @RawValue Movie?, var movieData: @RawValue MovieData?): Parcelable
