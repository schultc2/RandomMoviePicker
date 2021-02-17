package edu.rosehulman.schultc2.randommoviepicker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), StartScreenFragment.FindMovieListener, FilterScreenFragment.FilterMovieListener
        , SplashFragment.OnLoginButtonPressedListener, FavoriteMovieList.OnFavoriteSelectedListener, MovieList.OnMovieSelectedListener{

    private val RC_SIGN_IN = 1
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        logInWithUI()
        initializeListener()

//        val fragment = StartScreenFragment()
//        val ft = supportFragmentManager.beginTransaction()
//        ft.add(R.id.fragment_container, fragment)
//        ft.commit()
    }

    override fun replaceFragment(fragment: Fragment) {
//        Log.d(Constants.TAG,"Segue to Filter Screen")
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.toString())
        fragmentTransaction.addToBackStack(fragment.toString())
        fragmentTransaction.commit()
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authStateListener)
    }

    fun signOut(){
        auth.signOut()
    }

    private fun initializeListener(){
        authStateListener = FirebaseAuth.AuthStateListener { auth: FirebaseAuth ->
            val user = auth.currentUser
            Log.d(Constants.TAG, "In auth listener, user = $user")
            if(user != null){
//                replaceFragment(StartScreenFragment())
                val fragment = StartScreenFragment.newInstance(user.uid)
                val ft = supportFragmentManager.beginTransaction()
                ft.add(R.id.fragment_container, fragment)
                ft.commit()
            }else{
                Log.d(Constants.TAG, "user is null")
                switchToSplashFragment()
            }
        }
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun getMovieFragment(movie: MovieWrapper) {
        Log.d(Constants.TAG,"Segue to Movie Desc Screen")
        val frag = MovieDescFragment.newInstance(movie,auth.currentUser!!.uid, false)
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, frag, frag.toString())
        fragmentTransaction.addToBackStack(frag.toString())
        fragmentTransaction.commit()
    }

    override fun getMovieList(goodMovies: ArrayList<Movie>) {
        Log.d(Constants.TAG,"Segue to Movie List Screen")
        val frag = MovieList.newInstance(goodMovies)
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, frag, frag.toString())
        fragmentTransaction.addToBackStack(frag.toString())
        fragmentTransaction.commit()
    }

    private fun switchToSplashFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, SplashFragment())
        ft.commit()
    }

    private fun logInWithUI(){
        // Choose authentication providers
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

// Create and launch sign-in intent
        val loginIntent =
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.mipmap.ic_launcher_foreground)
                        .build()

        startActivityForResult(loginIntent, RC_SIGN_IN)
    }

    override fun onLoginButtonPressed() {
        logInWithUI()
    }

    override fun onFavoriteSelected(movie: MovieWrapper) {
        Log.d(Constants.TAG,"Segue to Movie Desc Screen")
        val frag = MovieDescFragment.newInstance(movie,auth.currentUser!!.uid, true)
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, frag, frag.toString())
        fragmentTransaction.addToBackStack(frag.toString())
        fragmentTransaction.commit()
    }

    override fun onMovieSelected(movie: MovieWrapper) {
        Log.d(Constants.TAG,"Segue to Movie Desc Screen")
        val frag = MovieDescFragment.newInstance(movie,auth.currentUser!!.uid, false)
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, frag, frag.toString())
        fragmentTransaction.addToBackStack(frag.toString())
        fragmentTransaction.commit()
    }

}