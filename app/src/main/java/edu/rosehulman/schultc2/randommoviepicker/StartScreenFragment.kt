package edu.rosehulman.schultc2.randommoviepicker

import android.R.attr.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class StartScreenFragment : Fragment(){

    private var listener: FindMovieListener? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.start_screen, container, false)


        val goButton: Button? = rootView?.findViewById(R.id.pick_a_movie_button) ?: null

        goButton?.setOnClickListener {
            var filterFrag = FilterScreenFragment() as Fragment
            listener?.replaceFragment(filterFrag)
        }

        return rootView
    }

    interface FindMovieListener {
        fun replaceFragment(frag: Fragment)
    }

}