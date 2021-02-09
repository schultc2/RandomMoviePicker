package edu.rosehulman.schultc2.randommoviepicker

import org.json.JSONArray


data class MovieData(var Poster: String = "https://i.pinimg.com/originals/96/a0/0d/96a00d42b0ff8f80b7cdf2926a211e47.jpg",
                     var Plot: String = "Description not loaded",
                     var Actors: String = "Actors not loaded",
                     var Title: String = "Title Here",
                     var Year: String = "20XX",
                     var Rated: String = "XX",
                     var Released: String =  "Date here",
                     var Runtime: String = "xx min",
                     var Genre: String = "Genres Here",
                     var Director: String = "Directors Here",
                     var Writer: String = "Writers Here",
                     var Language: String = "Language",
                     var Country: String = "USA",
                     var Awards: String = "Awards",
                     var Ratings: Array<Rating>,
                     var Metascore: String = "100",
                     var imdbRating: String,
                     var imdbVotes: String,
                     var imdbID: String,
                     var Type: String,
                     var DVD: String,
                     var BoxOffice: String,
                     var Production: String,
                     var Website: String,
                     var Response: String
                        )

