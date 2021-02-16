package edu.rosehulman.schultc2.randommoviepicker

import android.os.Parcelable
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class MovieWrapper(var movie: @RawValue Movie?, var movieData: @RawValue MovieData?): Parcelable
