package edu.rosehulman.schultc2.randommoviepicker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FilterScreenFragment: Fragment() {

    private var listener: FilterMovieListener? = null
    private var genres: ArrayList<CheckBox> = arrayListOf()
    private var netflixSelected: Boolean = false
    private var primeSelected: Boolean = false
    private var huluSelected: Boolean = false
    private var disneySelected: Boolean = false
    private var fromRating: Double = 0.0
    private var toRating: Double = 5.0
    private var fromYear: Int? = 1970
    private var toYear: Int? = 2021
    private var maturity: String? = null
    private var actors: String?  = null
    private var keyword: String? = null
    private var movies = ArrayList<Movie>()

    private var netflixButton: ToggleButton? = null
    private var primeButton: ToggleButton? = null
    private var disneyButton: ToggleButton? = null
    private var huluButton: ToggleButton? = null

    var fromRatingBar: RatingBar? = null
    var toRatingBar: RatingBar? = null

    var fromYearEditText : EditText? = null
    var toYearEditText : EditText? = null

    var maturityEditText : EditText? = null

    var actorsEditText : EditText? = null

    var keywordEditText : EditText? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.filter_screen, container, false)

        //Streaming service buttons
        netflixButton= rootView?.findViewById(R.id.netflix_filter) ?: null
        primeButton= rootView?.findViewById(R.id.prime_filter) ?: null
        huluButton= rootView?.findViewById(R.id.hulu_filter) ?: null
        disneyButton = rootView?.findViewById(R.id.disney_filter) ?: null

        netflixButton!!.setOnClickListener {
            netflixSelected = !netflixSelected
        }
        primeButton!!.setOnClickListener {
            primeSelected = !primeSelected
        }
        huluButton!!.setOnClickListener {
            huluSelected = !huluSelected
        }
        disneyButton!!.setOnClickListener {
            disneySelected = !disneySelected
        }

        //RatingBars
        fromRatingBar = rootView?.findViewById(R.id.from_star_rating) ?: null
        toRatingBar  = rootView?.findViewById(R.id.to_star_rating) ?: null

        fromRatingBar!!.setOnRatingBarChangeListener { ratingBar, fl, b ->
            fromRating = ratingBar.rating.toDouble()
        }
        toRatingBar!!.setOnRatingBarChangeListener { ratingBar, fl, b ->
            toRating = ratingBar.rating.toDouble()
        }


        //Year Input
        fromYearEditText = rootView?.findViewById(R.id.from_year_drop) ?: null
        toYearEditText = rootView?.findViewById(R.id.to_year_drop) ?: null




        //Genre Radio Buttons
//        val radioGroup1 : RadioGroup? = rootView?.findViewById(R.id.radio1) ?: null
//        val radioGroup2 : RadioGroup? = rootView?.findViewById(R.id.radio2) ?: null
//        val radioGroup3 : RadioGroup? = rootView?.findViewById(R.id.radio3) ?: null
//        val radioGroup4 : RadioGroup? = rootView?.findViewById(R.id.radio4) ?: null
//        val radioGroup5 : RadioGroup? = rootView?.findViewById(R.id.radio5) ?: null

