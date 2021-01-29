package edu.rosehulman.schultc2.randommoviepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun replaceFragment(frag: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, frag, android.R.attr.fragment.toString())
        fragmentTransaction.addToBackStack(android.R.attr.fragment.toString())
        fragmentTransaction.commit()
    }

}