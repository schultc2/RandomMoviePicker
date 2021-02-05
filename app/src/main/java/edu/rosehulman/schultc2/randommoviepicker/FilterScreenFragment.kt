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
import java.time.Year

class FilterScreenFragment: Fragment() {

    private var listener: FilterMovieListener? = null
    private var genres: ArrayList<RadioButton> = arrayListOf()
    private var netflixSelected: Boolean = false
    private var primeSelected: Boolean = false
    private var huluSelected: Boolean = false
    private var disneySelected: Boolean = false
    private var fromRating: Double = 0.0
    private var toRating: Double = 5.0
    private var fromYear: String? = "1970"
    private var toYear: String? = "2021"
    private var maturity: String? = null
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
        val netflixButton: ToggleButton? = rootView?.findViewById(R.id.netflix_filter) ?: null
        val primeButton: ToggleButton? = rootView?.findViewById(R.id.prime_filter) ?: null
        val huluButton: ToggleButton? = rootView?.findViewById(R.id.hulu_filter) ?: null
        val disneyButton: ToggleButton? = rootView?.findViewById(R.id.disney_filter) ?: null

        netflixSelected = netflixButton?.isSelected ?: false
        primeSelected = primeButton?.isSelected ?: false
        huluSelected = huluButton?.isSelected ?: false
        disneySelected = disneyButton?.isSelected ?: false


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
        val fromYearEditText : EditText? = rootView?.findViewById(R.id.from_year_drop) ?: null
        val toYearEditText : EditText? = rootView?.findViewById(R.id.to_year_drop) ?: null



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
        val maturityEditText : EditText? = rootView?.findViewById(R.id.maturity_rating_input) ?: null

        //Actors Input
        val actorsEditText : EditText? = rootView?.findViewById(R.id.actors_input) ?: null

        //Keyword Input
        val keywordEditText : EditText? = rootView?.findViewById(R.id.keyword_input) ?: null


        val searchButton: Button? = rootView?.findViewById(R.id.search_button) ?: null

        val randomButton: Button? = rootView?.findViewById(R.id.random_button) ?: null

        searchButton?.setOnClickListener {
            Log.d(Constants.TAG, "searchButton selected")
            val moviesRef : CollectionReference = FirebaseFirestore
                    .getInstance()
                    .collection("Movies")
            moviesRef.get().addOnSuccessListener {
                snapshot : QuerySnapshot ->
                for(doc in snapshot){
                    if(YearCheck(doc.id) ){
                        var title = (doc["Title"] ?: "Error") as String
                        var movie = Movie()
                        movie.title = title
                        movies.add(movie)
                    }
                }

            }
            for (movie in this.movies){
                Log.d(Constants.TAG, movie.title)
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

    private fun AgeCheck(id: String) : Boolean {
        if (this.maturity == null) {
            return true
        }
        var check: Boolean = false
        val ref: DocumentReference = FirebaseFirestore
                .getInstance()
                .collection("Movies")
                .document(id)
        ref.get().addOnSuccessListener { snapshot: DocumentSnapshot ->
            var age = (snapshot["Age"] ?: "0") as String
            if (age.toInt() < this.maturity!!.toInt()) {
                check = true

            }
        }

        Log.d(Constants.TAG, check.toString())
        return check
    }

    private fun StreamingServiceCheck(id : String) : Boolean{
        var check: Boolean = false
        val ref: DocumentReference = FirebaseFirestore
                .getInstance()
                .collection("Movies")
                .document(id)
        ref.get().addOnSuccessListener {snapshot: DocumentSnapshot ->
            var netflix = (snapshot["Netflix"] ?: 0) as Int
            var hulu = (snapshot["Hulu"] ?: 0) as Int
            var prime = (snapshot["Prime Video"] ?: 0) as Int
            var disney = (snapshot["Disney+"] ?: 0) as Int
            if(netflixSelected && (netflix == 1)) check = true
            if(huluSelected && (hulu == 1)) check = true
            if(primeSelected && (prime == 1)) check = true
            if(disneySelected && (disney == 1)) check = true
        }
        Log.d(Constants.TAG, check.toString())
        return check
    }

    private fun YearCheck(id : String) : Boolean{
        var check: Boolean = false;
        val ref: DocumentReference = FirebaseFirestore
                .getInstance()
                .collection("Movies")
                .document(id)
        ref.get().addOnSuccessListener { snapshot: DocumentSnapshot ->
            val year = (snapshot["Year"] ?: 0) as Int
            if(fromYear == null) {
                if (toYear == null) {
                    check = true
                }else{
                    check = (year <= toYear!!.toInt())
                }
            }else{
                if(toYear == null){
                    check = (year >= fromYear!!.toInt())
                }else{
                    check = ((year <= toYear!!.toInt()) && (year >= fromYear!!.toInt()))
                }
            }

        }
        Log.d(Constants.TAG, check.toString())
        return check
    }

    private fun GenreCheck(id : String) : Boolean{
        var check: Boolean = true;
        val ref: DocumentReference = FirebaseFirestore
                .getInstance()
                .collection("Movies")
                .document(id)
        ref.get().addOnSuccessListener { snapshot: DocumentSnapshot ->
            val genre = (snapshot["Genres"] ?: "") as String
            for(rb in this.genres) {
                if (rb.isSelected && !genre.contains(rb.text)) {
                    check = false
                    break
                }
            }
        }
        Log.d(Constants.TAG, check.toString())
        return check


    }
}