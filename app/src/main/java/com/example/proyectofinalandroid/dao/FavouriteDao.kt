package com.example.proyectofinalandroid.dao

import androidx.room.*
import com.example.proyectofinalandroid.model.Favourite
@Dao
interface FavouriteDao {

    @Query("SELECT * from Favourite")
    fun getAll(): List<Favourite>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(people: List<Favourite>)

    @Update
    fun update(person: Favourite)

    @Delete
    fun delete(person: Favourite)

}