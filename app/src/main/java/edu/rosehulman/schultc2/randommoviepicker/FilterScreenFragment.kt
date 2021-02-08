package edu.rosehulman.schultc2.randommoviepicker

import android.accessibilityservice.GestureDescription
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.*
import com.google.firebase.firestore.core.QueryListener
import java.lang.NumberFormatException
import java.time.Year

class FilterScreenFragment: Fragment() {

    private var listener: FilterMovieListener? = null
    private var genres: ArrayList<RadioButton> = arrayListOf()
//    private var netflixSelected: Boolean = false
//    private var primeSelected: Boolean = false
//    private var huluSelected: Boolean = false
//    private var disneySelected: Boolean = false
    lateinit var netflixButton : ToggleButton
    lateinit var huluButton : ToggleButton
    lateinit var disneyButton : ToggleButton
    lateinit var primeButton : ToggleButton
    lateinit var maturityEditText: EditText
    private var fromRating: Double = 0.0
    private var toRating: Double = 5.0
//    private var fromYear: String? = "1970"
//    private var toYear: String? = "2021"
//    private var maturity: String? = null
    lateinit var fromYearEditText: EditText
    lateinit var toYearEditText: EditText
    private var actors: String?  = null
    private var keyword: String? = null
    private val movies = ArrayList<Movie>()
    private val randomMovies = ArrayList<FavoriteMovie>()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.filter_screen, container, false)

        //Streaming service buttons
        netflixButton = rootView.findViewById(R.id.netflix_filter)
        primeButton = rootView.findViewById(R.id.prime_filter)
        huluButton = rootView.findViewById(R.id.hulu_filter)
        disneyButton = rootView.findViewById(R.id.disney_filter)

//        netflixSelected = netflixButton?.isSelected ?: false
//        primeSelected = primeButton?.isSelected ?: false
//        huluSelected = huluButton?.isSelected ?: false
//        disneySelected = disneyButton?.isSelected ?: false


        //RatingBars
        val fromRatingBar: RatingBar? = rootView?.findViewById(R.id.from_star_rating) ?: null
        val toRatingBar: RatingBar? = rootView?.findViewById(R.id.to_star_rating) ?: null

        fromRatingBar!!.setOnRatingBarChangeListener { ratingBar, fl, b ->
            fromRating = ratingBar.rating.toDouble()
        }
        toRatingBar!!.setOnRatingBarChangeListener { ratingBar, fl, b ->
            toRating = ratingBar.rating.toDouble()
        }


        //Year Input
        fromYearEditText  = rootView.findViewById(R.id.from_year_drop)!!
        toYearEditText = rootView.findViewById(R.id.to_year_drop)



        //Genre Radio Buttons
        val radioGroup1 : RadioGroup? = rootView?.findViewById(R.id.radio1) ?: null
        val radioGroup2 : RadioGroup? = rootView?.findViewById(R.id.radio2) ?: null
        val radioGroup3 : RadioGroup? = rootView?.findViewById(R.id.radio3) ?: null
        val radioGroup4 : RadioGroup? = rootView?.findViewById(R.id.radio4) ?: null
        val radioGroup5 : RadioGroup? = rootView?.findViewById(R.id.radio5) ?: null


        for( i in 0 until (radioGroup1?.childCount ?: 0)){
            genres.add(radioGroup1?.getChildAt(i) as RadioButton)
        }
        for( i in 0 until (radioGroup2?.childCount ?: 0)){
            genres.add(radioGroup2?.getChildAt(i) as RadioButton)
        }
        for( i in 0 until (radioGroup3?.childCount ?: 0)){
            genres.add(radioGroup3?.getChildAt(i) as RadioButton)
        }
        for( i in 0 until (radioGroup4?.childCount ?: 0)){
            genres.add(radioGroup4?.getChildAt(i) as RadioButton)
        }
        for( i in 0 until (radioGroup5?.childCount ?: 0)){
            genres.add(radioGroup5?.getChildAt(i) as RadioButton)
        }

