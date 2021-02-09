package edu.rosehulman.schultc2.randommoviepicker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), StartScreenFragment.FindMovieListener, FilterScreenFragment.FilterMovieListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = StartScreenFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.fragment_container, fragment)
        ft.commit()
    }

    override fun replaceFragment(fragment: Fragment) {
        Log.d(Constants.TAG,"Segue to Filter Screen")
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.toString())
        fragmentTransaction.addToBackStack(fragment.toString())
        fragmentTransaction.commit()
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
        val frag = MovieDescFragment.newInstance(movie)
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, frag, frag.toString())
        fragmentTransaction.addToBackStack(frag.toString())
        fragmentTransaction.commit()
    }

}