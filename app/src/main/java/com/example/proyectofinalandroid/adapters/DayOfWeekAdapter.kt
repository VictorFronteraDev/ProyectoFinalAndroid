package com.example.proyectofinalandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.model.DayOfWeek

class DayOfWeekAdapter: RecyclerView.Adapter<DayOfWeekAdapter.MiViewHolder>() {

    private var list: ArrayList<DayOfWeek> = ArrayList()
    private var listener: View.OnClickListener? = null

    //Create the ViewHolder
    class MiViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val spanishWord: TextView
        val englishWord: TextView
        val btnFavorite: Button

        init {
            spanishWord = view.findViewById(R.id.txt_view_spanish_word)
            englishWord = view.findViewById(R.id.txt_view_english_word)
            btnFavorite = view.findViewById(R.id.btnFavorite)
        }
    }

    //Create new view in the layout "elements_list"
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MiViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.element_list, viewGroup, false)

        view.setOnClickListener {

        }

        return MiViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: MiViewHolder, position: Int) {
        viewHolder.spanishWord.text = list[position].spanishWord
        viewHolder.englishWord.text = list[position].englishWord

    }

    //Return the size of the list of days
    override fun getItemCount() = list.size


    //Return the element in the position pos
    fun getItem(pos: Int) = list[pos]

    // Method to add a day list to recyclerView
    fun addToList(list_: ArrayList<DayOfWeek>) {
        list.clear()
        list.addAll(list_)

        notifyDataSetChanged() // Update recyclerView
    }

    fun addToList(dayOfWeek: DayOfWeek) {
        list.add(dayOfWeek)

        notifyDataSetChanged() // Update recyclerView
    }

    // Method to update a position of the recyclerView
    fun updateList(pos: Int, dayOfWeek: DayOfWeek) {
        list[pos] = dayOfWeek

        notifyDataSetChanged() // Update recyclerView
    }

    //Method to delete a position of the recyclerView
    fun deleteFromList(pos: Int){
        list.removeAt(pos)

        notifyDataSetChanged()
    }

    fun setOnClickListener(onClickListener: View.OnClickListener) {
        listener = onClickListener
    }
}