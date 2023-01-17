package com.example.proyectofinalandroid.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.UpdateDeleteActivity
import com.example.proyectofinalandroid.adapters.ColorAdapter
import com.example.proyectofinalandroid.connection.Api
import com.example.proyectofinalandroid.connection.Client
import com.example.proyectofinalandroid.dialog.DialogEditDelete
import com.example.proyectofinalandroid.model.Color
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ColorsFragment : Fragment() {

    private var retrofit: Retrofit? = null
    private var colorAdapter: ColorAdapter? = null
    private var pressedPosition: Int = -1
    private var key: String = "color"

    val updateDeleteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultData = result.data?.getBundleExtra(Intent.EXTRA_TEXT)

            // Delete
            if(resultData?.getBoolean("Delete", true) == true){
                deleteData(resultData.getInt("id", 0))
            } else { // Update
                val color = resultData?.getParcelable<Color>("Color")

                updateData(color!!)
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

        recycler.setHasFixedSize(true)

        recycler.addItemDecoration(DividerItemDecoration(context, 1))

        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        colorAdapter = ColorAdapter()

        recycler.adapter = colorAdapter

        retrofit = Client.getClient()

        getData()

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
                    Toast.makeText(context, "Fail into the response", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<Color>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateData(color: Color){
        val api: Api? = retrofit?.create(Api::class.java)

        color.id?.let {
            api?.updateColor(it, color)?.enqueue(object : Callback<Color> {
                override fun onResponse(call: Call<Color>, response: Response<Color>) {
                    if (response.isSuccessful) {
                        val color = response.body()

                        if (color != null) {
                            colorAdapter?.updateList(pressedPosition, color)
                            Toast.makeText(context,R.string.update, Toast.LENGTH_SHORT).show()

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

    private fun deleteData(id: Int){
        val api: Api? = retrofit?.create(Api::class.java)

        // onResponse y onFailure.
        api?.deleteColor(id)?.enqueue(object : Callback<Color> {
            override fun onResponse(call: Call<Color>, response: Response<Color>) {
                if (response.isSuccessful) {
                    val color = response.body()

                    if (color != null) {
                        colorAdapter?.deleteFromList(pressedPosition)
                        Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show()
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