//        val comedyRadio : RadioButton? = rootView?.findViewById(R.id.comedy_radio) ?: null
//        val scifiRadio : RadioButton? = rootView?.findViewById(R.id.scifi_radio) ?: null
//        val supernaturalRadio : RadioButton? = rootView?.findViewById(R.id.supernatural_radio) ?: null
//        val feelgoodRadio : RadioButton? = rootView?.findViewById(R.id.feelgood_radio) ?: null
//        val westernRadio : RadioButton? = rootView?.findViewById(R.id.western_radio) ?: null
//        val foreignRadio : RadioButton? = rootView?.findViewById(R.id.foreign_radio) ?: null
//        val actionRadio : RadioButton? = rootView?.findViewById(R.id.action_radio) ?: null
//        val dramaRadio : RadioButton? = rootView?.findViewById(R.id.drama_radio) ?: null
//        val animatedRadio : RadioButton? = rootView?.findViewById(R.id.animated_radio) ?: null
//        val thrillerRadio : RadioButton? = rootView?.findViewById(R.id.thriller_radio) ?: null
//        val fantasyRadio : RadioButton? = rootView?.findViewById(R.id.fantasy_radio) ?: null
//        val adventureRadio : RadioButton? = rootView?.findViewById(R.id.adventure_radio) ?: null
//        val romanceRadio : RadioButton? = rootView?.findViewById(R.id.romance_radio) ?: null
//        val docuRadio : RadioButton? = rootView?.findViewById(R.id.documentary_radio) ?: null
//        val crimeRadio : RadioButton? = rootView?.findViewById(R.id.crime_radio) ?: null
//        val horrorRadio : RadioButton? = rootView?.findViewById(R.id.horror_radio) ?: null
//        val familyRadio : RadioButton? = rootView?.findViewById(R.id.family_radio) ?: null
//        val sportsRadio : RadioButton? = rootView?.findViewById(R.id.sports_radio) ?: null
//        val mysteryRadio : RadioButton? = rootView?.findViewById(R.id.mystery_radio) ?: null
//        val classicRadio : RadioButton? = rootView?.findViewById(R.id.classic_radio) ?: null

        //Maturity Rating Input
        maturityEditText = rootView?.findViewById(R.id.maturity_rating_input)!!

        //Actors Input
        val actorsEditText : EditText? = rootView?.findViewById(R.id.actors_input) ?: null

        //Keyword Input
        val keywordEditText : EditText? = rootView?.findViewById(R.id.keyword_input) ?: null


        val searchButton: Button? = rootView?.findViewById(R.id.search_button) ?: null

        val randomButton: Button? = rootView?.findViewById(R.id.random_button) ?: null

        searchButton?.setOnClickListener {
            Log.d(Constants.TAG, "searchButton selected")
            //check the status of Streaming Toggle Button
            Log.d(Constants.TAG, "Disney: ${disneyButton.isChecked}, Hulu: ${huluButton.isChecked}, Netflix: ${netflixButton.isChecked}, Prime: ${primeButton.isChecked}")

            //check the status of Genres Radio Button
            for(rb in genres){
                Log.d(Constants.TAG, "${rb.text}: ${rb.isChecked}")
            }

            //check the status of Maturity EditText
            Log.d(Constants.TAG, "Maturiy: ${this.maturityEditText.text}")

            //check the status of Year EditText
            Log.d(Constants.TAG, "From Year: ${this.fromYearEditText.text}, To Year: ${this.toYearEditText.text}")

            val moviesRef : CollectionReference = FirebaseFirestore
                    .getInstance()
                    .collection("FormattedMovies")
            moviesRef.get().addOnSuccessListener {
                snapshot : QuerySnapshot ->
                var docCount = 0
                for(doc in snapshot){
                    docCount++
                    var title = (doc["title"] ?: "None") as String
                    var maturity = (doc["age"] ?: "") as String
                    if(maturity.equals("")){
                        maturity = "0"
                    }else{
                        maturity = maturity.substring(0, maturity.length - 1)
                    }
                    var genres = (doc["genres"] ?: "") as String
                    var runtimeInString = (doc["runetime"] ?: "") as String
                    var runtime : Int

                    //some runtime's data type are string but some are number, so
                    //it's hard to convert them using toObject() method. Therefore
                    //each value is fetched separately from the firebase and pass
                    //into the movie parameter
                    if(runtimeInString.equals("")){
                        runtime = 0
                    }else{
                        runtime = runtimeInString.toInt()
                    }
                    var directors = (doc["directors"] ?: "") as String
                    var disney = (doc["disney"] ?: 0.toLong()) as Long
                    var hulu = (doc["hulu"] ?: 0.toLong()) as Long
                    var id = doc.id
                    var netflix = (doc["netflix"] ?: 0.toLong()) as Long
                    var prime = (doc["prime"] ?: "") as Long
//                    var ratingInString = (doc["rating"] ?: "") as String
//                    var rating : Double
//                    if(ratingInString.equals("")){
//                        rating = 0.0
//                    }else{
//                        rating = ratingInString.toDouble()
//                    }
                    var year = (doc["year"] ?: 0.toLong()) as Long




                    val movie = Movie()

                    if(this.includeGenres(genres) && this.includeStreaming(disney.toInt(), hulu.toInt(), netflix.toInt(), prime.toInt())
                            && this.includeMaturity(maturity) && this.includeYear(year.toInt())) {
                        movie.resetMovieParam(maturity, directors, disney.toInt(), genres, hulu.toInt(),
                                id, netflix.toInt(), prime.toInt(), 10.0, runtime, title, year.toInt())
                        movies.add(movie)
                        val movieSummary = "ID: ${doc.id}, Title: $title, Maturity: $maturity, Genres: $genres"
                        Log.d(Constants.TAG, movieSummary)
                    }




//                    val movieSummary = "ID: ${doc.id}, Title: $title, Maturity: $maturity, Genres: $genres"
//                    val movie = doc.toObject(Movie::class.java)
//                    this.movies.add(movie)
//                    Log.d(Constants.TAG, movieSummary)

                }
                Log.d(Constants.TAG, "$docCount vs ${movies.size}")
            }
        }

        randomButton?.setOnClickListener{
            getRandomMovie()
        }

        return rootView
    }

    private fun getRandomMovie(){
        val moviesRef : CollectionReference = FirebaseFirestore
                .getInstance()
                .collection("Movies")
//        val query = moviesRef.orderBy("ID", Query.Direction.DESCENDING)
//        query.addSnapshotListener { querySnapshot, error ->
//
//        }
        moviesRef.get().addOnSuccessListener {
            snapshot : QuerySnapshot ->
            var i = 5
            for(doc in snapshot){
                if(i <= 0) break
                val title = (doc["Title"] ?: " ") as String
                this.randomMovies.add(FavoriteMovie(0, title))
                Log.d(Constants.TAG, title)
                i--;

            }
        }

    }

    private fun getAllMovies(){

    }

    interface FilterMovieListener {
        fun getMovieFragment(frag: Fragment)
    }

    private fun getFilteredMovies(){

    }

    private fun includeStreaming(disney: Int, hulu: Int, netflix: Int, prime: Int) : Boolean{
        if(disney == 1 && disneyButton.isChecked) return true
        if(hulu == 1 && huluButton.isChecked) return true
        if(netflix == 1 && netflixButton.isChecked) return true
        if(prime == 1 && primeButton.isChecked) return true
        return false
    }

    private fun includeGenres(genres: String) : Boolean{
        for(rb in this.genres){
            if(genres.contains(rb.text) && rb.isChecked){
                return true
            }
        }
        return false
    }

    private fun includeMaturity(maturity : String) : Boolean {
        val movieMaturity = try {
            this.maturityEditText.text.toString().toInt()
        }catch (e : NumberFormatException){
            0
        }
        return movieMaturity <= maturity.toInt()
    }

    private fun includeYear(year: Int) : Boolean{
        val fromYear = try {
            this.fromYearEditText.text.toString().toInt()
        }catch (e : NumberFormatException){
            1800
        }
        val toYear = try {
            this.toYearEditText.text.toString().toInt()
        }catch (e : NumberFormatException){
            2021
        }

        return ((year <= toYear) && (year >= fromYear))
    }
}