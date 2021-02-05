package edu.rosehulman.schultc2.randommoviepicker

data class Movie(
        var age: String = "",
        var directors: String = "",
        var genres: String = "",
        var disney : Int = 0,
        var hulu : Int = 0,
        var netflix : Int = 0,
        var prime : Int = 0,
        var runtime : Int = 0,
        var ID : Int = 0,
        var rating : Double = 0.0,
        var title : String,
        var year : Int = 0,
        ) {
}