package com.example.proyectofinalandroid.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.proyectofinalandroid.model.Favourite
@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourite_table ORDER BY id ASC")
    fun getFavourites() : LiveData<List<Favourite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavourite(favourite: Favourite)

    @Query("UPDATE favourite_table SET spanishWord =:spanishWord, englishWord=:englishWord WHERE id =:id")
    fun updateFavourite(id: Int, spanishWord: String, englishWord: String)

    @Query("DELETE FROM favourite_table WHERE spanishWord =:spanishWord AND englishWord=:englishWord")
    fun deleteFavourite(spanishWord: String, englishWord: String)

}