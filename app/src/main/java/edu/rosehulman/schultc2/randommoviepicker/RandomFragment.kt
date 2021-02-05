package edu.rosehulman.schultc2.randommoviepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorite_main.*

class RandomFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.favorite_main, container, false)

        var recycler = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(rootView.context)
        recycler_view.layoutManager = layoutManager
        var adapter = FavoriteMovieAdapter(rootView.context);
        recycler_view.adapter = adapter
        return rootView
    }
}