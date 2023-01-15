package com.example.proyectofinalandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectofinalandroid.dao.FavouriteDao
import com.example.proyectofinalandroid.model.Favourite

@Database(
    entities = [Favourite::class],
    version = 1
)

abstract class DataBase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao
}