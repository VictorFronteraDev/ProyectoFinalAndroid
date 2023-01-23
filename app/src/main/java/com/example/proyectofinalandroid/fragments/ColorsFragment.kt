package com.example.proyectofinalandroid.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalandroid.AddActivity
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.UpdateDeleteActivity
import com.example.proyectofinalandroid.adapters.ColorAdapter
import com.example.proyectofinalandroid.connection.Api
import com.example.proyectofinalandroid.connection.Client
import com.example.proyectofinalandroid.model.Color
import com.example.proyectofinalandroid.model.Favourite
import com.example.proyectofinalandroid.viewmodel.FavouriteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ColorsFragment : Fragment() {

    private lateinit var favouriteViewModel: FavouriteViewModel

    private var retrofit: Retrofit? = null
    private var colorAdapter: ColorAdapter? = null
    private var pressedPosition: Int = -1
    private var key: String = "Color"

    val updateDeleteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultData = result.data?.getBundleExtra(Intent.EXTRA_TEXT)

            // Delete
            if(resultData?.getBoolean("Delete", true) == true){
                deleteColor(resultData.getInt("id", 0))
            } else { // Update
                val color = resultData?.getParcelable<Color>("Color")

                updateColor(color!!)
            }
        }
    }

    val addResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultData = result.data?.getBundleExtra(Intent.EXTRA_TEXT)

            val color = resultData?.getParcelable<Color>("Color")

            if(color != null) {
                addColor(color)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler, container, false)

        val recycler: RecyclerView = view.findViewById(R.id.recycler_fragment)

        val fab: FloatingActionButton = view.findViewById(R.id.fab)

        recycler.setHasFixedSize(true)

        recycler.addItemDecoration(DividerItemDecoration(context, 1))

        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        favouriteViewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

        colorAdapter = ColorAdapter { btn, pressedPosition ->
            val color = colorAdapter?.getItem(pressedPosition)

            val favourite = Favourite(color?.spanishWord, color?.englishWord)

            favouriteViewModel.add(favourite)
            btn.setBackgroundResource(R.drawable.heart)

        }

        recycler.adapter = colorAdapter

        colorAdapter!!.setOnClickListener {
            pressedPosition = recycler.getChildLayoutPosition(it)

            val bundle = Bundle()

            val color = colorAdapter?.getItem(pressedPosition)

            bundle.putString("Key", key)
            bundle.putParcelable("Color", color)

            val intent = Intent(context, UpdateDeleteActivity::class.java).apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }
            updateDeleteResult.launch(intent)
        }

        fab.setOnClickListener {
            val bundle = Bundle()

            bundle.putString("Key", key)
            val intent = Intent(context, AddActivity::class.java).apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }

            addResult.launch(intent)
        }

        retrofit = Client.getClient()

        getData()

        return view

    }

    private fun getData() {
        val api: Api? = retrofit?.create(Api::class.java)

        api?.getColors()?.enqueue(object : Callback<ArrayList<Color>> {
            override fun onResponse(call: Call<ArrayList<Color>>, response: Response<ArrayList<Color>>) {
                if (response.isSuccessful) {
                    val colorList = response.body()

                    if (colorList != null) {
                        colorAdapter?.addToList(colorList)
                    }
                } else
                    Toast.makeText(context, R.string.fail_reponse, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<Color>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addColor(color: Color) {
        val api: Api? = retrofit?.create(Api::class.java)

        api?.saveColor(color.spanishWord, color.englishWord)?.enqueue(object : Callback<Color> {
            override fun onResponse(call: Call<Color>, response: Response<Color>) {
                if(response.isSuccessful) {
                    val color = response.body()

                    if(color != null) {
                        colorAdapter?.addToList(color)
                        Snackbar.make(activity!!.findViewById(android.R.id.content), R.string.color_added, Snackbar.LENGTH_LONG)
                            .setAction(R.string.accept){
                            }
                            .show()
                    }
                } else
                    Toast.makeText(context, R.string.fail_reponse, Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Color>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateColor(color: Color){
        val api: Api? = retrofit?.create(Api::class.java)

        color.id?.let {
            api?.updateColor(it, color)?.enqueue(object : Callback<Color> {
                override fun onResponse(call: Call<Color>, response: Response<Color>) {
                    if (response.isSuccessful) {
                        val color = response.body()

                        if (color != null) {
                            colorAdapter?.updateList(pressedPosition, color)
                            Snackbar.make(activity!!.findViewById(android.R.id.content), R.string.updated, Snackbar.LENGTH_LONG)
                                .setAction(R.string.accept){
                                }
                                .show()

                        }
                    } else
                        Toast.makeText(context,R.string.fail_reponse, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Color>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun deleteColor(id: Int){
        val api: Api? = retrofit?.create(Api::class.java)

        // onResponse y onFailure.
        api?.deleteColor(id)?.enqueue(object : Callback<Color> {
            override fun onResponse(call: Call<Color>, response: Response<Color>) {
                if (response.isSuccessful) {
                    val color = response.body()

                    if (color != null) {
                        colorAdapter?.deleteFromList(pressedPosition)
                        Snackbar.make(activity!!.findViewById(android.R.id.content), R.string.deleted, Snackbar.LENGTH_LONG)
                            .setAction(R.string.accept){
                            }
                            .show()
                    }
                } else
                    Toast.makeText(context, R.string.fail_reponse, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Color>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}