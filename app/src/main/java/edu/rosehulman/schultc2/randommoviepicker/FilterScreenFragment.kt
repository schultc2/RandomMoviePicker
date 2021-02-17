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
import kotlinx.android.synthetic.main.filter_screen.*

class FilterScreenFragment: Fragment(), View.OnClickListener {

    private var listener: FilterMovieListener? = null
    private var genres: ArrayList<CheckBox> = arrayListOf()
    private var genreBool: ArrayList<Boolean> = arrayListOf()
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

    private lateinit var comedyRadio : CheckBox
    private lateinit var scifiRadio : CheckBox
    private lateinit var dramaRadio : CheckBox
    private lateinit var supernaturalRadio : CheckBox
    private lateinit var westernRadio : CheckBox
    private lateinit var foreignRadio : CheckBox
    private lateinit var actionRadio : CheckBox
    private lateinit var feelgoodRadio : CheckBox
    private lateinit var animatedRadio : CheckBox
    private lateinit var thrillerRadio : CheckBox
    private lateinit var fantasyRadio : CheckBox
    private lateinit var adventureRadio : CheckBox
    private lateinit var romanceRadio : CheckBox
    private lateinit var crimeRadio : CheckBox
    private lateinit var horrorRadio : CheckBox
    private lateinit var documentaryRadio : CheckBox
    private lateinit var familyRadio : CheckBox
    private lateinit var sportRadio : CheckBox
    private lateinit var mysteryRadio : CheckBox
    private lateinit var classicRadio : CheckBox



    private var netflixButton: ToggleButton? = null
    private var primeButton: ToggleButton? = null
    private var disneyButton: ToggleButton? = null
    private var huluButton: ToggleButton? = null

    var fromRatingBar: RatingBar? = null
    var toRatingBar: RatingBar? = null

    var fromYearEditText : EditText? = null
    var toYearEditText : EditText? = null

    var maturityEditText : EditText? = null

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


        comedyRadio = rootView?.findViewById(R.id.comedy_radio)!!

        comedyRadio.setOnClickListener(this)

        scifiRadio  = rootView?.findViewById(R.id.scifi_radio)!!
            scifiRadio.setOnClickListener(this)
        supernaturalRadio = rootView?.findViewById(R.id.supernatural_radio)
            supernaturalRadio.setOnClickListener(this)
        feelgoodRadio  = rootView?.findViewById(R.id.feelgood_radio)
            feelgoodRadio.setOnClickListener(this)
        westernRadio  = rootView?.findViewById(R.id.western_radio)
            westernRadio.setOnClickListener(this)
        foreignRadio = rootView?.findViewById(R.id.foreign_radio)
            foreignRadio.setOnClickListener(this)
        actionRadio = rootView?.findViewById(R.id.action_radio)
            actionRadio.setOnClickListener(this)
        dramaRadio  = rootView?.findViewById(R.id.drama_radio)
            dramaRadio.setOnClickListener(this)
        animatedRadio = rootView?.findViewById(R.id.animated_radio)
            animatedRadio.setOnClickListener(this)
        thrillerRadio = rootView?.findViewById(R.id.thriller_radio)
            thrillerRadio.setOnClickListener(this)
        fantasyRadio = rootView?.findViewById(R.id.fantasy_radio)
            fantasyRadio.setOnClickListener(this)
        adventureRadio = rootView?.findViewById(R.id.adventure_radio)
            adventureRadio.setOnClickListener(this)
        romanceRadio = rootView?.findViewById(R.id.romance_radio)
            romanceRadio.setOnClickListener(this)
        documentaryRadio  = rootView?.findViewById(R.id.documentary_radio)
            documentaryRadio.setOnClickListener(this)
        crimeRadio= rootView?.findViewById(R.id.crime_radio)
            crimeRadio.setOnClickListener(this)
        horrorRadio = rootView?.findViewById(R.id.horror_radio)
            horrorRadio.setOnClickListener(this)
        familyRadio = rootView?.findViewById(R.id.family_radio)
            familyRadio.setOnClickListener(this)
        sportRadio = rootView?.findViewById(R.id.sports_radio)
            sportRadio.setOnClickListener(this)
        mysteryRadio  = rootView?.findViewById(R.id.mystery_radio)
            mysteryRadio.setOnClickListener(this)
        classicRadio = rootView?.findViewById(R.id.classic_radio)
            classicRadio.setOnClickListener(this)

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
        genres.add(documentaryRadio!!)
        genres.add(crimeRadio!!)
        genres.add(horrorRadio!!)
        genres.add(familyRadio!!)
        genres.add(sportRadio!!)
        genres.add(mysteryRadio!!)
        genres.add(classicRadio!!)


        for(i in 1 .. 20){
            genreBool.add(false)
        }



        //Maturity Rating Input
        maturityEditText = rootView?.findViewById(R.id.maturity_rating_input) ?: null

//        //Actors Input
//        actorsEditText = rootView?.findViewById(R.id.actors_input) ?: null
//
//        //Keyword Input
//        keywordEditText = rootView?.findViewById(R.id.keyword_input) ?: null

        val searchButton: Button? = rootView?.findViewById(R.id.search_button) ?: null

        searchButton?.setOnClickListener {
            if(movies.isEmpty()){
                grabAllMovies()
            } else{
                getFilteredMovie()
            }
        }

