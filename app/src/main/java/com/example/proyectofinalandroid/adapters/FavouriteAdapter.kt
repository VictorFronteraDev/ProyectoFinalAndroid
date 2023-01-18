package com.example.proyectofinalandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.model.Favourite

class FavouriteAdapter: RecyclerView.Adapter<FavouriteAdapter.MiViewHolder>() {

    private var list: ArrayList<Favourite> = ArrayList()
    private var listener: View.OnClickListener? = null

    class MiViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val spanishWord: TextView
        val englishWord: TextView
        val btnFavorite: Button

        init {
            spanishWord = view.findViewById(R.id.txt_view_spanish_word)
            englishWord = view.findViewById(R.id.txt_view_english_word)
            btnFavorite = view.findViewById(R.id.btnFavourite)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MiViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.element_list, viewGroup, false)

        view.setOnClickListener (listener)

        return MiViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: FavouriteAdapter.MiViewHolder, position: Int) {
        viewHolder.spanishWord.text = list[position].spanishWord
        viewHolder.englishWord.text = list[position].englishWord

    }

    override fun getItemCount() = list.size


    fun getItem(pos: Int) = list[pos]

    fun setOnItemClickListener(onClickListener: View.OnClickListener) {
        listener = onClickListener
    }

    fun addToList(list_: ArrayList<Favourite>) {
        list.clear()
        list.addAll(list_)

        notifyDataSetChanged()
    }



}