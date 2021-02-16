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

private const val ARG_UID = "UID"

class FavoriteMovieList : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: OnFavoriteSelectedListener? = null
    lateinit var adapter: FavoriteMovieAdapter
    private var uid: String? = null
    private lateinit var recyclerView : RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_UID)
            adapter = FavoriteMovieAdapter(context, listener, uid!!)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        recyclerView = inflater.inflate(R.layout.favorite_main, container, false) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        adapter.startListening()
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        return recyclerView
    }


    fun onButtonPressed(movie: MovieWrapper){
        listener?.onFavoriteSelected(movie)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnFavoriteSelectedListener){
            listener = context
        } else{
            throw RuntimeException(context.toString() + "must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFavoriteSelectedListener {
        fun onFavoriteSelected(movie: MovieWrapper)
    }

    companion object {
        @JvmStatic
        fun newInstance(uid: String) =
                FavoriteMovieList().apply {
                    arguments = Bundle().apply {
                        putString(ARG_UID, uid)
                    }
                }
    }

}
