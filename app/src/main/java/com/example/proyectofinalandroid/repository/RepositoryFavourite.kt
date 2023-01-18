package com.example.proyectofinalandroid.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.proyectofinalandroid.dao.FavouriteDao
import com.example.proyectofinalandroid.database.DataBase
import com.example.proyectofinalandroid.model.Favourite

class RepositoryFavourite() {
    private lateinit var favouriteDao: FavouriteDao
    private lateinit var allFavourites: LiveData<List<Favourite>>

    constructor(application: Application) : this() {
        val db = DataBase.getData(application)
        favouriteDao = db.favouriteDao()
        allFavourites = favouriteDao.getFavourites()
    }

    // Method to get all favorites.
    fun getAllFavourites() : LiveData<List<Favourite>> {
        return allFavourites
    }

    // Method to add a favourite.
    fun add(favourite: Favourite) {
        DataBase.databaseWriterExecutor.execute {
            favouriteDao.addFavourite(favourite)
        }
    }

    // Method to update a favourite.
    fun update(id: Int, spanishWord: String, englishWord: String) {
        DataBase.databaseWriterExecutor.execute {
            favouriteDao.updateFavourite(spanishWord, englishWord)
        }
    }
}