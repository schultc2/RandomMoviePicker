package edu.rosehulman.schultc2.randommoviepicker

import java.lang.reflect.GenericArrayType

data class Movie(
        var age: String = "",
        var directors: String = "",
        var disney: Int = 0,
        var genres: String = "",
        var hulu: Int = 0,
        var id: String = "",
        var netflix: Int = 0,
        var prime: Int = 0,
        var rating: Double = 6.4,
        var runtime: Int = 0,
        var title: String = "",
        var year: Int = 0

        ) {

        fun resetMovieParam(age: String, directors: String, disney: Int, genres: String,
                                    hulu: Int, id: String, netflix: Int, prime: Int, rating: Double,
                                    runtime: Int, title: String, year: Int){
                this.age = age
                this.directors = directors
                this.disney = disney
                this.genres = genres
                this.hulu = hulu
                this.id = id
                this.netflix = netflix
                this.prime = prime
                this.rating = rating
                this.runtime = runtime
                this.title = title
                this.year = year

        }
}