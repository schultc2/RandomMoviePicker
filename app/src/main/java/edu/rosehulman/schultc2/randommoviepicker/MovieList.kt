package edu.rosehulman.schultc2.randommoviepicker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val ARG_MOVIES = "MOVIES"

class MovieList : Fragment(), View.OnCreateContextMenuListener{
    // TODO: Rename and change types of parameters
    private var listener: OnMovieSelectedListener? = null
    lateinit var adapter: MovieListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            adapter = MovieListAdapter(context!!,listener!!)
            adapter.setMovies(it.getParcelableArrayList<Movie>("MOVIES")!!)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.favorite_main, container, false) as RecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        return recyclerView
    }


    fun onButtonPressed(movie: MovieWrapper){
        listener?.onMovieSelected(movie)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if( context is OnMovieSelectedListener){
            listener = context
        } else{
            throw RuntimeException(context.toString() + "must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnMovieSelectedListener {
        fun onMovieSelected(movie: MovieWrapper)
    }


    companion object {
        @JvmStatic
        fun newInstance(goodMovies: ArrayList<Movie>) =
                MovieList().apply {
                    arguments = Bundle().apply {
                        putParcelableArrayList(ARG_MOVIES,goodMovies)
                    }
                }
    }

}