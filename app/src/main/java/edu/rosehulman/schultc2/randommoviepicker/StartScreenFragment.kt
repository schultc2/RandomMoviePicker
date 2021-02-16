package edu.rosehulman.schultc2.randommoviepicker

import android.R.attr.fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


private const val ARG_UID = "UID"

class StartScreenFragment : Fragment(){

    private var listener: FindMovieListener? = null
    private var uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_UID)
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.start_screen, container, false)

        val favorites : Button? = rootView?.findViewById(R.id.fav_list_button) ?: null
        val logout : Button? = rootView?.findViewById(R.id.logout_button) ?: null

        val goButton: Button? = rootView?.findViewById(R.id.pick_a_movie_button) ?: null

        goButton?.setOnClickListener {
            Log.d(Constants.TAG,"Segue to Filter Screen")
            var filterFrag = FilterScreenFragment() as Fragment
            listener?.replaceFragment(filterFrag)
        }

        favorites?.setOnClickListener {
            Log.d(Constants.TAG,"Segue to Favorites Screen")
            var favFrag = FavoriteMovieList.newInstance(uid!!) as Fragment
            listener?.replaceFragment(favFrag)
        }

        logout?.setOnClickListener {
            Log.d(Constants.TAG,"Logout")
            (activity as MainActivity).signOut()
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if( context is FindMovieListener){
            listener = context
        } else{
            throw RuntimeException(context.toString() + "must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface FindMovieListener {
        fun replaceFragment(frag: Fragment)
    }

    companion object {
        @JvmStatic
        fun newInstance(uid: String) =
                StartScreenFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_UID, uid)
                    }
                }
    }

}