package com.example.proyectofinalandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.adapters.ColorAdapter
import com.example.proyectofinalandroid.connection.Api
import com.example.proyectofinalandroid.connection.Client
import com.example.proyectofinalandroid.dialog.DialogEditDelete
import com.example.proyectofinalandroid.model.Color
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ColorsFragment : Fragment(), DialogEditDelete.NoticeDialogListener {

    private var retrofit: Retrofit? = null
    private var colorAdapter: ColorAdapter? = null
    private var pressedPosition: Int = -1

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

        colorAdapter!!.setOnLongClickListener {
            pressedPosition = recycler.getChildLayoutPosition(it)
            DialogEditDelete().setListener(this)
            DialogEditDelete().show(childFragmentManager, DialogEditDelete.TAG)
            true
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

    override fun onDialogEditClick(dialog: DialogEditDelete) {
        val edTxtSpanish = view?.findViewById<EditText>(R.id.edTxtSpanishWord)
        val edTxtEnglish = view?.findViewById<EditText>(R.id.edTxtEnglishWord)
        edTxtSpanish!!.isEnabled
        edTxtEnglish!!.isEnabled

    }

    override fun onDialogDeleteClick(dialog: DialogEditDelete) {
        TODO("Not yet implemented")
    }

}