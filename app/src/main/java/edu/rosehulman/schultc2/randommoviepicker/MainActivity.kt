package edu.rosehulman.schultc2.randommoviepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), StartScreenFragment.FindMovieListener {
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

}