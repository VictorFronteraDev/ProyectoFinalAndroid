package com.example.proyectofinalandroid.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.proyectofinalandroid.model.Favourite
@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourite_table")
    fun getFavourites() : LiveData<List<Favourite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavourite(favourite: Favourite)

    @Query("UPDATE favourite_table SET spanishWord =:spanishWord, englishWord=:englishWord WHERE id = id")
    fun updateFavourite(spanishWord: String, englishWord: String)

}