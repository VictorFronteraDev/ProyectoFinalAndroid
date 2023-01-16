package com.example.proyectofinalandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.adapters.DayOfWeekAdapter
import com.example.proyectofinalandroid.connection.Api
import com.example.proyectofinalandroid.connection.Client
import com.example.proyectofinalandroid.model.DayOfWeek
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DaysOfWeekFragment : Fragment() {

    private var retrofit: Retrofit? = null
    private var dayOfWeekAdapter: DayOfWeekAdapter? = null

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

        dayOfWeekAdapter = DayOfWeekAdapter()

        recycler.adapter = dayOfWeekAdapter

        retrofit = Client.getClient()

        getData()

        return view


    }

    private fun getData() {
        val api: Api? = retrofit?.create(Api::class.java)

        api?.getDaysOfWeek()?.enqueue(object : Callback<ArrayList<DayOfWeek>> {
            override fun onResponse(call: Call<ArrayList<DayOfWeek>>, response: Response<ArrayList<DayOfWeek>>) {
                if (response.isSuccessful) {
                    val dayList = response.body()

                    if (dayList != null) {
                        dayOfWeekAdapter?.addToList(dayList)
                    }
                } else
                    Toast.makeText(context, "Fail into the response", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<DayOfWeek>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}