        return rootView
    }

    private fun grabAllMovies(){
            val moviesRef: CollectionReference = FirebaseFirestore
                    .getInstance()
                    .collection("FormattedMovies")
            val query = moviesRef.orderBy("id", Query.Direction.DESCENDING)
            query.addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    Log.e(Constants.TAG, "Error getting movies: $error")
                    return@addSnapshotListener
                }
                movies.removeAll(movies)
                querySnapshot?.documents?.forEach { documentSnapshot: DocumentSnapshot ->
                    movies.add(Movie.fromSnapshot(documentSnapshot))
                }
                getFilteredMovie()
            }
    }

    private fun getFilteredMovie(){
        Log.d(Constants.TAG,"Filtering Movies")
        var goodMovies = ArrayList<Movie>()
        if(movies.isEmpty()){
            Log.d(Constants.TAG,"Movies is empty")
        }


        for(currMovie in movies){
            //Filter Streaming Service
            var goodService = false
//            Log.d(Constants.TAG,"Currently Checking $currMovie.title")


            //Filter Rating
            var myRating = currMovie.rating.toFloat()/2

            var goodRating = false

            //Filter year
            var myYear = currMovie.year.toInt()
            var goodYear = false

            if(fromYearEditText?.text.toString() != ""){
                fromYear = fromYearEditText?.text.toString().toInt()
            } else {
                fromYear = 1970
            }

            if(toYearEditText?.text.toString() != ""){
                toYear = toYearEditText?.text.toString().toInt()
            } else {
                toYear = 2021
            }

            //Maturity Rating
            var matureRating = getComparableRating(currMovie.age!!)
            //Log.d(Constants.TAG,"Maturity input: ${maturityEditText?.text.toString()}")
            var filteredMatureRating = getComparableRating(maturityEditText?.text.toString())
            var goodMaturity = false

            if((((currMovie.netflix == 1) && netflixSelected) || ((currMovie.disney == 1) && disneySelected) || ((currMovie.hulu == 1) && huluSelected) || ((currMovie.prime == 1) && primeSelected))){
                goodService = true
            }

//            Log.d(Constants.TAG,"Rating check: ${myRating} in ${fromRating} to $toRating")
//            Log.d(Constants.TAG,"Year check: ${myYear} in ${fromYear} to $toYear")
//            Log.d(Constants.TAG,"Maturity check: ${matureRating} in $filteredMatureRating")
            if(!goodService){
//                Log.d(Constants.TAG,"Not on netflix: $netflixSelected")
            }
            if ((myRating >= fromRating) && (myRating <= toRating)){
                goodRating = true
            }
            if ((myYear >= fromYear!!) && (myYear <= toYear!!)){
                goodYear = true
            }
            if (matureRating <= filteredMatureRating){
                goodMaturity = true
            }

//            Log.d(Constants.TAG,"Rating check: $goodRating")
//            Log.d(Constants.TAG,"Year check: $goodYear")
//            Log.d(Constants.TAG,"Maturity check: $goodMaturity")

            //Filter Genres
            var goodGenre = false

            for(i in 0 .. genres.size-1){
                if(genreBool[i]){
                    var containedGenre = currMovie.genres.contains(genres[i].text)
//                    Log.d(Constants.TAG,"Genre check: ${genres[i].text} in ${currMovie.genres} = $containedGenre")
                    if(currMovie.genres.contains(genres[i].text)){
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

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.comedy_radio -> {
                genreBool[0] = comedyRadio.isChecked
            }
            R.id.scifi_radio -> {
                genreBool[1] = scifiRadio.isChecked
            }
            R.id.supernatural_radio -> {
                genreBool[2] = supernaturalRadio.isChecked
            }
            R.id.feelgood_radio -> {
                genreBool[3] = feelgoodRadio.isChecked
            }
            R.id.western_radio -> {
                genreBool[4] = westernRadio.isChecked
            }
            R.id.foreign_radio -> {
                genreBool[5] = foreignRadio.isChecked
            }
            R.id.action_radio -> {
                genreBool[6] = actionRadio.isChecked
            }
            R.id.drama_radio -> {
                genreBool[7] = dramaRadio.isChecked
            }
            R.id.animated_radio -> {
                genreBool[8] = animatedRadio.isChecked
            }
            R.id.thriller_radio -> {
                genreBool[9] = thrillerRadio.isChecked
            }
            R.id.fantasy_radio -> {
                genreBool[10] = fantasyRadio.isChecked
            }
            R.id.adventure_radio -> {
                genreBool[11] = adventureRadio.isChecked
            }
            R.id.romance_radio -> {
                genreBool[12] = romanceRadio.isChecked
            }
            R.id.documentary_radio -> {
                genreBool[13] = documentaryRadio.isChecked
            }
            R.id.crime_radio -> {
                genreBool[14] = crimeRadio.isChecked
            }
            R.id.horror_radio -> {
                genreBool[15] = horrorRadio.isChecked
            }
            R.id.family_radio -> {
                genreBool[16] = familyRadio.isChecked
            }
            R.id.sports_radio -> {
                genreBool[17] = sportRadio.isChecked
            }
            R.id.mystery_radio -> {
                genreBool[18] = mysteryRadio.isChecked
            }
            R.id.classic_radio -> {
                genreBool[19] = classicRadio.isChecked
            }
        }
    }


}