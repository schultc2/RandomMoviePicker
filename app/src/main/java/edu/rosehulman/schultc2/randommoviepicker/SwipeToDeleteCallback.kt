package edu.rosehulman.schultc2.randommoviepicker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class SwipeToDeleteCallback(var mAdapter: FavoriteMovieAdapter) : SimpleCallback(
    0, RIGHT)  {


//    fun SwipeToDeleteCallback(adapter: FoodAdapter) {
//        mAdapter = adapter;
//    }
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        mAdapter.remove(position)

    }

}

