package com.example.proyectofinalandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalandroid.model.Color

class ColorAdapter: RecyclerView.Adapter<ColorAdapter.MiViewHolder>() {

    private var list: ArrayList<Color> = ArrayList()

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

    //Return the size of the list of colors
    override fun getItemCount() = list.size


    //Return the element in the position pos
    fun getItem(pos: Int) = list[pos]

    // Method to add a color list to recyclerView
    fun addToList(list_: ArrayList<Color>) {
        list.clear()
        list.addAll(list_)

        notifyDataSetChanged() // Update recyclerView
    }

    fun addToList(color: Color) {
        list.add(color)

        notifyDataSetChanged() // Update recyclerView
    }

    // Method to update a position of the recyclerView
    fun updateList(pos: Int, color: Color) {
        list[pos] = color

        notifyDataSetChanged() // Update recyclerView
    }

    //Method to delete a position of the recyclerView
    fun deleteFromList(pos: Int){
        list.removeAt(pos)

        notifyDataSetChanged()
    }


}