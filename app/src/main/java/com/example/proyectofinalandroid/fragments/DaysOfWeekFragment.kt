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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.UpdateDeleteActivity
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
    private var pressedPosition: Int = -1
    private var key: String = "DayOfWeek"

    val updateDeleteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultData = result.data?.getBundleExtra(Intent.EXTRA_TEXT)

            // Delete
            if(resultData?.getBoolean("Delete", true) == true){
                deleteData(resultData.getInt("id", 0))
            } else { // Update
                val dayOfWeek = resultData?.getParcelable<DayOfWeek>("DayOfWeek")

                updateData(dayOfWeek!!)
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

        dayOfWeekAdapter = DayOfWeekAdapter()

        dayOfWeekAdapter!!.setOnClickListener {
            pressedPosition = recycler.getChildLayoutPosition(it)

            val bundle = Bundle()

            val dayOfWeek = dayOfWeekAdapter?.getItem(pressedPosition)

            bundle.putString("Key", key)
            bundle.putParcelable("DayOfWeek", dayOfWeek)

            val intent = Intent(context, UpdateDeleteActivity::class.java).apply {
                putExtra(Intent.EXTRA_TEXT, bundle)

            }
            updateDeleteResult.launch(intent)
        }

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
    private fun updateData(dayOfWeek: DayOfWeek){
        val api: Api? = retrofit?.create(Api::class.java)

        dayOfWeek.id?.let {
            api?.updateDayOfWeek(it, dayOfWeek)?.enqueue(object : Callback<DayOfWeek> {
                override fun onResponse(call: Call<DayOfWeek>, response: Response<DayOfWeek>) {
                    if (response.isSuccessful) {
                        val day = response.body()

                        if (day != null) {
                            dayOfWeekAdapter?.updateList(pressedPosition, dayOfWeek)
                            Toast.makeText(context,R.string.update, Toast.LENGTH_SHORT).show()

                        }
                    } else
                        Toast.makeText(context,R.string.fail_reponse, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<DayOfWeek>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun deleteData(id: Int){
        val api: Api? = retrofit?.create(Api::class.java)

        // onResponse y onFailure.
        api?.deleteDayOfWeek(id)?.enqueue(object : Callback<DayOfWeek> {
            override fun onResponse(call: Call<DayOfWeek>, response: Response<DayOfWeek>) {
                if (response.isSuccessful) {
                    val day = response.body()

                    if (day != null) {
                        dayOfWeekAdapter?.deleteFromList(pressedPosition)
                        Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show()
                    }
                } else
                    Toast.makeText(context, R.string.fail_reponse, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<DayOfWeek>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
