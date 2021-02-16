package edu.rosehulman.schultc2.randommoviepicker

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.squareup.picasso.Picasso

class FavoriteMovieAdapter(var context: Context?,
                           var listener: FavoriteMovieList.OnFavoriteSelectedListener?,
                           var uid: String
                           ): RecyclerView.Adapter<FavoriteMovieHolder>() {
    private val movies = ArrayList<FavoriteMovie>()

    val movieQuotesRef = FirebaseFirestore
            .getInstance()
            .collection(Constants.USERS_COLLECTION)
            .document(uid!!)
            .collection(Constants.FAVORITES_COLLECTION)
    private var listenerRegistration: ListenerRegistration? = null

    fun addSnapshotListener() {
        listenerRegistration = movieQuotesRef
                .orderBy(Movie.LAST_TOUCHED_KEY, Query.Direction.ASCENDING)
                .addSnapshotListener { querySnapshot, e ->
                    if (e != null) {
                        Log.w(Constants.TAG, "listen error", e)
                    } else {
                        processSnapshotChanges(querySnapshot!!)
                    }
                }
    }

    fun startListening(){
        if(listenerRegistration != null){
            listenerRegistration!!.remove()
        }
        var query = movieQuotesRef.orderBy(Movie.LAST_TOUCHED_KEY, Query.Direction.ASCENDING)

        listenerRegistration = query.addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
            if(exception != null){
                Log.e(Constants.TAG,"Listen error: $exception")
                return@addSnapshotListener
            }
            movies.removeAll(movies)
            snapshot?.documents?.forEach{ documentSnapshot : DocumentSnapshot ->
                movies.add(FavoriteMovie.fromSnapshot(documentSnapshot))
            }
            notifyDataSetChanged()

        }
    }

    private fun processSnapshotChanges(querySnapshot: QuerySnapshot) {
        movieQuotesRef.addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
            if(exception != null){
                Log.e(Constants.TAG,"Listen error: $exception")
                return@addSnapshotListener
            }
            //pics.removeAll(pics)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.favorite_movie_view, parent, false)
        return FavoriteMovieHolder(view, this)
    }

    override fun onBindViewHolder(holder: FavoriteMovieHolder, position: Int) {
        holder.bind(movies[position])
        Picasso.with(context).load(movies[position].Poster).into(holder.fav_movie_img)
    }

    fun selectFavoriteAt(adapterPosition: Int){
        val movie = movies[adapterPosition]
        listener?.onFavoriteSelected(MovieWrapper(movie.movie,null))
//        picsRef.document(pic.id).set(pic)
    }

    override fun getItemCount(): Int = movies.size

    fun add(fm : FavoriteMovie){
        movieQuotesRef.add(fm)
    }

    fun getItem(adapterPosition: Int) = movies[adapterPosition]

    fun remove(adapterPosition: Int){
        movieQuotesRef.document(movies[adapterPosition].id).delete()
        notifyItemChanged(adapterPosition)
    }
}