//        for( i in 0 until (radioGroup1?.childCount ?: 0)){
//            genres.add(radioGroup1?.getChildAt(i) as RadioButton)
//        }
//        for( i in 0 until (radioGroup2?.childCount ?: 0)){
//            genres.add(radioGroup2?.getChildAt(i) as RadioButton)
//        }
//        for( i in 0 until (radioGroup3?.childCount ?: 0)){
//            genres.add(radioGroup3?.getChildAt(i) as RadioButton)
//        }
//        for( i in 0 until (radioGroup4?.childCount ?: 0)){
//            genres.add(radioGroup4?.getChildAt(i) as RadioButton)
//        }
//        for( i in 0 until (radioGroup5?.childCount ?: 0)){
//            genres.add(radioGroup5?.getChildAt(i) as RadioButton)
//        }

        val comedyRadio : CheckBox? = rootView?.findViewById(R.id.comedy_radio) ?: null
        val scifiRadio : CheckBox? = rootView?.findViewById(R.id.scifi_radio) ?: null
        val supernaturalRadio : CheckBox? = rootView?.findViewById(R.id.supernatural_radio) ?: null
        val feelgoodRadio : CheckBox? = rootView?.findViewById(R.id.feelgood_radio) ?: null
        val westernRadio : CheckBox? = rootView?.findViewById(R.id.western_radio) ?: null
        val foreignRadio : CheckBox? = rootView?.findViewById(R.id.foreign_radio) ?: null
        val actionRadio : CheckBox? = rootView?.findViewById(R.id.action_radio) ?: null
        val dramaRadio : CheckBox? = rootView?.findViewById(R.id.drama_radio) ?: null
        val animatedRadio : CheckBox? = rootView?.findViewById(R.id.animated_radio) ?: null
        val thrillerRadio : CheckBox? = rootView?.findViewById(R.id.thriller_radio) ?: null
        val fantasyRadio : CheckBox? = rootView?.findViewById(R.id.fantasy_radio) ?: null
        val adventureRadio : CheckBox? = rootView?.findViewById(R.id.adventure_radio) ?: null
        val romanceRadio : CheckBox? = rootView?.findViewById(R.id.romance_radio) ?: null
        val docuRadio : CheckBox? = rootView?.findViewById(R.id.documentary_radio) ?: null
        val crimeRadio : CheckBox? = rootView?.findViewById(R.id.crime_radio) ?: null
        val horrorRadio : CheckBox? = rootView?.findViewById(R.id.horror_radio) ?: null
        val familyRadio : CheckBox? = rootView?.findViewById(R.id.family_radio) ?: null
        val sportsRadio : CheckBox? = rootView?.findViewById(R.id.sports_radio) ?: null
        val mysteryRadio : CheckBox? = rootView?.findViewById(R.id.mystery_radio) ?: null
        val classicRadio : CheckBox? = rootView?.findViewById(R.id.classic_radio) ?: null

        genres.add(comedyRadio!!)
        genres.add(scifiRadio!!)
        genres.add(supernaturalRadio!!)
        genres.add(feelgoodRadio!!)
        genres.add(westernRadio!!)
        genres.add(foreignRadio!!)
        genres.add(actionRadio!!)
        genres.add(dramaRadio!!)
        genres.add(animatedRadio!!)
        genres.add(thrillerRadio!!)
        genres.add(fantasyRadio!!)
        genres.add(adventureRadio!!)
        genres.add(romanceRadio!!)
        genres.add(docuRadio!!)
        genres.add(crimeRadio!!)
        genres.add(horrorRadio!!)
        genres.add(familyRadio!!)
        genres.add(sportsRadio!!)
        genres.add(mysteryRadio!!)
        genres.add(classicRadio!!)




        //Maturity Rating Input
        maturityEditText = rootView?.findViewById(R.id.maturity_rating_input) ?: null

        //Actors Input
        actorsEditText = rootView?.findViewById(R.id.actors_input) ?: null

        //Keyword Input
        keywordEditText = rootView?.findViewById(R.id.keyword_input) ?: null

        val searchButton: Button? = rootView?.findViewById(R.id.search_button) ?: null

        searchButton?.setOnClickListener {

            grabAllMovies()
        }

        return rootView
    }

    private fun grabAllMovies(){
        val moviesRef : CollectionReference = FirebaseFirestore
                .getInstance()
                .collection("TestMovies")
        val query = moviesRef.orderBy("id", Query.Direction.DESCENDING)
        query.addSnapshotListener { querySnapshot, error ->
            if(error != null){
                Log.e(Constants.TAG, "Error getting movies: $error")
                return@addSnapshotListener
            }
            movies.removeAll(movies)
            querySnapshot?.documents?.forEach { documentSnapshot: DocumentSnapshot ->
                movies.add(Movie.fromSnapshot(documentSnapshot))
            }
            getFilteredMovie(moviesRef)
        }
    }

    private fun getFilteredMovie(moviesRef : CollectionReference){
        Log.d(Constants.TAG,"Filtering Movies")
        var goodMovies = ArrayList<Movie>()
        if(movies.isEmpty()){
            Log.d(Constants.TAG,"Movies is empty")
        }
        for(currMovie in movies){
            //Filter Streaming Service
            var goodService = false
            Log.d(Constants.TAG,"Currently Checking $currMovie.title")


            //Filter Rating
            //var myRating = currMovie.rating.toFloat()

            var goodRating = true

            //Filter year
            var myYear = currMovie.year.toInt()
            var goodYear = true

            if(fromYearEditText?.text.toString() != ""){
                fromYear = fromYearEditText?.text.toString().toInt()
            } else {
                fromYear = 1970
            }

            if(toYearEditText?.text.toString() != ""){
                toYear = fromYearEditText?.text.toString().toInt()
            } else {
                toYear = 2021
            }

            //Maturity Rating
            var matureRating = getComparableRating(currMovie.age!!)
            var filteredMatureRating = getComparableRating(maturityEditText.toString())
            var goodMaturity = true

            if((((currMovie.netflix == 1) && netflixSelected) || ((currMovie.disney == 1) && disneySelected) || ((currMovie.hulu == 1) && huluSelected) || ((currMovie.prime == 1) && primeSelected))){
                goodService = true
            }

            if(!goodService){
                Log.d(Constants.TAG,"Not on netflix: $netflixSelected")
            }
//            else if (!(myRating >= fromRating) && (myRating <= toRating)){
//                goodRating = false
//            }
//            else if (!(myYear >= fromYear!!) && (myYear <= toYear!!)){
//                goodYear = false
//            }
//            else if (!(matureRating <= filteredMatureRating)){
//                goodMaturity = false
//            }

            //Filter Genres
            var goodGenre = false

            for(genre in genres){
                if(genre.isChecked){
                    var containedGenre = currMovie.genres.contains(genre.text)
                    //Log.d(Constants.TAG,"Genre check: ${genre.text} in ${currMovie.genres} = $containedGenre")
                    if(currMovie.genres.contains(genre.text)){
                        goodGenre = true || goodGenre
                    }
                }
            }

            if(goodService && goodYear && goodGenre && goodRating && goodMaturity){
                Log.d(Constants.TAG,"Added Movie: ${currMovie.title}")
                goodMovies.add(currMovie)
            }
        }
        switchToRandomMovieDesc(goodMovies)
    }

    private fun switchToRandomMovieDesc(goodMovies : ArrayList<Movie>){

        if(goodMovies.isNotEmpty()){
            val randomMovie = goodMovies.random()
            Log.d(Constants.TAG,"Chose Movie: ${randomMovie.title}")
            listener?.getMovieFragment(MovieWrapper(randomMovie,null))
        }
    }

    private fun getComparableRating(rating : String): Int {
        when(rating){
            "all" -> {
                return 0
            }
            "13+" -> {
                return 2
            }
            "18+" -> {
                return 3
            }
            "7+" -> {
                return 1
            }
            "16+" -> {
                return 3
            }
            else -> {
                return 3
            }
        }
    }

    private fun getAllMovies(){

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if( context is FilterMovieListener){
            listener = context
        } else{
            throw RuntimeException(context.toString() + "must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface FilterMovieListener {
        fun getMovieFragment(movie: MovieWrapper)
    }


}