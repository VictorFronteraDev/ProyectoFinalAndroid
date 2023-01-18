package com.example.proyectofinalandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.adapters.ColorAdapter
import com.example.proyectofinalandroid.adapters.FavouriteAdapter
import com.example.proyectofinalandroid.connection.Client
import com.example.proyectofinalandroid.model.Favourite
import com.example.proyectofinalandroid.viewmodel.FavouriteViewModel
import retrofit2.Retrofit

class FavouriteFragment : Fragment() {

    private lateinit var favouriteViewModel: FavouriteViewModel

    private var retrofit: Retrofit? = null
    private var favouriteAdapter: FavouriteAdapter? = null
    private var pressedPosition: Int = -1
    private var key: String = "Favourite"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler, container, false)

        val recycler: RecyclerView = view.findViewById(R.id.recycler_fragment)

        recycler.setHasFixedSize(true)

        recycler.addItemDecoration(DividerItemDecoration(context, 1))

        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        favouriteAdapter = FavouriteAdapter()

        recycler.adapter = favouriteAdapter

        favouriteViewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

        favouriteViewModel.getAllFavorites().observe(viewLifecycleOwner) { favourite ->
            favouriteAdapter!!.addToList(favourite as ArrayList<Favourite>)

        }


        return view
    }

}