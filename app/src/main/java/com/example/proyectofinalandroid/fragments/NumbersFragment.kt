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
import com.example.proyectofinalandroid.adapters.NumbersAdapter
import com.example.proyectofinalandroid.connection.Api
import com.example.proyectofinalandroid.connection.Client
import com.example.proyectofinalandroid.model.Numbers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NumbersFragment : Fragment() {

    private var retrofit: Retrofit? = null
    private var numbersAdapter: NumbersAdapter? = null
    private var pressedPosition: Int = -1
    private var key: String = "Numbers"

    val updateDeleteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultData = result.data?.getBundleExtra(Intent.EXTRA_TEXT)

            // Delete
            if(resultData?.getBoolean("Delete", true) == true){
                deleteData(resultData.getInt("id", 0))
            } else { // Update
                val number = resultData?.getParcelable<Numbers>("Numbers")

                updateData(number!!)
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

        numbersAdapter = NumbersAdapter()

        numbersAdapter!!.setOnClickListener {
            pressedPosition = recycler.getChildLayoutPosition(it)

            val bundle = Bundle()

            val number = numbersAdapter?.getItem(pressedPosition)

            bundle.putString("Key", key)
            bundle.putParcelable("Numbers", number)

            val intent = Intent(context, UpdateDeleteActivity::class.java).apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }
            updateDeleteResult.launch(intent)
        }

        recycler.adapter = numbersAdapter

        retrofit = Client.getClient()

        getData()

        return view
    }

    private fun getData() {
        val api: Api? = retrofit?.create(Api::class.java)

        api?.getNumbers()?.enqueue(object : Callback<ArrayList<Numbers>> {
            override fun onResponse(call: Call<ArrayList<Numbers>>, response: Response<ArrayList<Numbers>>) {
                if (response.isSuccessful) {
                    val numberList = response.body()

                    if (numberList != null) {
                        numbersAdapter?.addToList(numberList)
                    }
                } else
                    Toast.makeText(context, "Fail into the response", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<Numbers>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateData(number: Numbers){
        val api: Api? = retrofit?.create(Api::class.java)

        number.id?.let {
            api?.updateNumber(it, number)?.enqueue(object : Callback<Numbers> {
                override fun onResponse(call: Call<Numbers>, response: Response<Numbers>) {
                    if (response.isSuccessful) {
                        val number = response.body()

                        if (number != null) {
                            numbersAdapter?.updateList(pressedPosition, number)
                            Toast.makeText(context,R.string.update, Toast.LENGTH_SHORT).show()

                        }
                    } else
                        Toast.makeText(context,R.string.fail_reponse, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Numbers>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun deleteData(id: Int){
        val api: Api? = retrofit?.create(Api::class.java)

        // onResponse y onFailure.
        api?.deleteNumber(id)?.enqueue(object : Callback<Numbers> {
            override fun onResponse(call: Call<Numbers>, response: Response<Numbers>) {
                if (response.isSuccessful) {
                    val color = response.body()

                    if (color != null) {
                        numbersAdapter?.deleteFromList(pressedPosition)
                        Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show()
                    }
                } else
                    Toast.makeText(context, R.string.fail_reponse, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Numbers>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
