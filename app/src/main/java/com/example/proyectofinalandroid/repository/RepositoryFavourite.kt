package com.example.proyectofinalandroid.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.proyectofinalandroid.dao.FavouriteDao
import com.example.proyectofinalandroid.database.DataBase
import com.example.proyectofinalandroid.model.Favourite

class RepositoryFavourite() {
    private lateinit var favouriteDao: FavouriteDao
    private lateinit var allFavorites: LiveData<List<Favourite>>

    constructor(application: Application) : this() {
        val db = DataBase.getData(application)
        favouriteDao = db.favouriteDao()
        allFavorites = favouriteDao.getFavourites()
    }

    // Method to get all favorites.
    fun getAllFavorites() : LiveData<List<Favourite>> {
        return allFavorites
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
            favouriteDao.updateFavourite(id, spanishWord, englishWord)
        }
    }

    fun delete(spanishWord: String, englishWord: String) {
        DataBase.databaseWriterExecutor.execute {
            favouriteDao.deleteFavourite(spanishWord, englishWord)
        }
    }
}